package com.solarsystem.wheaterpredictor.core.orbits;

import java.util.Comparator;

import com.solarsystem.wheaterpredictor.core.PolarCoord;

public class CircularOrbit extends Orbit {

	public static Comparator<CircularOrbit> radiusComparator = new Comparator<CircularOrbit>() {

		@Override
		public int compare(CircularOrbit o1, CircularOrbit o2) {
			int result = 0;
			if (o1 == null || o1.getRadius() == null) {
				result = -1;
			} else if (o2 == null || o2.getRadius() == null) {
				result = 1;
			} else {
				result = o1.getRadius().compareTo(o2.getRadius());
			}
			return result;
		}
	};
	
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
	public Integer getRadius() {
		return this.getInitialPosition().getRadius();
	}

	@Override
	public PolarCoord calculatePosition(int day) {

		return new PolarCoord(initialPosition.getRadius(),
				(initialPosition.getAzimuth() + this.angularSpeed * day) % 360);
	}

}
