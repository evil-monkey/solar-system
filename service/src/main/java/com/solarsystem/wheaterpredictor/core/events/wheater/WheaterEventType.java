package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.solarsystem.wheaterpredictor.core.events.CyclicEventPattern;
import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;

public abstract class WheaterEventType implements EventType {

	protected static final int SECOND_OCCURRENCE_ITERATIONS_LIMIT = 3653;

	private CyclicEventPattern pattern;
	private Collection<Orbit> orbits;
	private Collection<WheaterEventType> relatedEventTypes;

	/**
	 * WheaterEventType knows if it occurs in a specific day
	 * 
	 * @param day
	 * @return
	 * @throws PatternCalculationError
	 */
	@Override
	public Collection<EventType> occurs(Integer day) {

		if (pattern == null) {
			pattern = obtainPattern();
		}

		List<EventType> events = null;

		if (pattern.isDayInPattern(day)) {
			events = new LinkedList<>();
			events.add(this);
			if (this.getRelatedEventTypes() != null) {
				events.addAll(this.getRelatedEventTypes().stream().map(relatedEvent -> relatedEvent.occurs(day))
						.filter(relatedEvent -> relatedEvent != null).flatMap(listContainer -> listContainer.stream())
						.collect(Collectors.toList()));
			}
		}

		return events;
	}

	protected abstract CyclicEventPattern obtainPattern() throws PatternCalculationError;

	public Collection<Orbit> getOrbits() {
		return orbits;
	}

	public void setOrbits(Collection<Orbit> orbits) {
		this.orbits = orbits;
	}

	public Collection<WheaterEventType> getRelatedEventTypes() {
		return relatedEventTypes;
	}

	public void setRelatedEventTypes(Collection<WheaterEventType> relatedEventTypes) {
		this.relatedEventTypes = relatedEventTypes;
	}
}
