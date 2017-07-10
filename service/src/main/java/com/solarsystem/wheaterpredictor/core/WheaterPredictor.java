package com.solarsystem.wheaterpredictor.core;

import java.util.Collection;
import java.util.stream.Collectors;

import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.events.wheater.TransitionWheater;
import com.solarsystem.wheaterpredictor.core.events.wheater.WheaterEventType;

public class WheaterPredictor implements Predictor {

	private Collection<WheaterEventType> eventTypes;

	@Override
	public Collection<EventType> predict(Integer day) {

		Collection<EventType> events = eventTypes.parallelStream().map(event -> event.occurs(day))
				.filter(event -> event != null).flatMap(listContainer -> listContainer.stream())
				.collect(Collectors.toSet());

		if (events.size() < 1) {
			events.add(new TransitionWheater());
		}

		return events;
	}

	public Collection<WheaterEventType> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(Collection<WheaterEventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

}
