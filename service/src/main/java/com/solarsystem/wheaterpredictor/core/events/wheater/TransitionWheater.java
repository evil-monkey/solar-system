package com.solarsystem.wheaterpredictor.core.events.wheater;

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
