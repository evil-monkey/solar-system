package com.solarsystem.wheaterpredictor.test.service;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.solarsystem.wheaterpredictor.api.dto.HealthCheckResponse;
import com.solarsystem.wheaterpredictor.api.dto.WheaterStatus;
import com.solarsystem.wheaterpredictor.core.WheaterPredictor;
import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.TransitionWheater;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.service.ServiceImplementation;
import com.solarsystem.wheaterpredictor.service.exceptions.InvalidDataException;
import com.solarsystem.wheaterpredictor.service.exceptions.WheaterPredictorException;

public class ServiceTest {

	@Mock
	private WheaterPredictor wheaterPredictor;

	@InjectMocks
	private ServiceImplementation service;

	@Before
	public void setUp() {
		service = new ServiceImplementation();

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getWheaterStatusOKTest() throws Exception {
		Mockito.when(wheaterPredictor.predict(Mockito.anyInt()))
				.thenReturn(Arrays.asList(new EventType[] { new TransitionWheater() }));

		WheaterStatus wheaterStatus = service.getWheaterStatus(50);
		assertNotNull("No wheaterStatus returned", wheaterStatus);
		assertEquals("Wrong day specified", Integer.valueOf(50), wheaterStatus.getDia());
		assertEquals("Wrong wheater returned", "Clima de transición", wheaterStatus.getClima());

	}

	@Test(expected = WheaterPredictorException.class)
	public void getWheaterStatusNothingReturnedTest() throws Exception {
		Mockito.when(wheaterPredictor.predict(Mockito.anyInt())).thenReturn(null);

		service.getWheaterStatus(50);
	}

	@Test
	public void getWheaterStatusNoEventsReturnedTest() throws Exception {
		Mockito.when(wheaterPredictor.predict(Mockito.anyInt())).thenReturn(Arrays.asList(new EventType[] {}));

		WheaterStatus wheaterStatus = service.getWheaterStatus(50);
		assertNotNull("No wheaterStatus returned", wheaterStatus);
		assertEquals("Wrong day specified", Integer.valueOf(50), wheaterStatus.getDia());
		assertEquals("Wrong wheater returned", "", wheaterStatus.getClima());

	}

	@Test(expected = InvalidDataException.class)
	public void getWheaterStatusBadRequest() throws Exception {
		Mockito.when(wheaterPredictor.predict(Mockito.anyInt()))
				.thenReturn(Arrays.asList(new EventType[] { new TransitionWheater() }));

		service.getWheaterStatus(null);
	}

	@Test(expected = WheaterPredictorException.class)
	public void getWheaterStatusPatternCalculationError() throws Exception {
		Mockito.when(wheaterPredictor.predict(Mockito.anyInt())).thenThrow(new PatternCalculationError("msg"));

		service.getWheaterStatus(3);
	}

	@Test(expected = WheaterPredictorException.class)
	public void getWheaterStatusAnyOtherException() throws Exception {
		Mockito.when(wheaterPredictor.predict(Mockito.anyInt())).thenThrow(new RuntimeException("msg"));

		service.getWheaterStatus(3);
	}

	@Test
	public void heathCheckTest() throws Exception {
		HealthCheckResponse healthCheck = service.healthCheck();
		assertNotNull("Invalid healthCheckResponse", healthCheck);
		assertNotNull("Invalid healthCheckResponse name", healthCheck.getName());
		assertNotNull("Invalid healthCheckResponse version", healthCheck.getVersion());
	}

	@Test
	public void keepAliveTest() throws Exception {
		try {
			service.keepAlive();
		} catch (Exception e) {
			fail("Error in keep alive!");
		}
	}

	@Test
	public void versionTest() throws Exception {
		Object version = service.getVersion();
		assertNotNull("Invalid version", version);
	}

}
