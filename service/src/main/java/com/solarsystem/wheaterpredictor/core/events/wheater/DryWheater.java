package com.solarsystem.wheaterpredictor.core.events.wheater;

import com.solarsystem.wheaterpredictor.core.PolarCoord;
import com.solarsystem.wheaterpredictor.core.events.CyclicEventPattern;
import com.solarsystem.wheaterpredictor.core.events.WheaterEventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;

/**
 * When all planets are aligned with the sun
 * 
 * @author pablo
 *
 */
public final class DryWheater extends WheaterEventType {

	@Override
	public String getName() {
		return "Clima seco";
	}

	@Override
	protected CyclicEventPattern obtainPattern() throws PatternCalculationError {
		// Assummes day 0 is first occurence
		Integer firstOccurrence = 0;
		Integer secondOcurrence = null;
		for (int day = 1; day < SECOND_OCCURRENCE_ITERATIONS_LIMIT; day++) {
			PolarCoord position = null;
			secondOcurrence = day;
			for (Orbit orbit : this.getOrbits()) {
				if (position == null) {
					position = orbit.calculatePosition(day);
				} else if (areNotSameOrOppositeAzimuth(
						orbit.calculatePosition(day), position)) {
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

		return new CyclicEventPattern(firstOccurrence, secondOcurrence, 0);

	}

	private boolean areNotSameOrOppositeAzimuth(PolarCoord position1,
			PolarCoord position2) {
		return position1.getAzimuth().equals(position2.getAzimuth())
				|| Math.abs(position2.getAzimuth() - position1.getAzimuth()) == 180;
	}

}
