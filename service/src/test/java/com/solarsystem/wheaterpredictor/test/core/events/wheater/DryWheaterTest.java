package com.solarsystem.wheaterpredictor.test.core.events.wheater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
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
import com.solarsystem.wheaterpredictor.core.events.wheater.DryWheater;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.AngularHelper;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

public class DryWheaterTest extends WheaterEventTypeBaseTest {

	@Mock
	private AngularHelper angularHelper;

	@InjectMocks
	private DryWheater eventType;

	@Before
	public void setUp() {
		eventType = new DryWheater();
		initOrbits();
		eventType.setOrbits(orbits);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void basicNoOccurrenceTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		//process uses n-1 calls by day iteration (n = number of orbits) if first of day wasn' false, if it's false
		//then skip to next day
		//so for 3 orbits this array should give a pattern with first occurrence on day 0 and period = 7
		final Boolean[] occurrences = new Boolean[] { true, true, false, false, false, false, false, false, true, true };
		Mockito.when(
				angularHelper.areNotSameOrOppositeAzimuth(Mockito.any(PolarCoord.class), Mockito.any(PolarCoord.class)))
				.thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});
		
		Collection<EventType> events = eventType.occurs(2);
		assertNull("Invalid occurs response", events);
	}

	@Test
	public void basicOccurrenceTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		//process uses n-1 calls by day iteration (n = number of orbits) if first of day wasn' false, if it's false
		//then skip to next day
		//so for 3 orbits this array should give a pattern with first occurrence on day 0 and period = 7
		final Boolean[] occurrences = new Boolean[] { true, true, false, false, false, false, false, false, true, true };
		Mockito.when(
				angularHelper.areNotSameOrOppositeAzimuth(Mockito.any(PolarCoord.class), Mockito.any(PolarCoord.class)))
				.thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		Collection<EventType> events = eventType.occurs(0);
		assertNotNull("Invalid occurs response", events);
		assertEquals("Invalid occurs response size", 1, events.size());
		EventType event = events.iterator().next();
		assertNotNull("Invalid occurence", event);
		assertEquals("Invalid event type occurrence", eventType.getName(), event.getName());
	}

	@Test
	public void getPatternTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		//process uses n-1 calls by day iteration (n = number of orbits) if first of day wasn' false, if it's false
		//then skip to next day
		//so for 3 orbits this array should give a pattern with first occurrence on day 0 and period = 7
		final Boolean[] occurrences = new Boolean[] { true, true, false, false, false, false, false, false, true, true };
		Mockito.when(
				angularHelper.areNotSameOrOppositeAzimuth(Mockito.any(PolarCoord.class), Mockito.any(PolarCoord.class)))
				.thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		OrbitRelatedUniformEventPattern pattern = eventType.getPattern();
		assertNotNull("No pattern obtained", pattern);
		assertNotNull("No pattern's firstTime", pattern.getFirstOccurrence());
		assertEquals("Invalid pattern's firstTime", 0, pattern.getFirstOccurrence().intValue());
		assertNotNull("No pattern's period", pattern.getPeriod());
		assertEquals("Invalid pattern's period", 7, pattern.getPeriod().intValue());
		assertNotNull("No pattern's extension", pattern.getExtension());
		assertEquals("Invalid pattern's extension", 0, pattern.getExtension().intValue());

	}

	@Test(expected = PatternCalculationError.class)
	public void cantObtainPatternTest() throws Exception {
		eventType.setPatternCalculationMaxIterations(0);
		eventType.occurs(1);
	}

	@Test(expected = PatternCalculationError.class)
	public void cantObtainSecondOccurenceForPatternTest() throws Exception {
		final AtomicInteger iterations = new AtomicInteger(0);
		//process uses n-1 calls by day iteration (n = number of orbits) if first of day wasn' false, if it's false
		//then skip to next day
		//so for 3 orbits this array should give a pattern with first occurrence on day 0 and period = 7
		final Boolean[] occurrences = new Boolean[] { true, true, false, false, false, false, false, false, true, true };
		Mockito.when(
				angularHelper.areNotSameOrOppositeAzimuth(Mockito.any(PolarCoord.class), Mockito.any(PolarCoord.class)))
				.thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		eventType.setPatternCalculationMaxIterations(4);
		eventType.occurs(1);
	}

}
