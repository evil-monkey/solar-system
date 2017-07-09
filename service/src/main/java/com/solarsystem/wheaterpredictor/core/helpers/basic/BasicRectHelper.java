package com.solarsystem.wheaterpredictor.core.helpers.basic;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.RectHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

@Component
public class BasicRectHelper implements RectHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicRectHelper.class);

	@Override
	public boolean allAreAlignedExceptByTheSun(List<RectangularCoord> positions, RectangularCoord sun,
			Double tolerance) {

		if (positions.size() < 3) {
			LOGGER.warn("This algorithm requires 3 different positions at least!");
			throw new PatternCalculationError("This algorithm requires 3 different positions at least!");
		}

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
