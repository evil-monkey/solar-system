package com.solarsystem.wheaterpredictor.test.core.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.GoodWheater;
import com.solarsystem.wheaterpredictor.core.events.wheater.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.events.wheater.RelatedWheaterEventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.RectHelper;

public class WheaterEventTest extends WheaterEventTypeBaseTest {

	@Mock
	private RectHelper rectHelper;

	@Spy
	@InjectMocks
	private GoodWheater eventType;

	@Before
	public void setUp() {
		eventType = new GoodWheater();
		initOrbits();
		eventType.setOrbits(orbits);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void basicNoOccurrenceTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return iterations.getAndIncrement() % 5 < 1;
					}
				});

		Collection<EventType> events = eventType.occurs(2);
		assertNull("Invalid occurs response", events);
	}

	@Test
	public void basicOccurrenceTest() {
		final AtomicInteger iterations = new AtomicInteger(0);
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return iterations.getAndIncrement() % 5 < 1;
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
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return iterations.getAndIncrement() % 5 < 1;
					}
				});

		OrbitRelatedUniformEventPattern pattern = eventType.getPattern();
		assertNotNull("No pattern obtained", pattern);
		assertNotNull("No pattern's firstTime", pattern.getFirstOccurrence());
		assertEquals("Invalid pattern's firstTime", 1, pattern.getFirstOccurrence().intValue());
		assertNotNull("No pattern's period", pattern.getPeriod());
		assertEquals("Invalid pattern's period", 5, pattern.getPeriod().intValue());
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
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenAnswer(new Answer<Boolean>() {

					@Override
					public Boolean answer(InvocationOnMock invocation) throws Throwable {
						return iterations.getAndIncrement() % 5 < 1;
					}
				});

		eventType.setPatternCalculationMaxIterations(6);
		eventType.occurs(1);
	}

	@Test
	public void noRecalculatePAtternTest() throws Exception {
		// returns true always, so it took just two iterations in GoodWheater to
		// calculate pattern so
		// just two calls to rectHelper.allAreAlignedExceptByTheSun are being
		// expected
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenReturn(true);

		Mockito.verifyZeroInteractions(rectHelper);
		eventType.getPattern();
		Mockito.verify(rectHelper, Mockito.times(2)).allAreAlignedExceptByTheSun(
				Mockito.anyListOf(RectangularCoord.class), Mockito.any(RectangularCoord.class), Mockito.anyDouble());
		Mockito.verifyNoMoreInteractions(rectHelper);
		eventType.getPattern();
		Mockito.verifyNoMoreInteractions(rectHelper);
	}

	@Test
	public void callToRelatedEventsInOccursCallTest() throws Exception {
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenReturn(true);

		RelatedWheaterEventType relatedEventType = Mockito.mock(RelatedWheaterEventType.class);
		Mockito.when(relatedEventType.occurs(Mockito.anyInt())).thenReturn(null);

		eventType.setRelatedEventTypes(new LinkedList<>());
		eventType.getRelatedEventTypes().add(relatedEventType);

		assertNotNull("No pattern", eventType.getPattern());
		Mockito.verifyZeroInteractions(relatedEventType);

		assertNotNull("No occurs calculated", eventType.occurs(1));
		Mockito.verify(relatedEventType, Mockito.times(1)).occurs(Mockito.anyInt());
		Mockito.verifyNoMoreInteractions(relatedEventType);
	}

	@Test
	public void withRelatedEventsOccurrenceOccursCallTest() throws Exception {
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenReturn(true);

		RelatedWheaterEventType relatedEventType = Mockito.mock(RelatedWheaterEventType.class);
		Collection<EventType> relatedEventsOccurrences = new LinkedList<>();
		relatedEventsOccurrences.add(relatedEventType);
		Mockito.when(relatedEventType.occurs(Mockito.anyInt())).thenReturn(relatedEventsOccurrences);

		eventType.setRelatedEventTypes(new LinkedList<>());
		eventType.getRelatedEventTypes().add(relatedEventType);

		Collection<EventType> result = eventType.occurs(1);
		assertNotNull("No occurs calculated", result);
		assertEquals("Wrong result size", 2, result.size());
		assertTrue("Result doesn't contain main event", result.contains(eventType));
		assertTrue("Result doesn't contain related event", result.contains(relatedEventType));

	}

	@Test
	public void withoutRelatedEventsOccurrenceOccursCallTest() throws Exception {
		Mockito.when(rectHelper.allAreAlignedExceptByTheSun(Mockito.anyListOf(RectangularCoord.class),
				Mockito.any(RectangularCoord.class), Mockito.anyDouble())).thenReturn(true);

		RelatedWheaterEventType relatedEventType = Mockito.mock(RelatedWheaterEventType.class);
		Mockito.when(relatedEventType.occurs(Mockito.anyInt())).thenReturn(null);

		eventType.setRelatedEventTypes(new LinkedList<>());
		eventType.getRelatedEventTypes().add(relatedEventType);

		eventType.setRelatedEventTypes(new LinkedList<>());
		eventType.getRelatedEventTypes().add(relatedEventType);

		Collection<EventType> result = eventType.occurs(1);
		assertNotNull("No occurs calculated", result);
		assertEquals("Wrong result size", 1, result.size());
		assertTrue("Result doesn't contain main event", result.contains(eventType));
		
	}
}
