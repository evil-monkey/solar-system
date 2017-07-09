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

		Iterator<RectangularCoord> positionsIterator = positions.iterator();

		// take first 2 positions and calculate the slope
		RectangularCoord from = positionsIterator.next();
		RectangularCoord second = positionsIterator.next();

		double verticalTolerance = tolerance / 4;

		boolean aligned = true;

		if (areVerticallyAligned(second, from, verticalTolerance)) {
			LOGGER.debug(
					String.format("Choosing Vertical checking mode [%s, %s, %f].", second, from, verticalTolerance));

			aligned = verticalChecking(sun, positionsIterator, from, verticalTolerance);
		} else {
			Double slope = getSlope(from, second);
			LOGGER.debug(String.format("Choosing NonVertical checking mode [%s, %s, %f, %f].", second, from, tolerance,
					slope));

			aligned = nonVerticalChecking(sun, tolerance, positionsIterator, from, slope);
		}

		return aligned;
	}

	public boolean verticalChecking(RectangularCoord sun, Iterator<RectangularCoord> positionsIterator,
			RectangularCoord from, double verticalTolerance) {

		boolean aligned = true;
		// discard sun is aligned
		if (!areVerticallyAligned(sun, from, verticalTolerance)) {
			// check the rest of planet positions
			while (positionsIterator.hasNext()) {
				if (!areVerticallyAligned(positionsIterator.next(), from, verticalTolerance)) {
					aligned = false;
					break;
				}
			}
		} else {
			// by the sun
			aligned = false;
			LOGGER.debug(
					String.format("Vertically aligned with the sun [%s, %s, %f].", sun, from, verticalTolerance));
		}
		return aligned;
	}

	public boolean nonVerticalChecking(RectangularCoord sun, Double tolerance,
			Iterator<RectangularCoord> positionsIterator, RectangularCoord from, Double slope) {

		boolean aligned = true;
		// discard sun is aligned
		if (!areAligned(sun, slope, from, tolerance)) {
			// check the rest of planet positions
			while (positionsIterator.hasNext()) {
				if (!areAligned(positionsIterator.next(), slope, from, tolerance)) {
					aligned = false;
					break;
				}
			}

		} else {
			// by the sun
			aligned = false;
			LOGGER.debug(String.format("Aligned with the sun [%s, %s, %f, %f].", sun, from, tolerance, slope));
		}
		return aligned;
	}

	public boolean areVerticallyAligned(RectangularCoord to, RectangularCoord from, Double tolerance) {
		return Math.abs(to.getX() - from.getX()) <= tolerance;
	}

	private boolean areAligned(RectangularCoord to, Double slope, RectangularCoord from, Double tolerance) {
		// y-y0 = m (x-x0) -> y = m (x-x0) + y0
		Double expectedY = slope * (to.getX() - from.getX()) + from.getY();

		double difference = Math.abs(expectedY - to.getY());

		return difference <= tolerance;

	}

	private Double getSlope(RectangularCoord from, RectangularCoord to) {
		return ((double) (to.getY() - from.getY())) / (to.getX() - from.getX());
	}
}
