package com.solarsystem.wheaterpredictor.core.helpers.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.TriangleHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

@Component
public class TriangleBasicHelper implements TriangleHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(TriangleBasicHelper.class);

	@Override
	public boolean isSunBetweenPlanets(List<RectangularCoord> positions, RectangularCoord sunPosition) {
		
		if (positions == null || positions.size() != 3) {
			LOGGER.warn("Only 3 planets determines a rainy wheater!");
			throw new PatternCalculationError("Only 3 planets determines a rainy wheater!");
		}

		int mainOrientation = getTriangleOrientation(positions);

		if (positions.size() < 3) {
			LOGGER.warn("Can't draw a triangle with these position!");
			throw new PatternCalculationError("Can't draw a triangle with these position!");
		}

		boolean result = false;

		// A1A2P, A2A3P, A3A1P
		List<RectangularCoord> st = Lists.newArrayList(positions);
		st.set(2, sunPosition);
		if (mainOrientation * getTriangleOrientation(st) > 0) {
			st = new ArrayList<>(3);
			st.addAll(positions.subList(1, 2));
			st.add(sunPosition);
			if (mainOrientation * getTriangleOrientation(st) > 0) {
				st = new ArrayList<>(3);
				st.add(positions.get(2));
				st.add(positions.get(0));
				st.add(sunPosition);

				result = mainOrientation * getTriangleOrientation(st) > 0;
			}
		}

		return result;
	}

	@Override
	public Double getPerimeter(Collection<RectangularCoord> vertices) {

		List<RectangularCoord> v = new ArrayList<>(vertices);
		return Math
				.sqrt(Math.pow((v.get(1).getX() - v.get(0).getX()), 2)
						* Math.pow((v.get(1).getY() - v.get(0).getY()), 2))
				+ Math.sqrt(Math.pow((v.get(2).getX() - v.get(1).getX()), 2)
						* Math.pow((v.get(2).getY() - v.get(1).getY()), 2))
				+ Math.sqrt(Math.pow((v.get(2).getX() - v.get(0).getX()), 2)
						* Math.pow((v.get(2).getY() - v.get(0).getY()), 2));
	}

	private int getTriangleOrientation(List<RectangularCoord> vertices) {
		// (A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
		return (vertices.get(0).getX() - vertices.get(2).getX()) * (vertices.get(1).getY() - vertices.get(2).getY())
				- (vertices.get(0).getY() - vertices.get(2).getY())
						* (vertices.get(1).getX() - vertices.get(2).getX()) >= 0 ? 1 : -1;
	}
}
