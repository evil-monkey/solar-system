package com.solarsystem.wheaterpredictor.test.core.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.GoodWheater;

public class GoodWheaterTest extends WheaterEventTypeBaseTest {

	private GoodWheater eventType;

	@Before
	public void setUp() {
		eventType = new GoodWheater();
		initOrbits();
		eventType.setOrbits(orbits);
	}

	@Test
	public void bruteForceTest() {

		Collection<EventType> events = eventType.occurs(0);
		assertNotNull("Invalid occurs response", events);
		assertEquals("Invalid occurs response size", 1, events.size());
		assertNull("This event doesn't occurs on day 0", events.iterator().next());

	}

}
