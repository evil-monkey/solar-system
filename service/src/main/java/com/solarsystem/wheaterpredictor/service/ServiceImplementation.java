package com.solarsystem.wheaterpredictor.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.solarsystem.wheaterpredictor.api.ServiceApi;
import com.solarsystem.wheaterpredictor.api.dto.HealthCheckResponse;
import com.solarsystem.wheaterpredictor.api.dto.WheaterStatus;
import com.solarsystem.wheaterpredictor.core.WheaterPredictor;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.service.exceptions.InvalidDataException;
import com.solarsystem.wheaterpredictor.service.exceptions.WheaterPredictorException;

@Component
public class ServiceImplementation implements ServiceApi {

	public static final Logger LOGGER = LoggerFactory.getLogger(ServiceImplementation.class);

	@Inject
	private WheaterPredictor wheaterPredictor;

	private String version;

	public ServiceImplementation() {
		super();
		version = this.getClass().getPackage().getImplementationVersion();
	}

	private String getPackageVersion() {
		if (version == null) {
			version = VERSION;
		}
		return version;
	}

	@Override
	public void keepAlive() {
		LOGGER.debug("I'm alive!");
	}

	@Override
	public HealthCheckResponse healthCheck() {
		return new HealthCheckResponse(SERVICE_NAME, getPackageVersion());
	}

	@Override
	public Object getVersion() {
		Map<String, String> response = new HashMap<>();
		response.put("version", getPackageVersion());
		return response;
	}

	@Override
	public WheaterStatus getWheaterStatus(Integer day) {

		if (day == null) {
			throw new InvalidDataException("A day is required!");
		}

		try {
			WheaterStatus wheaterStatus = new WheaterStatus();
			wheaterStatus.setDia(day);
			Collection<String> events = wheaterPredictor.predict(day).stream().map(eventType -> eventType.getName())
					.collect(Collectors.toSet());
			wheaterStatus.setClima(String.join(", ", events));
			return wheaterStatus;

		} catch (PatternCalculationError pce) {
			throw new WheaterPredictorException("Can't obtain wheater patterns: " + pce.getMessage());
		} catch (RuntimeException e) {
			throw new WheaterPredictorException("Error: " + e.getMessage());
		}
	}

}
