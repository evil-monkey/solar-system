package com.solarsystem.wheaterpredictor.core.helpers.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.PolygonHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

@Component
public class TriangleBasicHelper implements PolygonHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(TriangleBasicHelper.class);

	@Override
	public boolean isPointBetweenVertices(List<RectangularCoord> positions, RectangularCoord point) {

		validatePositions(positions);

		int mainOrientation = getTriangleOrientation(positions);

		boolean result = getTriangleArea(positions) > 0.0d;

		if (result) {
			result = false;
			// A1A2P, A2A3P, A3A1P
			List<RectangularCoord> st = Lists.newArrayList(positions);
			st.set(2, point);
			if (mainOrientation * getTriangleOrientation(st) > 0) {
				st = new ArrayList<>(3);
				st.add(positions.get(1));
				st.add(positions.get(2));
				st.add(point);
				if (mainOrientation * getTriangleOrientation(st) > 0) {
					st = new ArrayList<>(3);
					st.add(positions.get(2));
					st.add(positions.get(0));
					st.add(point);

					result = mainOrientation * getTriangleOrientation(st) > 0;
				}
			}
		} else {
			LOGGER.debug("Triangle is collapsed!");
		}

		return result;
	}

	public void validatePositions(Collection<RectangularCoord> vertices) {

		if (vertices == null) {
			LOGGER.warn("Can't draw a triangle with these positions!");
			throw new PatternCalculationError("Can't draw a triangle with these positions!");
		}

		Set<RectangularCoord> uniqueVertices = new HashSet<>(vertices);
		if (uniqueVertices.size() != 3) {
			LOGGER.warn("Can't draw a triangle with these positions!");
			throw new PatternCalculationError("Can't draw a triangle with these positions!");
		}

	}

	@Override
	public Double getPerimeter(Collection<RectangularCoord> vertices) {

		validatePositions(vertices);

		List<RectangularCoord> v = new ArrayList<>(vertices);

		RectangularCoord p1 = v.get(0);
		RectangularCoord p2 = v.get(1);
		RectangularCoord p3 = v.get(2);

		// p2 -p1
		double side1 = (Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + (Math.pow(p2.getY() - p1.getY(), 2))));
		double side2 = (Math.sqrt(Math.pow(p3.getX() - p2.getX(), 2) + (Math.pow(p3.getY() - p2.getY(), 2))));
		double side3 = (Math.sqrt(Math.pow(p3.getX() - p1.getX(), 2) + (Math.pow(p3.getY() - p1.getY(), 2))));

		return side1 + side2 + side3;
	}

	private int getTriangleOrientation(List<RectangularCoord> vertices) {
		// (A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
		return (vertices.get(0).getX() - vertices.get(2).getX()) * (vertices.get(1).getY() - vertices.get(2).getY())
				- (vertices.get(0).getY() - vertices.get(2).getY())
						* (vertices.get(1).getX() - vertices.get(2).getX()) >= 0 ? 1 : -1;
	}

	private double getTriangleArea(Collection<RectangularCoord> vertices) {

		List<RectangularCoord> v = new ArrayList<>(vertices);

		RectangularCoord p1 = v.get(0);
		RectangularCoord p2 = v.get(1);
		RectangularCoord p3 = v.get(2);
		return Math.abs(
				(p1.getX() - p3.getX()) * (p2.getY() - p1.getY()) - (p1.getX() - p2.getX()) * (p3.getY() - p1.getY()))
				* 0.5;
	}
}
