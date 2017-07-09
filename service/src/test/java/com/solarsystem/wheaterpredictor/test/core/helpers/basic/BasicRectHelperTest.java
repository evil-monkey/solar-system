package com.solarsystem.wheaterpredictor.test.core.helpers.basic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.basic.BasicRectHelper;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class BasicRectHelperTest {

	private BasicRectHelper helper;
	private Double tolerance;

	public BasicRectHelperTest() {
		helper = new BasicRectHelper();
		tolerance = 1.0d;
	}

	@Test(expected = PatternCalculationError.class)
	public void insufficientDataTest() throws Exception {
		helper.allAreAlignedExceptByTheSun(new LinkedList<>(), new RectangularCoord(), tolerance);
	}

	@Test
	public void sunIsAlignedTest() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 30));
		polarCoords.add(new PolarCoord(1500, 30));
		polarCoords.add(new PolarCoord(2000, 210));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertFalse("Sun is aligned, it mus be false.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(0.0d, 0.0d), tolerance));
	}
	
	@Test
	public void sunIsNotAlignedBasicTest() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 30));
		polarCoords.add(new PolarCoord(1500, 30));
		polarCoords.add(new PolarCoord(2000, 30));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}

	@Test
	public void testHorizontalAlignment() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 0));
		polarCoords.add(new PolarCoord(1500, 0));
		polarCoords.add(new PolarCoord(2000, 0));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void testVerticalAlignment() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 90));
		polarCoords.add(new PolarCoord(1500, 90));
		polarCoords.add(new PolarCoord(2000, 90));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void sunIsNotAlignedBasicTestWithOpossites() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 30));
		polarCoords.add(new PolarCoord(1500, 210));
		polarCoords.add(new PolarCoord(2000, 30));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}

	@Test
	public void testHorizontalAlignmentWithOpossites() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 0));
		polarCoords.add(new PolarCoord(1500, 180));
		polarCoords.add(new PolarCoord(2000, 0));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void testVerticalAlignmentWithOpossites() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 90));
		polarCoords.add(new PolarCoord(1500, 270));
		polarCoords.add(new PolarCoord(2000, 90));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void testDemiVericalAlignmentWithOpossites0() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 87));
		polarCoords.add(new PolarCoord(1500, 267));
		polarCoords.add(new PolarCoord(2000, 87));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void testDemiVericalAlignmentWithOpossites1() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 88));
		polarCoords.add(new PolarCoord(1500, 268));
		polarCoords.add(new PolarCoord(2000, 88));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void testDemiVericalAlignmentWithOpossites2() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 89));
		polarCoords.add(new PolarCoord(1500, 269));
		polarCoords.add(new PolarCoord(2000, 89));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertTrue("Sun not is aligned, it must be true.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(20.0d, 20.0d), tolerance));
	}
	
	@Test
	public void sunIsVerticallyAlignedTest() throws Exception {
		List<PolarCoord> polarCoords = new LinkedList<>();
		polarCoords.add(new PolarCoord(1000, 90));
		polarCoords.add(new PolarCoord(1500, 270));
		polarCoords.add(new PolarCoord(2000, 90));
		List<RectangularCoord> positions = polarCoords.stream().map(pcoord -> pcoord.getRectangularCoord())
				.collect(Collectors.toList());

		assertFalse("Sun is aligned, it mus be false.",
				helper.allAreAlignedExceptByTheSun(positions, new RectangularCoord(0.0d, 0.0d), tolerance));
	}

}
