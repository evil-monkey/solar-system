package com.solarsystem.wheaterpredictor.core.orbits;

import com.solarsystem.wheaterpredictor.core.PolarCoord;

public abstract class Orbit {

	protected PolarCoord initialPosition;

	protected String name;

	public PolarCoord getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(PolarCoord initialPosition) {
		this.initialPosition = initialPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract PolarCoord calculatePosition(int day);
}
