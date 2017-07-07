package com.solarsystem.wheaterpredictor.core.helpers.basic;

import org.springframework.stereotype.Component;

import com.solarsystem.wheaterpredictor.core.PolarCoord;
import com.solarsystem.wheaterpredictor.core.helpers.AngularHelper;

@Component
public class BasicAngularHelper implements AngularHelper {

	@Override
	public boolean areNotSameOrOppositeAzimuth(PolarCoord position1, PolarCoord position2) {
		return position1.getAzimuth().equals(position2.getAzimuth())
				|| Math.abs(position2.getAzimuth() - position1.getAzimuth()) == 180;
	}
}
