package com.solarsystem.wheaterpredictor.core.helpers;

import java.util.Collection;
import java.util.List;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;

public interface TriangleHelper {

	boolean isSunBetweenPlanets(List<RectangularCoord> positions, RectangularCoord sunPosition);

	Double getPerimeter(Collection<RectangularCoord> vertices);

}
