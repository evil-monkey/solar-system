package com.solarsystem.wheaterpredictor.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.solarsystem.wheaterpredictor.api.ServiceApi;
import com.solarsystem.wheaterpredictor.api.dto.HealthCheckResponse;

@Component
public class ServiceImplementation implements ServiceApi {

	public static final Logger LOGGER = LoggerFactory.getLogger(ServiceImplementation.class);

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

}
