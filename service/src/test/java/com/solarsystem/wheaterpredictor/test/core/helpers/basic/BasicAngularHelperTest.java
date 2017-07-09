package com.solarsystem.wheaterpredictor.test.core.helpers.basic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.helpers.basic.BasicAngularHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

public class BasicAngularHelperTest {

	private BasicAngularHelper helper;

	public BasicAngularHelperTest() {
		helper = new BasicAngularHelper();
	}

	@Test
	public void testCenterPosition() {
		assertTrue("Positions are the same!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(0, 0), new PolarCoord(0, 0)));
	}

	@Test
	public void testCenterVsOtherPosition() {
		assertTrue("If any position has no radius, 0, it must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(0, 0), new PolarCoord(4, 30)));
	}

	@Test
	public void testOtherVsCenterPosition() {
		assertTrue("If any position has no radius, 0, it must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(4, 30), new PolarCoord(0, 0)));
	}

	@Test
	public void testSameAzimuth() {
		assertTrue("It must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(4, 30), new PolarCoord(10, 30)));
	}

	@Test
	public void testOppositeAzimuth() {
		assertTrue("It must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(4, 210), new PolarCoord(10, 30)));
	}

	@Test
	public void testSameRelativeAzimuth() {
		assertTrue("It must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(4, -90), new PolarCoord(10, 270)));
	}

	@Test
	public void testOppositeRelativeAzimuth() {
		assertTrue("It must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(4, 570), new PolarCoord(10, 30)));
	}

	@Test
	public void testNegative() {
		assertFalse("It must be true!",
				helper.areNotSameOrOppositeAzimuth(new PolarCoord(4, -95), new PolarCoord(10, 270)));
	}

}
