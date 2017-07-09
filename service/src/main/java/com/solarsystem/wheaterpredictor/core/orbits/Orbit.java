package com.solarsystem.wheaterpredictor.core.orbits;

public abstract class Orbit {

	/**
	 * initial position (reference)
	 */
	protected PolarCoord initialPosition;

	/**
	 * orbit identify
	 */
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

	/**
	 * calculates position for a particular day
	 * @param day
	 * @return {@link PolarCoord}
	 */
	public abstract PolarCoord calculatePosition(int day);
}
