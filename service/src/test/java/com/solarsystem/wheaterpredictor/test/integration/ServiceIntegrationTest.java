package com.solarsystem.wheaterpredictor.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		String out = IntStream.range(0, 3700).mapToObj(i -> (service.getWheaterStatus(i)))
				.map(status -> status.toString()).collect(Collectors.joining(","));
		assertNotNull("Invalid out", out);
		System.out.println(out);
	}

	@Test
	public void testBulk() throws Exception {
		try {
			service.getPeriodWheaterStatus(-10000, 10000, false);
		} catch (Exception e) {
			fail("Some went wrong!");
		}
	}

	@Test
	public void bulkVsSequential() throws Exception {
		List<WheaterStatus> sequential = IntStream.range(0, 1000).mapToObj(i -> (service.getWheaterStatus(i)))
				.collect(Collectors.toList());
		List<WheaterStatus> bulk = new ArrayList<>(service.getPeriodWheaterStatus(0, 1000, false));
		assertEquals("different sizes", sequential.size(), bulk.size());
	}
}
