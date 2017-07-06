package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;

public class GoodWheater extends WheaterEventType {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoodWheater.class);

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

		for (int day = 1; day < SECOND_OCCURRENCE_ITERATIONS_LIMIT; day++) {
			final int predictionDay = day;

			// uses a set to disable repeat position (config, error) then a list
			// to retrieve elements by index later
			List<RectangularCoord> positions = new ArrayList<RectangularCoord>(
					this.getOrbits().stream().map(orbit -> orbit.calculatePosition(predictionDay).getRectangularCoord())
							.collect(Collectors.toSet()));

			if (positions.size() < 3) {
				LOGGER.warn("This algorithm requires 3 different positions at least!");
				throw new PatternCalculationError("This algorithm requires 3 different positions at least!");
			}

			if (allAreAlignedExceptByTheSun(positions, sun, tolerance)) {
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

	private boolean allAreAlignedExceptByTheSun(List<RectangularCoord> positions, RectangularCoord sun,
			Double tolerance) {

		boolean aligned = true;

		Iterator<RectangularCoord> positionsIterator = positions.iterator();

		// take first 2 positions and calculate the slope
		RectangularCoord from = positionsIterator.next();
		Double slope = getSlope(from, positionsIterator.next());

		// discard sun is aligned
		if (!isAligned(sun, slope, from, tolerance)) {
			// check the rest of planet positions
			while (positionsIterator.hasNext()) {
				if (!isAligned(positionsIterator.next(), slope, from, tolerance)) {
					aligned = false;
					break;
				}
			}

		} else {
			aligned = false;
		}

		return aligned;
	}

	private Double getTolerance(Collection<Orbit> orbits) {
		// Defino el calculo de tolerancia de forma arbitraria como el másimo
		// incremento posible
		// en las ordenadas dentro de todo el sistema cuando el ángulo se
		// incrementa en 1º
		// t = Rmax * sen 1
		Optional<Integer> maxRadius = orbits.stream().map(orbit -> orbit.getInitialPosition().getRadius())
				.max(Integer::max);
		try {
			return maxRadius.get() * Math.sin(1);
		} catch (NoSuchElementException nsee) {
			throw new PatternCalculationError("Can't calculate tolerance!");
		}
	}

	private boolean isAligned(RectangularCoord to, Double slope, RectangularCoord from, Double tolerance) {
		// y-y0 = m (x-x0) -> y = m (x-x0) + y0
		Double expectedY = slope * (to.getX() - from.getX()) + from.getY();

		double difference = Math.abs(expectedY - to.getY());

		return difference <= tolerance;

	}

	private Double getSlope(RectangularCoord from, RectangularCoord to) {
		return ((double) (to.getY() - from.getY())) / (to.getX() - from.getX());
	}

}
