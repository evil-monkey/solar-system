package com.solarsystem.wheaterpredictor.core.helpers;

import java.util.List;

import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public interface RectHelper {

	boolean allAreAlignedExceptByThePoint(List<RectangularCoord> positions, RectangularCoord point, Double tolerance);

}
