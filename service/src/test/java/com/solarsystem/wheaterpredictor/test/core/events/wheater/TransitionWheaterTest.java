package com.solarsystem.wheaterpredictor.test.core.events.wheater;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.events.wheater.TransitionWheater;

public class TransitionWheaterTest {

	private TransitionWheater eventType;

	@Before
	public void setUp() {
		eventType = new TransitionWheater();
	}

	@Test
	public void basicTest() throws Exception {
		assertNotNull("No name", eventType.getName());
		assertNull("Unnecesary pattern, this is a dummy event!", eventType.getPattern());
	}

}
