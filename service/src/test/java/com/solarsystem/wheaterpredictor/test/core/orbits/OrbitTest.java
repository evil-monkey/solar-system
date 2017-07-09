package com.solarsystem.wheaterpredictor.test.core.orbits;

import static org.junit.Assert.*;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.orbits.Orbit;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

public class OrbitTest {

	@Test
	public void testName() throws Exception {
		Orbit orbit = new Orbit() {

			@Override
			public PolarCoord calculatePosition(int day) {
				return null;
			}
		};

		String name = "Name";
		orbit.setName(name);
		assertEquals("Invalid orbit name", name, orbit.getName());

	}

}
