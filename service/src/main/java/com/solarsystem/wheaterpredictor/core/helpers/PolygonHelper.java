package com.solarsystem.wheaterpredictor.core.helpers;

import java.util.Collection;
import java.util.List;

import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public interface PolygonHelper {

	boolean isPointBetweenVertices(List<RectangularCoord> positions, RectangularCoord point);

	Double getPerimeter(Collection<RectangularCoord> vertices);

}
