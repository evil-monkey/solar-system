package com.solarsystem.wheaterpredictor.core.helpers.basic;

import org.springframework.stereotype.Component;

import com.solarsystem.wheaterpredictor.core.helpers.AngularHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

@Component
public class BasicAngularHelper implements AngularHelper {

	@Override
	public boolean areNotSameOrOppositeAzimuth(PolarCoord position1, PolarCoord position2) {
		return position1.getRadius() == 0 || position2.getRadius() == 0
				|| position1.getRelativeAzimuth() == position2.getRelativeAzimuth()
				|| Math.abs(position2.getRelativeAzimuth() - position1.getRelativeAzimuth()) == 180;
	}
}
