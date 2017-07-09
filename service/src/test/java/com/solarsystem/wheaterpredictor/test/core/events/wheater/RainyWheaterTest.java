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
import com.solarsystem.wheaterpredictor.core.events.wheater.RainyWheater;
import com.solarsystem.wheaterpredictor.core.events.wheater.RelatedWheaterEventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.TriangleHelper;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class RainyWheaterTest extends WheaterEventTypeBaseTest {

	@Mock
	private TriangleHelper triangleHelper;

	@InjectMocks
	private RainyWheater eventType;

	@Before
	public void setUp() {
		eventType = new RainyWheater();
		initOrbits();
		eventType.setOrbits(orbits);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void basicNoOccurrenceTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		final Boolean[] occurrences = new Boolean[] { false, true, true, true, true, false, false, true };
		Mockito.when(triangleHelper.isSunBetweenPlanets(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class))).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		Collection<EventType> events = eventType.occurs(0);
		assertNull("Invalid occurs response", events);
	}

	@Test
	public void basicOccurrenceTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		final Boolean[] occurrences = new Boolean[] { false, true, true, true, true, false, false, true };
		Mockito.when(triangleHelper.isSunBetweenPlanets(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class))).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		Collection<EventType> events = eventType.occurs(1);
		assertNotNull("Invalid occurs response", events);
		assertEquals("Invalid occurs response size", 1, events.size());
		EventType event = events.iterator().next();
		assertNotNull("Invalid occurence", event);
		assertEquals("Invalid event type occurrence", eventType.getName(), event.getName());
	}

	@Test
	public void getPatternTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		final Boolean[] occurrences = new Boolean[] { false, true, true, true, true, false, false, true };
		Mockito.when(triangleHelper.isSunBetweenPlanets(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class))).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		OrbitRelatedUniformEventPattern pattern = eventType.getPattern();
		assertNotNull("No pattern obtained", pattern);
		assertNotNull("No pattern's firstTime", pattern.getFirstOccurrence());
		assertEquals("Invalid pattern's firstTime", 1, pattern.getFirstOccurrence().intValue());
		assertNotNull("No pattern's period", pattern.getPeriod());
		assertEquals("Invalid pattern's period", 6, pattern.getPeriod().intValue());
		assertNotNull("No pattern's extension", pattern.getExtension());
		assertEquals("Invalid pattern's extension", 3, pattern.getExtension().intValue());

	}

	@Test(expected = PatternCalculationError.class)
	public void cantObtainPatternTest() throws Exception {
		eventType.setPatternCalculationMaxIterations(0);
		eventType.occurs(1);
	}

	@Test(expected = PatternCalculationError.class)
	public void cantObtainSecondOccurenceForPatternTest() throws Exception {
		final AtomicInteger iterations = new AtomicInteger(0);
		final Boolean[] occurrences = new Boolean[] { false, true, true, true, true, false, false, true };
		Mockito.when(triangleHelper.isSunBetweenPlanets(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class))).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		eventType.setPatternCalculationMaxIterations(6);
		eventType.occurs(1);
	}

	@Test
	public void callToRelatedEventsInOccursCallTest() throws Exception {

		final AtomicInteger iterations = new AtomicInteger(0);
		final Boolean[] occurrences = new Boolean[] { false, true, true, true, true, false, false, true };
		Mockito.when(triangleHelper.isSunBetweenPlanets(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class))).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return occurrences[iterations.getAndIncrement()];
					}
				});

		RelatedWheaterEventType relatedEventType = Mockito.mock(RelatedWheaterEventType.class);
		Mockito.doNothing().when(relatedEventType).addPositionsData(Mockito.anyCollectionOf(RectangularCoord.class),
				Mockito.anyInt());
		Mockito.doNothing().when(relatedEventType).calculatePatternFromPositions(Mockito.anyInt());
		Mockito.when(relatedEventType.occurs(Mockito.anyInt())).thenReturn(null);

		eventType.setRelatedEventTypes(new LinkedList<>());
		eventType.getRelatedEventTypes().add(relatedEventType);

		assertNotNull("No pattern", eventType.getPattern());
		Mockito.verify(relatedEventType, Mockito.times(4))
				.addPositionsData(Mockito.anyCollectionOf(RectangularCoord.class), Mockito.anyInt());
		Mockito.verify(relatedEventType, Mockito.times(1)).calculatePatternFromPositions(Mockito.anyInt());

		assertNotNull("No occurs calculated", eventType.occurs(1));
		Mockito.verify(relatedEventType, Mockito.times(1)).occurs(Mockito.anyInt());
		Mockito.verifyNoMoreInteractions(relatedEventType);
	}

}
