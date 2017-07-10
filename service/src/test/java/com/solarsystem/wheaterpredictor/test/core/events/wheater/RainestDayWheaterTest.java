package com.solarsystem.wheaterpredictor.test.core.events.wheater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.RainestDayWheater;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.PolygonHelper;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class RainestDayWheaterTest extends WheaterEventTypeBaseTest {

	@Mock
	private PolygonHelper triangleHelper;

	@InjectMocks
	private RainestDayWheater eventType;

	@Before
	public void setUp() {
		eventType = new RainestDayWheater();
		MockitoAnnotations.initMocks(this);
		assertNull("Defined Orbits...! It shouldn´t needed!", eventType.getOrbits());

	}

	@Test
	public void basicNoOccurrenceTest() {

		eventType.addPositionsData(new LinkedList<>(), 28);
		eventType.addPositionsData(new LinkedList<>(), 29);
		eventType.addPositionsData(new LinkedList<>(), 30);
		eventType.addPositionsData(new LinkedList<>(), 31);

		final AtomicInteger iterations = new AtomicInteger(0);
		final Double[] perimeters = new Double[] { 200.0d, 250.0d, 300.0d, 275.0d };
		Mockito.when(triangleHelper.getPerimeter(Mockito.anyListOf(RectangularCoord.class)))
				.thenAnswer(new Answer<Double>() {

					@Override
					public Double answer(InvocationOnMock invocation) throws Throwable {
						return perimeters[iterations.getAndIncrement()];
					}
				});

		eventType.calculatePatternFromPositions(100);

		Collection<EventType> events = eventType.occurs(28);
		assertNull("Invalid occurs response", events);
	}

	@Test
	public void basicOccurrenceTest() {
		eventType.addPositionsData(new LinkedList<>(), 28);
		eventType.addPositionsData(new LinkedList<>(), 29);
		eventType.addPositionsData(new LinkedList<>(), 30);
		eventType.addPositionsData(new LinkedList<>(), 31);

		final AtomicInteger iterations = new AtomicInteger(0);
		final Double[] perimeters = new Double[] { 200.0d, 250.0d, 300.0d, 275.0d };
		Mockito.when(triangleHelper.getPerimeter(Mockito.anyListOf(RectangularCoord.class)))
				.thenAnswer(new Answer<Double>() {

					@Override
					public Double answer(InvocationOnMock invocation) throws Throwable {
						return perimeters[iterations.getAndIncrement()];
					}
				});

		eventType.calculatePatternFromPositions(100);

		Collection<EventType> events = eventType.occurs(30);
		assertNotNull("Invalid occurs response", events);
		assertEquals("Invalid occurs response size", 1, events.size());
		EventType event = events.iterator().next();
		assertNotNull("Invalid occurence", event);
		assertEquals("Invalid event type occurrence", eventType.getName(), event.getName());

		events = eventType.occurs(130);
		assertNotNull("Invalid occurs response", events);
		assertEquals("Invalid occurs response size", 1, events.size());
		event = events.iterator().next();
		assertNotNull("Invalid occurence", event);
		assertEquals("Invalid event type occurrence", eventType.getName(), event.getName());

	}

	@Test
	public void getPatternTest() {
		eventType.addPositionsData(new LinkedList<>(), 28);
		eventType.addPositionsData(new LinkedList<>(), 29);
		eventType.addPositionsData(new LinkedList<>(), 30);
		eventType.addPositionsData(new LinkedList<>(), 31);

		final AtomicInteger iterations = new AtomicInteger(0);
		final Double[] perimeters = new Double[] { 200.0d, 250.0d, 300.0d, 275.0d };
		Mockito.when(triangleHelper.getPerimeter(Mockito.anyListOf(RectangularCoord.class)))
				.thenAnswer(new Answer<Double>() {

					@Override
					public Double answer(InvocationOnMock invocation) throws Throwable {
						return perimeters[iterations.getAndIncrement()];
					}
				});

		eventType.calculatePatternFromPositions(100);

		OrbitRelatedUniformEventPattern pattern = eventType.getPattern();
		assertNotNull("No pattern obtained", pattern);
		assertNotNull("No pattern's firstTime", pattern.getFirstOccurrence());
		assertEquals("Invalid pattern's firstTime", 30, pattern.getFirstOccurrence().intValue());
		assertNotNull("No pattern's period", pattern.getPeriod());
		assertEquals("Invalid pattern's period", 100, pattern.getPeriod().intValue());
		assertNotNull("No pattern's extension", pattern.getExtension());
		assertEquals("Invalid pattern's extension", 0, pattern.getExtension().intValue());

	}

	@Test(expected = PatternCalculationError.class)
	public void cantObtainPatterNoDataToProcessTest() throws Exception {
		eventType.calculatePatternFromPositions(4);
	}

	@Test(expected = PatternCalculationError.class)
	public void cantObtainPatternInvalidPeriodTest() throws Exception {
		eventType.addPositionsData(new LinkedList<>(), 28);
		eventType.addPositionsData(new LinkedList<>(), 29);
		eventType.addPositionsData(new LinkedList<>(), 30);
		eventType.addPositionsData(new LinkedList<>(), 31);

		final AtomicInteger iterations = new AtomicInteger(0);
		final Double[] perimeters = new Double[] { 200.0d, 250.0d, 300.0d, 275.0d };
		Mockito.when(triangleHelper.getPerimeter(Mockito.anyListOf(RectangularCoord.class)))
				.thenAnswer(new Answer<Double>() {

					@Override
					public Double answer(InvocationOnMock invocation) throws Throwable {
						return perimeters[iterations.getAndIncrement()];
					}
				});

		eventType.calculatePatternFromPositions(0);
	}

}
