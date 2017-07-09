package com.solarsystem.wheaterpredictor.core.orbits;

import java.util.Comparator;

public class CircularOrbit extends Orbit {

	public static Comparator<CircularOrbit> radiusComparator = new Comparator<CircularOrbit>() {

		@Override
		public int compare(CircularOrbit o1, CircularOrbit o2) {
			int result = 0;
			if (o1 == null) {
				result = -1;
			} else if (o2 == null) {
				result = 1;
			} else {
				result = o1.getRadius().compareTo(o2.getRadius());
			}
			return result;
		}
	};

	/**
	 * Angular speed in degrees by day
	 */
	private int angularSpeed;

	public CircularOrbit() {
		super();
	}

	public CircularOrbit(int angularSpeed, PolarCoord initialPosition) {
		super();
		this.setAngularSpeed(angularSpeed);
		this.setInitialPosition(initialPosition);
	}

	/**
	 * 
	 * @return angular speed in degrees by day
	 */
	public int getAngularSpeed() {
		return angularSpeed;
	}

	/**
	 * Set angular speed in degrees by day
	 * 
	 * @param angularSpeed
	 */
	public void setAngularSpeed(int angularSpeed) {
		this.angularSpeed = angularSpeed;
	}

	/**
	 * for CircularOrbits radius is constant and it comes from initial position
	 * 
	 * @return
	 */
	public Integer getRadius() {
		return this.getInitialPosition().getRadius();
	}

	@Override
	public PolarCoord calculatePosition(int day) {

		return new PolarCoord(initialPosition.getRadius(),
				(initialPosition.getAzimuth() + this.getAngularSpeed() * day) % 360);
	}

}
