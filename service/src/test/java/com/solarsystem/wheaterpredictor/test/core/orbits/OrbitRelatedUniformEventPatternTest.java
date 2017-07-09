package com.solarsystem.wheaterpredictor.test.core.orbits;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.solarsystem.wheaterpredictor.core.exceptions.InvalidPatternDefinitionException;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;

public class OrbitRelatedUniformEventPatternTest {

	@Test
	public void constructorTest() {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(1, 4, 1);
		assertEquals("Invalid first Occurrence", Integer.valueOf(1), pattern.getFirstOccurrence());
		assertEquals("Invalid period", Integer.valueOf(3), pattern.getPeriod());
		assertEquals("Invalid extension", Integer.valueOf(1), pattern.getExtension());
	}

	@Test
	public void defaultConstructorTest() {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern();
		assertNull("Invalid first Occurrence", pattern.getFirstOccurrence());
		assertNull("Invalid period", pattern.getPeriod());
		assertNull("Invalid extension", pattern.getExtension());
	}

	@Test
	public void isDayInPatternTestWithoutExtension() throws Exception {
		// just for the day
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(2, 5, 0);
		// backwards

		assertFalse("Event shouldn't occur in day 10", pattern.isDayInPattern(10));
		assertFalse("Event shouldn't occur in day 9", pattern.isDayInPattern(9));
		assertTrue("Event should occur in day 8", pattern.isDayInPattern(8));
		assertFalse("Event shouldn't occur in day 7", pattern.isDayInPattern(7));
		assertFalse("Event shouldn't occur in day 6", pattern.isDayInPattern(6));
		assertTrue("Event should occur in day 5", pattern.isDayInPattern(5));
		assertFalse("Event shouldn't occur in day 4", pattern.isDayInPattern(4));
		assertFalse("Event shouldn't occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertFalse("Event shouldn't occur in day 1", pattern.isDayInPattern(1));
		assertFalse("Event shouldn't occur in day 0", pattern.isDayInPattern(0));
		assertTrue("Event should occur in day -1", pattern.isDayInPattern(-1));
		assertFalse("Event shouldn't occur in day -2", pattern.isDayInPattern(-2));
		assertFalse("Event shouldn't occur in day -3", pattern.isDayInPattern(-3));
		assertTrue("Event should occur in day -4", pattern.isDayInPattern(-4));
		assertFalse("Event shouldn't occur in day -5", pattern.isDayInPattern(-5));
		assertFalse("Event shouldn't occur in day -6", pattern.isDayInPattern(-6));
		assertTrue("Event should occur in day -7", pattern.isDayInPattern(-7));
		assertFalse("Event shouldn't occur in day -8", pattern.isDayInPattern(-8));

		pattern = new OrbitRelatedUniformEventPattern(2, 7, 0);
		// backwards
		assertTrue("Event should occur in day 12", pattern.isDayInPattern(12));
		assertFalse("Event shouldn't occur in day 11", pattern.isDayInPattern(11));
		assertFalse("Event shouldn't occur in day 10", pattern.isDayInPattern(10));
		assertFalse("Event shouldn't occur in day 9", pattern.isDayInPattern(9));
		assertFalse("Event shouldn't occur in day 8", pattern.isDayInPattern(8));
		assertTrue("Event should occur in day 7", pattern.isDayInPattern(7));
		assertFalse("Event shouldn't occur in day 6", pattern.isDayInPattern(6));
		assertFalse("Event shouldn't occur in day 5", pattern.isDayInPattern(5));
		assertFalse("Event shouldn't occur in day 4", pattern.isDayInPattern(4));
		assertFalse("Event shouldn't occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertFalse("Event shouldn't occur in day 1", pattern.isDayInPattern(1));
		assertFalse("Event shouldn't occur in day 0", pattern.isDayInPattern(0));
		assertFalse("Event shouldn't occur in day -1", pattern.isDayInPattern(-1));
		assertFalse("Event shouldn't occur in day -2", pattern.isDayInPattern(-2));
		assertTrue("Event should occur in day -3", pattern.isDayInPattern(-3));
		assertFalse("Event shouldn't occur in day -4", pattern.isDayInPattern(-4));
		assertFalse("Event shouldn't occur in day -5", pattern.isDayInPattern(-5));
		assertFalse("Event shouldn't occur in day -6", pattern.isDayInPattern(-6));
		assertFalse("Event shouldn't occur in day -7", pattern.isDayInPattern(-7));
		assertTrue("Event should occur in day -8", pattern.isDayInPattern(-8));
		assertFalse("Event shouldn't occur in day -9", pattern.isDayInPattern(-9));
		assertFalse("Event shouldn't occur in day -10", pattern.isDayInPattern(-10));
		assertFalse("Event shouldn't occur in day -11", pattern.isDayInPattern(-11));
		assertFalse("Event shouldn't occur in day -12", pattern.isDayInPattern(-12));
		assertTrue("Event should occur in day -13", pattern.isDayInPattern(-13));
		assertFalse("Event shouldn't occur in day -14", pattern.isDayInPattern(-14));

		// border always
		pattern = new OrbitRelatedUniformEventPattern(1, 2, 0);
		// backwards

		assertTrue("Event should occur in day 10", pattern.isDayInPattern(10));
		assertTrue("Event should occur in day 9", pattern.isDayInPattern(9));
		assertTrue("Event should occur in day 8", pattern.isDayInPattern(8));
		assertTrue("Event should occur in day 7", pattern.isDayInPattern(7));
		assertTrue("Event should occur in day 6", pattern.isDayInPattern(6));
		assertTrue("Event should occur in day 5", pattern.isDayInPattern(5));
		assertTrue("Event should occur in day 4", pattern.isDayInPattern(4));
		assertTrue("Event should occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertTrue("Event should occur in day 1", pattern.isDayInPattern(1));
		assertTrue("Event should occur in day 0", pattern.isDayInPattern(0));
		assertTrue("Event should occur in day -1", pattern.isDayInPattern(-1));
		assertTrue("Event should occur in day -2", pattern.isDayInPattern(-2));
		assertTrue("Event should occur in day -3", pattern.isDayInPattern(-3));
		assertTrue("Event should occur in day -4", pattern.isDayInPattern(-4));
		assertTrue("Event should occur in day -5", pattern.isDayInPattern(-5));
		assertTrue("Event should occur in day -6", pattern.isDayInPattern(-6));
		assertTrue("Event should occur in day -7", pattern.isDayInPattern(-7));
		assertTrue("Event should occur in day -8", pattern.isDayInPattern(-8));

		// border always
		pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		// backwards

		assertTrue("Event should occur in day 10", pattern.isDayInPattern(10));
		assertTrue("Event should occur in day 9", pattern.isDayInPattern(9));
		assertTrue("Event should occur in day 8", pattern.isDayInPattern(8));
		assertTrue("Event should occur in day 7", pattern.isDayInPattern(7));
		assertTrue("Event should occur in day 6", pattern.isDayInPattern(6));
		assertTrue("Event should occur in day 5", pattern.isDayInPattern(5));
		assertTrue("Event should occur in day 4", pattern.isDayInPattern(4));
		assertTrue("Event should occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertTrue("Event should occur in day 1", pattern.isDayInPattern(1));
		assertTrue("Event should occur in day 0", pattern.isDayInPattern(0));
		assertTrue("Event should occur in day -1", pattern.isDayInPattern(-1));
		assertTrue("Event should occur in day -2", pattern.isDayInPattern(-2));
		assertTrue("Event should occur in day -3", pattern.isDayInPattern(-3));
		assertTrue("Event should occur in day -4", pattern.isDayInPattern(-4));
		assertTrue("Event should occur in day -5", pattern.isDayInPattern(-5));
		assertTrue("Event should occur in day -6", pattern.isDayInPattern(-6));
		assertTrue("Event should occur in day -7", pattern.isDayInPattern(-7));
		assertTrue("Event should occur in day -8", pattern.isDayInPattern(-8));

	}

	@Test
	public void isDayInPatternTestWithtExtension() throws Exception {
		// just for the day
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(2, 5, 1);
		// backwards

		assertFalse("Event shouldn't occur in day 10", pattern.isDayInPattern(10));
		assertTrue("Event should occur in day 9", pattern.isDayInPattern(9));
		assertTrue("Event should occur in day 8", pattern.isDayInPattern(8));
		assertFalse("Event shouldn't occur in day 7", pattern.isDayInPattern(7));
		assertTrue("Event should occur in day 6", pattern.isDayInPattern(6));
		assertTrue("Event should occur in day 5", pattern.isDayInPattern(5));
		assertFalse("Event shouldn't occur in day 4", pattern.isDayInPattern(4));
		assertTrue("Event should occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertFalse("Event shouldn't occur in day 1", pattern.isDayInPattern(1));
		assertTrue("Event should occur in day 0", pattern.isDayInPattern(0));
		assertTrue("Event should occur in day -1", pattern.isDayInPattern(-1));
		assertFalse("Event shouldn't occur in day -2", pattern.isDayInPattern(-2));
		assertTrue("Event should occur in day -3", pattern.isDayInPattern(-3));
		assertTrue("Event should occur in day -4", pattern.isDayInPattern(-4));
		assertFalse("Event shouldn't occur in day -5", pattern.isDayInPattern(-5));
		assertTrue("Event should occur in day -6", pattern.isDayInPattern(-6));
		assertTrue("Event should occur in day -7", pattern.isDayInPattern(-7));
		assertFalse("Event shouldn't occur in day -8", pattern.isDayInPattern(-8));

		pattern = new OrbitRelatedUniformEventPattern(2, 7, 2);
		// backwards
		assertTrue("Event should occur in day 14", pattern.isDayInPattern(14));
		assertTrue("Event should occur in day 13", pattern.isDayInPattern(13));
		assertTrue("Event should occur in day 12", pattern.isDayInPattern(12));
		assertFalse("Event shouldn't occur in day 11", pattern.isDayInPattern(11));
		assertFalse("Event shouldn't occur in day 10", pattern.isDayInPattern(10));
		assertTrue("Event should occur in day 9", pattern.isDayInPattern(9));
		assertTrue("Event should occur in day 8", pattern.isDayInPattern(8));
		assertTrue("Event should occur in day 7", pattern.isDayInPattern(7));
		assertFalse("Event shouldn't occur in day 6", pattern.isDayInPattern(6));
		assertFalse("Event shouldn't occur in day 5", pattern.isDayInPattern(5));
		assertTrue("Event should occur in day 4", pattern.isDayInPattern(4));
		assertTrue("Event should occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertFalse("Event shouldn't occur in day 1", pattern.isDayInPattern(1));
		assertFalse("Event shouldn't occur in day 0", pattern.isDayInPattern(0));
		assertTrue("Event should occur in day -1", pattern.isDayInPattern(-1));
		assertTrue("Event should occur in day -2", pattern.isDayInPattern(-2));
		assertTrue("Event should occur in day -3", pattern.isDayInPattern(-3));
		assertFalse("Event shouldn't occur in day -4", pattern.isDayInPattern(-4));
		assertFalse("Event shouldn't occur in day -5", pattern.isDayInPattern(-5));
		assertTrue("Event should occur in day -6", pattern.isDayInPattern(-6));
		assertTrue("Event should occur in day -7", pattern.isDayInPattern(-7));
		assertTrue("Event should occur in day -8", pattern.isDayInPattern(-8));
		assertFalse("Event shouldn't occur in day -9", pattern.isDayInPattern(-9));
		assertFalse("Event shouldn't occur in day -10", pattern.isDayInPattern(-10));
		assertTrue("Event should occur in day -11", pattern.isDayInPattern(-11));
		assertTrue("Event should occur in day -12", pattern.isDayInPattern(-12));
		assertTrue("Event should occur in day -13", pattern.isDayInPattern(-13));
		assertFalse("Event shouldn't occur in day -14", pattern.isDayInPattern(-14));

		// border always
		pattern = new OrbitRelatedUniformEventPattern(2, 5, 2);
		// backwards

		assertTrue("Event should occur in day 10", pattern.isDayInPattern(10));
		assertTrue("Event should occur in day 9", pattern.isDayInPattern(9));
		assertTrue("Event should occur in day 8", pattern.isDayInPattern(8));
		assertTrue("Event should occur in day 7", pattern.isDayInPattern(7));
		assertTrue("Event should occur in day 6", pattern.isDayInPattern(6));
		assertTrue("Event should occur in day 5", pattern.isDayInPattern(5));
		assertTrue("Event should occur in day 4", pattern.isDayInPattern(4));
		assertTrue("Event should occur in day 3", pattern.isDayInPattern(3));
		assertTrue("Event should occur in day 2", pattern.isDayInPattern(2));
		assertTrue("Event should occur in day 1", pattern.isDayInPattern(1));
		assertTrue("Event should occur in day 0", pattern.isDayInPattern(0));
		assertTrue("Event should occur in day -1", pattern.isDayInPattern(-1));
		assertTrue("Event should occur in day -2", pattern.isDayInPattern(-2));
		assertTrue("Event should occur in day -3", pattern.isDayInPattern(-3));
		assertTrue("Event should occur in day -4", pattern.isDayInPattern(-4));
		assertTrue("Event should occur in day -5", pattern.isDayInPattern(-5));
		assertTrue("Event should occur in day -6", pattern.isDayInPattern(-6));
		assertTrue("Event should occur in day -7", pattern.isDayInPattern(-7));
		assertTrue("Event should occur in day -8", pattern.isDayInPattern(-8));
	}

	@Test
	public void validateRelationsFromSetPeriodLessThan1Test() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		try {
			pattern.setPeriod(0);
			fail("There should be unreacheble code!");
		} catch (InvalidPatternDefinitionException e) {

		}
	}

	@Test
	public void validateRelationsFromSetPeriodNullTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		try {
			pattern.setPeriod(null);
			fail("There should be unreacheble code!");
		} catch (InvalidPatternDefinitionException e) {

		}
	}

	@Test
	public void validateRelationsFromSetPeriodTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		try {
			pattern.setPeriod(2);

		} catch (InvalidPatternDefinitionException e) {
			fail("There should be unreacheble code!");
		}
	}

	@Test
	public void validateRelationsFromSetExtensionEqualsOrGraterThanPeriodTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		try {
			pattern.setExtension(1);
			fail("There should be unreacheble code!");
		} catch (InvalidPatternDefinitionException e) {

		}
	}

	@Test
	public void validateRelationsFromSetExtensionLessThan0Test() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		try {
			pattern.setExtension(-1);
			fail("There should be unreacheble code!");
		} catch (InvalidPatternDefinitionException e) {

		}
	}

	@Test
	public void validateRelationsFromSetExtensionNullTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 1, 0);
		try {
			pattern.setExtension(null);
			fail("There should be unreacheble code!");
		} catch (InvalidPatternDefinitionException e) {

		}
	}

	@Test
	public void validateRelationsFromSetExtensionTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern(0, 2, 0);
		try {
			pattern.setExtension(1);
		} catch (InvalidPatternDefinitionException e) {
			fail("There should be unreacheble code!");
		}
	}

	@Test(expected = InvalidPatternDefinitionException.class)
	public void setFirstOccurrenceNullValidationTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern();
		pattern.setFirstOccurrence(null);
	}

	@Test(expected = InvalidPatternDefinitionException.class)
	public void setFirstOccurrenceNegativeValidationTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern();
		pattern.setFirstOccurrence(-3);
	}

	@Test
	public void setFirstOccurrenceValidationTest() throws Exception {
		OrbitRelatedUniformEventPattern pattern = new OrbitRelatedUniformEventPattern();
		try {
			pattern.setFirstOccurrence(3);
		} catch (InvalidPatternDefinitionException e) {
			fail("There should be unreacheble code!");
		}
	}
}
