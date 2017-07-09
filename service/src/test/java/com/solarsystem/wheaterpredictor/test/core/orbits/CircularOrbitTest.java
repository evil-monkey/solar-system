package com.solarsystem.wheaterpredictor.test.core.orbits;

import static org.junit.Assert.*;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.orbits.CircularOrbit;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

public class CircularOrbitTest {

	@Test
	public void calculatePositionTest() {
		CircularOrbit orbit = new CircularOrbit();
		orbit.setAngularSpeed(3);
		Integer radius = 1000;
		Integer azimuth = 0;
		orbit.setInitialPosition(new PolarCoord(radius, azimuth));

		assertEquals("Invalid calculated position for day 1", new PolarCoord(radius, 3), orbit.calculatePosition(1));
		assertEquals("Invalid calculated position for day 5", new PolarCoord(radius, 15), orbit.calculatePosition(5));
		assertEquals("Invalid calculated position for day -1", new PolarCoord(radius, -3), orbit.calculatePosition(-1));
		assertEquals("Invalid calculated position for day -5", new PolarCoord(radius, -15),
				orbit.calculatePosition(-5));
	}

	@Test
	public void radiusComparatorTest() throws Exception {

		// null vs null
		assertEquals("Invalid orbit radius comparator result", -1, CircularOrbit.radiusComparator.compare(null, null));

		// null vs not null
		assertEquals("Invalid orbit radius comparator result", -1,
				CircularOrbit.radiusComparator.compare(null, new CircularOrbit(1, new PolarCoord(1000, 0))));

		// not null vs null
		assertEquals("Invalid orbit radius comparator result", 1,
				CircularOrbit.radiusComparator.compare(new CircularOrbit(1, new PolarCoord(1000, 0)), null));

		// same radius
		assertEquals("Invalid orbit radius comparator result", 0, CircularOrbit.radiusComparator
				.compare(new CircularOrbit(1, new PolarCoord(1000, 0)), new CircularOrbit(1, new PolarCoord(1000, 0))));

		// smallest vs largest
		assertEquals("Invalid orbit radius comparator result", -1, CircularOrbit.radiusComparator
				.compare(new CircularOrbit(1, new PolarCoord(500, 0)), new CircularOrbit(1, new PolarCoord(1000, 0))));

		// largest vs smallest
		assertEquals("Invalid orbit radius comparator result", 1, CircularOrbit.radiusComparator
				.compare(new CircularOrbit(1, new PolarCoord(1000, 0)), new CircularOrbit(1, new PolarCoord(500, 0))));

	}

}
