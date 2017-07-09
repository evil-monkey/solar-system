package com.solarsystem.wheaterpredictor.test.core.events.wheater;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.events.wheater.RelatedWheaterEventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class RelatedWheaterEventTypeTest {

	private static class TestRelatedWheaterEventType extends RelatedWheaterEventType {

		@Override
		public String getName() {
			return "test related event type";
		}

		@Override
		protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {
			return null;
		}

		@Override
		public void calculatePatternFromPositions(Integer period) throws PatternCalculationError {

		}

		@Override
		public void addPositionsData(Collection<RectangularCoord> positions, Integer day) {

		}
	}

	private TestRelatedWheaterEventType eventType;

	@Before
	public void setUp() {

		eventType = new TestRelatedWheaterEventType();

	}

	@Test
	public void testName() throws Exception {
		assertNotNull("No name for event", eventType.getName());
	}
}
