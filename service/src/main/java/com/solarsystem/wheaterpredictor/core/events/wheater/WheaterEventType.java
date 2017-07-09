package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;

public abstract class WheaterEventType implements EventType {

	private static final int SECOND_OCCURRENCE_ITERATIONS_LIMIT = 3653;

	private OrbitRelatedUniformEventPattern pattern;
	private Collection<Orbit> orbits;
	private Collection<RelatedWheaterEventType> relatedEventTypes;
	private Integer patternCalculationMaxIterations;
	
	/**
	 * WheaterEventType knows if it occurs in a specific day
	 * 
	 * @param day
	 * @return
	 * @throws PatternCalculationError
	 */
	@Override
	public Collection<EventType> occurs(Integer day) {

		List<EventType> events = null;

		if (getPattern().isDayInPattern(day)) {
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

	/**
	 * Synchronized to calculate the pattern just once
	 * @return
	 */
	public synchronized OrbitRelatedUniformEventPattern getPattern() {
		if (pattern == null) {
			pattern = obtainPattern();
		}
		return pattern;
	}

	protected abstract OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError;

	public Collection<Orbit> getOrbits() {
		return orbits;
	}

	public void setOrbits(Collection<Orbit> orbits) {
		this.orbits = orbits;
	}

	public Collection<RelatedWheaterEventType> getRelatedEventTypes() {
		return relatedEventTypes;
	}

	public void setRelatedEventTypes(Collection<RelatedWheaterEventType> relatedEventTypes) {
		this.relatedEventTypes = relatedEventTypes;
	}

	public Integer getPatternCalculationMaxIterations() {
		if (patternCalculationMaxIterations == null) {
			patternCalculationMaxIterations = SECOND_OCCURRENCE_ITERATIONS_LIMIT;
		}
		return patternCalculationMaxIterations;
	}

	public void setPatternCalculationMaxIterations(Integer patternCalculationMaxIterations) {
		this.patternCalculationMaxIterations = patternCalculationMaxIterations;
	}
}
