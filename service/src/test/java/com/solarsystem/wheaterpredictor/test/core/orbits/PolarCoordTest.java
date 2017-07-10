package com.solarsystem.wheaterpredictor.test.core.orbits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class PolarCoordTest {

	@Test
	public void defaultConstructorTest() {
		PolarCoord coord = new PolarCoord();
		assertEquals("Wrong default radius", Integer.valueOf(0), coord.getRadius());
		assertEquals("Wrong default azimuth", Integer.valueOf(0), coord.getAzimuth());
	}

	@Test
	public void equalsTest() throws Exception {

		PolarCoord coord0 = new PolarCoord(1, 1);

		assertFalse("Coord can't be equals to null", coord0.equals(null));
		assertFalse("Coord can't be equals to other type of object", coord0.equals(Integer.valueOf(1)));

		PolarCoord coord1 = new PolarCoord(0, 0);
		assertFalse("Coord aren't equals", coord0.equals(coord1));
		coord1.setAzimuth(1);
		assertFalse("Coord aren't equals by radius", coord0.equals(coord1));
		coord1.setRadius(1);
		assertTrue("Coord are equals", coord0.equals(coord1));
		coord1.setAzimuth(0);
		assertFalse("Coord aren't equals by azimuth", coord0.equals(coord1));

		coord1.setAzimuth(721);
		assertTrue("Coord are equals", coord0.equals(coord1));

		coord1.setRadius(-1);
		coord1.setAzimuth(181);
		assertTrue("Coord are equals", coord0.equals(coord1));

		coord1.setRadius(1);
		assertFalse("Coord aren't equals", coord0.equals(coord1));

		coord1.setAzimuth(-359);
		assertTrue("Coord are equals", coord0.equals(coord1));

		coord1.setAzimuth(-359 - 720);
		assertTrue("Coord are equals", coord0.equals(coord1));
	}

	@Test
	public void getRelativeAzimuthTest() throws Exception {

		PolarCoord coord = new PolarCoord();

		assertEquals("Invalid result for 0", 0, coord.getRelativeAzimuth());

		coord.setAzimuth(30);
		assertEquals("Invalid result for positive less than 180", 30, coord.getRelativeAzimuth());

		coord.setAzimuth(270);
		assertEquals("Invalid result for positive between 180 and 360", 270, coord.getRelativeAzimuth());

		coord.setAzimuth(360);
		assertEquals("Invalid result for positive 360", 0, coord.getRelativeAzimuth());

		coord.setAzimuth(750);
		assertEquals("Invalid result for positive more than one gyro", 30, coord.getRelativeAzimuth());

		coord = new PolarCoord(-1, 0);

		assertEquals("Invalid result for negative radius and 0", 180, coord.getRelativeAzimuth());

		coord.setAzimuth(30);
		assertEquals("Invalid result for negative radius and positive less than 180", 210, coord.getRelativeAzimuth());

		coord.setAzimuth(270);
		assertEquals("Invalid result for negative radius and positive between 180 and 360", 90,
				coord.getRelativeAzimuth());

		coord.setAzimuth(360);
		assertEquals("Invalid result for negative radius and positive 360", 180, coord.getRelativeAzimuth());

		coord.setAzimuth(750);
		assertEquals("Invalid result for negative radius and positive more than one gyro", 210,
				coord.getRelativeAzimuth());

		coord = new PolarCoord(1, -30);

		assertEquals("Invalid result for negative less than one gyro", 330, coord.getRelativeAzimuth());

		coord = new PolarCoord(1, -390);

		assertEquals("Invalid result for negative more than one gyro", 330, coord.getRelativeAzimuth());

		coord = new PolarCoord(-1, -30);

		assertEquals("Invalid result for negative radius and negative less than one gyro", 150,
				coord.getRelativeAzimuth());

		coord = new PolarCoord(-1, -390);

		assertEquals("Invalid result for negative radius and negative more than one gyro", 150,
				coord.getRelativeAzimuth());

	}

	@Test
	public void testHashcode() throws Exception {

		PolarCoord coord0 = new PolarCoord(1, 30);
		PolarCoord coord1 = new PolarCoord(1, 30);
		PolarCoord coord2 = new PolarCoord(1, 390);
		PolarCoord coord3 = new PolarCoord(-1, 210);

		assertEquals("Coords should be equals...", coord0, coord1);
		assertEquals("Coords hashcode should be equals...", coord0.hashCode(), coord1.hashCode());
		assertEquals("Coords should be equals...", coord0, coord2);
		assertEquals("Coords hashcode should be equals...", coord0.hashCode(), coord1.hashCode());
		assertEquals("Coords should be equals...", coord0, coord2);
		assertEquals("Coords hashcode should be equals...", coord0.hashCode(), coord2.hashCode());
		assertEquals("Coords should be equals...", coord0, coord3);
		assertEquals("Coords hashcode should be equals...", coord0.hashCode(), coord3.hashCode());
	}

	@Test
	public void getRectangularCoordTest() throws Exception {
		PolarCoord polarCoord = new PolarCoord(1, 60);
		RectangularCoord rectangularCoord = polarCoord.getRectangularCoord();

		assertEquals("Wrong rectangular coord X", new BigDecimal(0.5d, new MathContext(1)),
				new BigDecimal(rectangularCoord.getX(), new MathContext(1)));
		assertEquals("Wrong rectangular coord Y", new BigDecimal(0.86602540378d, new MathContext(3)),
				new BigDecimal(rectangularCoord.getY(), new MathContext(3)));

	}

	@Test
	public void testToString() throws Exception {
		PolarCoord pc = new PolarCoord(10, 30);
		assertEquals("Wrong representation.", "p(10, 30)", pc.toString());

	}

	@Test
	public void testRectangularToString() throws Exception {
		RectangularCoord rc = new RectangularCoord(10.0d, 30.0d);
		assertEquals("Wrong representation.", "r(10.0, 30.0)", rc.toString());

	}

	@Test
	public void rectangularCoordEqualsTest() throws Exception {

		RectangularCoord coord0 = new RectangularCoord(1.0d, 1.0d);

		assertFalse("Coord can't be equals to null", coord0.equals(null));
		assertFalse("Coord can't be equals to other type of object", coord0.equals(Integer.valueOf(1)));

		RectangularCoord coord1 = new RectangularCoord(1.0d, 0.0d);
		assertFalse("Coord aren't equals", coord0.equals(coord1));
		
		coord1 = new RectangularCoord(null, 1.0d);
		assertFalse("Coord aren't equals", coord1.equals(coord0));
		
		coord1 = new RectangularCoord(1.0d, null);
		assertFalse("Coord aren't equals", coord1.equals(coord0));
		
		coord1 = new RectangularCoord(null, null);
		assertFalse("Coord aren't equals", coord1.equals(coord0));
		
		coord1 = new RectangularCoord(0.0d, 1.0d);
		assertFalse("Coord aren't equals", coord0.equals(coord1));
		
	}

}
