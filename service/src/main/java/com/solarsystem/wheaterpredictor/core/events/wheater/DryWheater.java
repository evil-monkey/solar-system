package com.solarsystem.wheaterpredictor.core.events.wheater;

import javax.inject.Inject;

import com.solarsystem.wheaterpredictor.core.PolarCoord;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.AngularHelper;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;

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

	public AngularHelper getAngularHelper() {
		return angularHelper;
	}

	public void setAngularHelper(AngularHelper angularHelper) {
		this.angularHelper = angularHelper;
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {
		// Assummes day 0 is first occurence
		Integer firstOccurrence = 0;
		Integer secondOcurrence = null;
		for (int day = 1; day < this.getPatternCalculationMaxIterations(); day++) {
			PolarCoord position = null;
			secondOcurrence = day;
			for (Orbit orbit : this.getOrbits()) {
				if (position == null) {
					position = orbit.calculatePosition(day);
				} else if (angularHelper.areNotSameOrOppositeAzimuth(orbit.calculatePosition(day), position)) {
					secondOcurrence = null;
					break;
				}
			}
			if (secondOcurrence != null) {
				break;
			}
		}

		if (secondOcurrence == null) {
			throw new PatternCalculationError("Can't get a pattern for event.");
		}

		return new OrbitRelatedUniformEventPattern(firstOccurrence, secondOcurrence, 0);

	}

}
