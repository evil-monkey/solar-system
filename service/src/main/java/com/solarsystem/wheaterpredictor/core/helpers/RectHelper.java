package com.solarsystem.wheaterpredictor.core.helpers;

import java.util.List;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;

public interface RectHelper {

	boolean allAreAlignedExceptByTheSun(List<RectangularCoord> positions, RectangularCoord sun, Double tolerance);

}
