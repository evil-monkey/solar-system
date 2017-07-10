package com.solarsystem.wheaterpredictor.test.core.helpers.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.basic.TriangleBasicHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class BasicTriangleHelperTest {

	private TriangleBasicHelper helper;

	public BasicTriangleHelperTest() {
		helper = new TriangleBasicHelper();
	}

	@Test
	public void sunIsBetweenPlanetsTest0() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(400.0d, 10.0d));
		coords.add(new RectangularCoord(-200.0d, 10.0d));
		coords.add(new RectangularCoord(0.0d, -1500.0d));

		assertTrue("It must be true.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test
	public void sunIsBetweenPlanetsTest1() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(0.0d, 1500d));
		coords.add(new RectangularCoord(200.0d, -10.0d));
		coords.add(new RectangularCoord(-400.0d, -15.0d));

		assertTrue("It must be true.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test
	public void sunIsNotBetweenPlanetsTest0() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(-400.0d, 10.0d));
		coords.add(new RectangularCoord(-200.0d, 10.0d));
		coords.add(new RectangularCoord(10.0d, -1500.0d));

		assertFalse("It must be false.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test
	public void sunIsNotBetweenPlanetsTest1() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(10.0d, 10.0d));
		coords.add(new RectangularCoord(20.0d, 10.0d));
		coords.add(new RectangularCoord(10.0d, 30.0d));
		assertFalse("It must be false.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test
	public void sunIsNotBetweenPlanetsTest2() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(-10.0d, 10.0d));
		coords.add(new RectangularCoord(-20.0d, 10.0d));
		coords.add(new RectangularCoord(-10.0d, 30.0d));
		assertFalse("It must be false.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test
	public void sunIsNotBetweenPlanetsTest3() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(-10.0d, -10.0d));
		coords.add(new RectangularCoord(-20.0d, -10.0d));
		coords.add(new RectangularCoord(-10.0d, -30.0d));
		assertFalse("It must be false.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test
	public void sunIsNotBetweenPlanetsTest4() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(10.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		coords.add(new RectangularCoord(10.0d, -30.0d));
		assertFalse("It must be false.", helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d)));

	}

	@Test(expected = PatternCalculationError.class)
	public void insufficientDataTest() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(10.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d));
	}

	@Test(expected = PatternCalculationError.class)
	public void insufficientDataTest2() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(10.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		helper.isPointBetweenVertices(coords, new RectangularCoord(0.0d, 0.0d));
	}

	@Test(expected = PatternCalculationError.class)
	public void insufficientDataTest3() throws Exception {
		helper.isPointBetweenVertices(null, new RectangularCoord(0.0d, 0.0d));
	}

	@Test(expected = PatternCalculationError.class)
	public void perimeterInsufficientDataTest() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(10.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		helper.getPerimeter(coords);
	}

	@Test(expected = PatternCalculationError.class)
	public void perimeterInsufficientDataTest2() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(10.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		coords.add(new RectangularCoord(20.0d, -10.0d));
		helper.getPerimeter(coords);
	}

	@Test(expected = PatternCalculationError.class)
	public void perimeterInsufficientDataTest3() throws Exception {
		helper.getPerimeter(null);
	}

	@Test
	public void getPerimeter() throws Exception {
		List<RectangularCoord> coords = new LinkedList<>();
		coords.add(new RectangularCoord(0.0d, 1.0d));
		coords.add(new RectangularCoord(1.0d, 0.0d));
		coords.add(new RectangularCoord(0.0d, 0.0d));

		Double perimeter = helper.getPerimeter(coords);
		assertNotNull("Invalid calculated perimeter", perimeter);

		BigDecimal expectedPerimeter = new BigDecimal(1 + 1 + Math.sqrt(2), new MathContext(2));
		assertEquals("Wrong calculated perimeter", expectedPerimeter, new BigDecimal(perimeter, new MathContext(2)));

	}

}
