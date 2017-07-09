package com.solarsystem.wheaterpredictor.core.events.wheater;

import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;

public final class TransitionWheater extends WheaterEventType {

	@Override
	public String getName() {
		return "Clima de transición";
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() {
		return null;
	}

}
