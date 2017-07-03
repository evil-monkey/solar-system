package com.solarsystem.wheaterpredictor.test.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solarsystem.wheaterpredictor.service.ServiceImplementation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class ServiceIntegrationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIntegrationTest.class);

	@Autowired
	private ServiceImplementation service;
	
	@Test
	public void dummyTest() throws Exception {
		LOGGER.debug("Executing service dummy test...");
		Assert.assertNotNull("Invalid service service", service);
	}
}
