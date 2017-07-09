package com.solarsystem.wheaterpredictor.test.core.events.wheater;

import java.util.Collection;
import java.util.LinkedList;

import com.solarsystem.wheaterpredictor.core.orbits.CircularOrbit;
import com.solarsystem.wheaterpredictor.core.orbits.Orbit;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;

public abstract class WheaterEventTypeBaseTest {

	protected Collection<Orbit> orbits;

	protected void initOrbits() {
		this.orbits = new LinkedList<>();

		CircularOrbit ferengi = new CircularOrbit();
		ferengi.setAngularSpeed(-1);
		ferengi.setInitialPosition(new PolarCoord(500, 0));

		CircularOrbit betasoide = new CircularOrbit();
		betasoide.setAngularSpeed(-3);
		betasoide.setInitialPosition(new PolarCoord(2000, 0));

		CircularOrbit vulcano = new CircularOrbit();
		vulcano.setAngularSpeed(5);
		vulcano.setInitialPosition(new PolarCoord(1000, 0));

		orbits.add(ferengi);
		orbits.add(betasoide);
		orbits.add(vulcano);

	}

}
