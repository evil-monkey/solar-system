package com.solarsystem.wheaterpredictor.test.integration;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.solarsystem.wheaterpredictor.api.dto.WheaterStatus;
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

	@Test
	public void testSequential() throws Exception {
		StringBuilder sb= new StringBuilder();
		for(int i = 0; i < 3700; i++) {
			WheaterStatus wheaterStatus = service.getWheaterStatus(i);
			sb.append("{\"dia\":").append(i).append("\",\"clima\":\"").append(wheaterStatus.getClima()).append("\"},");
		}
		String out = sb.toString();
		assertNotNull("Invalid out", out);
		System.out.println(out.substring(0, out.length()-2));
		
	}
}
