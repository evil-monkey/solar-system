package com.solarsystem.wheaterpredictor.core.helpers.basic;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.helpers.RectHelper;

@Component
public class BasicRectHelper implements RectHelper {
	
	@Override
	public boolean allAreAlignedExceptByTheSun(List<RectangularCoord> positions, RectangularCoord sun,
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
