package com.solarsystem.wheaterpredictor.core.events.wheater;

import javax.inject.Inject;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.AngularHelper;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

/**
 * When all planets are aligned with the sun
 * 
 * @author pablo
 *
 */
public final class DryWheater extends WheaterEventType {

	@Inject
	private AngularHelper angularHelper;

	@Override
	public String getName() {
		return "Clima seco";
	}

	public void setAngularHelper(AngularHelper angularHelper) {
		this.angularHelper = angularHelper;
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {
		//assumes sun is the center
		Integer firstOccurrence = null;
		Integer secondOcurrence = null;

		daybreak: for (int day = 0; day < this.getPatternCalculationMaxIterations(); day++) {
			PolarCoord position = null;
			for (Orbit orbit : this.getOrbits()) {
				if (position == null) {
					// first orbit position to calculate for current date
					position = orbit.calculatePosition(day);
				} else if (!angularHelper.areNotSameOrOppositeAzimuth(orbit.calculatePosition(day), position)) {
					//some are not aligned
					continue daybreak;
				}
			}
			//if it reach this is because all positions are aligned with sun
			if (firstOccurrence == null) {
				firstOccurrence = day;
			} else if (secondOcurrence == null) {
				secondOcurrence = day;
				break daybreak;
			}
		}

		if (firstOccurrence == null || secondOcurrence == null) {
			throw new PatternCalculationError("Can't get a pattern for event.");
		}

		return new OrbitRelatedUniformEventPattern(firstOccurrence, secondOcurrence, 0);

	}

}
