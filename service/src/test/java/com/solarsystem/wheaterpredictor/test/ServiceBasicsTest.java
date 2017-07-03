package com.solarsystem.wheaterpredictor.test;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solarsystem.wheaterpredictor.api.dto.HealthCheckResponse;
import com.solarsystem.wheaterpredictor.service.ServiceImplementation;

public class ServiceBasicsTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBasicsTest.class);

	private ServiceImplementation service;
	
	@Before
	public void setUp() {
		service = new ServiceImplementation();
	}

	@Test
	public void basicServiceTestNullVersionFromPackage() throws Exception {
		Field versionField = ServiceImplementation.class.getDeclaredField("version");
		versionField.setAccessible(true);
		versionField.set(service, null);

		LOGGER.debug("Calling service version...");
		Object version = service.getVersion();
		Assert.assertNotNull("Invalid service version response", version);
		LOGGER.debug("Service version: " + version.toString());
	}

	@Test
	public void basicServiceTestNonNullVersionFromPackage() throws Exception {
		Field versionField = ServiceImplementation.class.getDeclaredField("version");
		versionField.setAccessible(true);
		versionField.set(service, "1");

		LOGGER.debug("Calling service version...");
		Object version = service.getVersion();
		Assert.assertNotNull("Invalid service version response", version);
		LOGGER.debug("Service version: " + version.toString());
	}

	@Test
	public void testKeepAlive() throws Exception {
		service.keepAlive();
	}

	@Test
	public void testHealthCheck() {
		Object healthCheckResponse = service.healthCheck();
		Assert.assertNotNull("Invalid health check response", healthCheckResponse);
		Assert.assertEquals("Invalid health check response type", HealthCheckResponse.class,
			healthCheckResponse.getClass());
		HealthCheckResponse healthCheck = (HealthCheckResponse) healthCheckResponse;
		Assert.assertNotNull("No service name", healthCheck.getName());
		Assert.assertNotNull("No service version", healthCheck.getVersion());
	}

}
