package com.solarsystem.wheaterpredictor.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.solarsystem.wheaterpredictor.core.WheaterPredictor;
import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.WheaterEventType;

public class WheaterPredictorTest {

	private WheaterPredictor wheaterPredictor;

	@Before
	public void setUp() {

		wheaterPredictor = new WheaterPredictor();
		Collection<WheaterEventType> eventTypes = new LinkedList<>();
		wheaterPredictor.setEventTypes(eventTypes);
	}

	@Test
	public void predictTest() {
		WheaterEventType eventType1 = Mockito.mock(WheaterEventType.class);
		Mockito.when(eventType1.getName()).thenReturn("event1");
		List<EventType> response1 = Arrays.asList(new EventType[] { eventType1 });
		Mockito.when(eventType1.occurs(Mockito.anyInt())).thenReturn(null);
		Mockito.when(eventType1.occurs(Mockito.eq(0))).thenReturn(response1);
		Mockito.when(eventType1.occurs(Mockito.eq(25))).thenReturn(response1);

		WheaterEventType eventType2 = Mockito.mock(WheaterEventType.class);
		Mockito.when(eventType2.getName()).thenReturn("event2");
		List<EventType> response2 = Arrays.asList(new EventType[] { eventType2 });
		EventType subevent1 = Mockito.mock(EventType.class);
		Mockito.when(subevent1.getName()).thenReturn("subevent1");
		Mockito.when(eventType2.occurs(Mockito.anyInt())).thenAnswer(new Answer<Collection<EventType>>() {

			List<Integer> occurrences = Arrays.asList(new Integer[] { 15, 16, 17, 18, 19 });
			Integer peak = 18;

			@Override
			public Collection<EventType> answer(InvocationOnMock invocation) throws Throwable {
				Integer day = invocation.getArgumentAt(0, Integer.class);
				Collection<EventType> response = null;

				if (occurrences.contains(day)) {
					
					response = new LinkedList<>();
					response.add(eventType2);

					if (day == peak) {
						response.add(subevent1);
					}
				}
				return response;
			}
		});

		WheaterEventType eventType3 = Mockito.mock(WheaterEventType.class);
		Mockito.when(eventType3.getName()).thenReturn("event3");
		List<EventType> response3 = Arrays.asList(new EventType[] { eventType3 });
		Mockito.when(eventType3.occurs(Mockito.anyInt())).then(new Answer<Collection<EventType>>() {

			@Override
			public Collection<EventType> answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArgumentAt(0, Integer.class) % 2 > 0 ? response3 : null;
			}
		});

		wheaterPredictor.getEventTypes().add(eventType1);
		wheaterPredictor.getEventTypes().add(eventType2);
		wheaterPredictor.getEventTypes().add(eventType3);

		for (int i = 0; i < 31; i++) {
			Collection<EventType> predicted = wheaterPredictor.predict(i);
			System.out.printf("Day %d: %s\n", i,
					String.join(", ", predicted.stream().map(event -> event.getName()).collect(Collectors.toList())));
		}

		Mockito.verify(eventType1, Mockito.times(31)).occurs(Mockito.anyInt());
		Mockito.verify(eventType2, Mockito.times(31)).occurs(Mockito.anyInt());
		Mockito.verify(eventType3, Mockito.times(31)).occurs(Mockito.anyInt());
	}

}
