package com.solarsystem.wheaterpredictor.core.orbits;

import com.solarsystem.wheaterpredictor.core.PolarCoord;

public class CircularOrbit extends Orbit {

	private int angularSpeed;

	public int getAngularSpeed() {
		return angularSpeed;
	}

	public void setAngularSpeed(int angularSpeed) {
		this.angularSpeed = angularSpeed;
	}

	/**
	 * for CircularOrbits radius is constant and it comes from initial position
	 * 
	 * @return
	 */
	public int getRadius() {
		return this.getInitialPosition().getRadius();
	}

	@Override
	public PolarCoord calculatePosition(int day) {

		return new PolarCoord(initialPosition.getRadius(),
				(initialPosition.getAzimuth() + this.angularSpeed * day) % 360);
	}

}
