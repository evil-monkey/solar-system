package com.solarsystem.wheaterpredictor.test.core.events.wheater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.RelatedWheaterEventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.WheaterEventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;

public class WheaterEventTest extends WheaterEventTypeBaseTest {

	private static class TestWheaterEventType extends WheaterEventType {

		public AtomicInteger obtainPatternCounter;
		public OrbitRelatedUniformEventPattern patternMock;

		public TestWheaterEventType() {
			obtainPatternCounter = new AtomicInteger(0);
			patternMock = Mockito.mock(OrbitRelatedUniformEventPattern.class);
		}

		@Override
		public String getName() {
			return "test wheater event type";
		}

		@Override
		protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {
			obtainPatternCounter.incrementAndGet();
			return patternMock;
		}

	}

	private TestWheaterEventType eventType;

	@Before
	public void setUp() {
		initOrbits();

		eventType = new TestWheaterEventType();

		eventType.setOrbits(orbits);
		assertEquals("Invalid eventType orbits", orbits, eventType.getOrbits());

	}

	@Test
	public void testBasic() throws Exception {
		Integer patternCalculationMaxIterations = eventType.getPatternCalculationMaxIterations();
		assertNotNull("No patternCalculationMaxIterations", patternCalculationMaxIterations);

		Integer patternCalculationMaxIterations2 = patternCalculationMaxIterations - 1;
		eventType.setPatternCalculationMaxIterations(patternCalculationMaxIterations2);
		assertEquals("Wrong patternCalculationMaxIterations", patternCalculationMaxIterations2,
				eventType.getPatternCalculationMaxIterations());

		RelatedWheaterEventType relatedEventType = Mockito.mock(RelatedWheaterEventType.class);
		Collection<RelatedWheaterEventType> relatedEventTypes = new LinkedList<>();
		relatedEventTypes.add(relatedEventType);

		assertNull("Invalid initial relatedEventTypes", eventType.getRelatedEventTypes());
		eventType.setRelatedEventTypes(relatedEventTypes);
		assertEquals("Invalid relatedEventTypes", relatedEventTypes, eventType.getRelatedEventTypes());
	}

	@Test
	public void getPatternTest() {

		assertEquals("Wrong patternCalculation counter initialValue", 0, eventType.obtainPatternCounter.get());
		OrbitRelatedUniformEventPattern pattern = eventType.getPattern();

		assertNotNull("No pattern obtained", pattern);
		assertEquals("Wrong patternCalculation counter initialValue", 1, eventType.obtainPatternCounter.get());
		OrbitRelatedUniformEventPattern pattern2 = eventType.getPattern();

		assertEquals("getPattern is not indempotent", pattern, pattern2);
		assertEquals("Wrong patternCalculation counter initialValue", 1, eventType.obtainPatternCounter.get());

	}

	@Test
	public void basicNoOccurrenceTest() {
		Mockito.when(eventType.patternMock.isDayInPattern(Mockito.anyInt())).thenReturn(false);
		Collection<EventType> events = eventType.occurs(2);

		assertNull("Invalid occurs response", events);
	}

	@Test
	public void basicOccurrenceTest() {
		Mockito.when(eventType.patternMock.isDayInPattern(Mockito.anyInt())).thenReturn(true);
		Collection<EventType> events = eventType.occurs(1);
		assertNotNull("Invalid occurs response", events);
		assertEquals("Invalid occurs response size", 1, events.size());
		EventType event = events.iterator().next();
		assertNotNull("Invalid occurence", event);
		assertEquals("Invalid event type occurrence", eventType.getName(), event.getName());
	}

	@Test
	public void callToRelatedEventsInOccursCallTest() throws Exception {

		Mockito.when(eventType.patternMock.isDayInPattern(Mockito.anyInt())).thenReturn(true);

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

		Mockito.when(eventType.patternMock.isDayInPattern(Mockito.anyInt())).thenReturn(true);

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
		
		Mockito.when(eventType.patternMock.isDayInPattern(Mockito.anyInt())).thenReturn(true);

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
