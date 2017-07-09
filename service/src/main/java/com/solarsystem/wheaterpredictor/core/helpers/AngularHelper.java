package com.solarsystem.wheaterpredictor.core.helpers;

import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

public interface AngularHelper {

	boolean areNotSameOrOppositeAzimuth(PolarCoord position1, PolarCoord position2);

}
