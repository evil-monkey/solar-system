package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.RectHelper;
import com.solarsystem.wheaterpredictor.core.orbits.CircularOrbit;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class GoodWheater extends WheaterEventType {

	
	@Inject
	private RectHelper rectHelper;

	@Override
	public String getName() {
		return "Buen tiempo!";
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {

		// assumes sun is in solar system center (0,0)
		RectangularCoord sun = new RectangularCoord(0.0d, 0.0d);

		Integer firstOccurrence = null;
		Integer secondOcurrence = null;

		Double tolerance = getTolerance(this.getOrbits());

		for (int day = 1; day < this.getPatternCalculationMaxIterations(); day++) {
			final int predictionDay = day;

			// uses a set to disable repeat position (config, error) then a list
			// to retrieve elements by index later
			List<RectangularCoord> positions = new ArrayList<RectangularCoord>(
					this.getOrbits().stream().map(orbit -> orbit.calculatePosition(predictionDay).getRectangularCoord())
							.collect(Collectors.toSet()));

			if (rectHelper.allAreAlignedExceptByTheSun(positions, sun, tolerance)) {
				if (firstOccurrence == null) {
					firstOccurrence = predictionDay;
				} else {
					secondOcurrence = predictionDay;
					break;
				}
			}

		}

		if (firstOccurrence == null || secondOcurrence == null) {
			throw new PatternCalculationError("Can't get a pattern for event.");
		}

		return new OrbitRelatedUniformEventPattern(firstOccurrence, secondOcurrence, 0);
	}

	private Double getTolerance(Collection<Orbit> orbits) {
		// Defino el calculo de tolerancia de forma arbitraria como el máximo
		// incremento posible
		// en las ordenadas dentro de todo el sistema cuando el ángulo se
		// incrementa en 1º
		// t = Rmax * sen 1
		Optional<CircularOrbit> maxRadius = orbits.stream().map(orbit -> (CircularOrbit) orbit)
				.max(CircularOrbit.radiusComparator);
		return maxRadius.get().getRadius() * Math.sin(Math.toRadians(1.0d));
	}

}
