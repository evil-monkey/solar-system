package com.solarsystem.wheaterpredictor.core.events;

import java.util.Collection;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;

public abstract class WheaterEventType implements EventType {

	protected static final int SECOND_OCCURRENCE_ITERATIONS_LIMIT = 3653;

	private CyclicEventPattern pattern;
	private Collection<Orbit> orbits;

	/**
	 * WheaterEventType knows if it occurs in a specific day
	 * 
	 * @param day
	 * @return
	 * @throws PatternCalculationError 
	 */
	public EventType occurs(Integer day) throws PatternCalculationError {

		if (pattern == null) {
			pattern = obtainPattern();
		}

		return pattern.isDayInPattern(day) ? this : null;
	}

	protected abstract CyclicEventPattern obtainPattern() throws PatternCalculationError;

	public Collection<Orbit> getOrbits() {
		return orbits;
	}

	public void setOrbits(Collection<Orbit> orbits) {
		this.orbits = orbits;
	}
}
