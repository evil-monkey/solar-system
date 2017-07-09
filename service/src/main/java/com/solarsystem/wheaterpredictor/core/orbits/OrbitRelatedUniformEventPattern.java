package com.solarsystem.wheaterpredictor.core.orbits;

import com.solarsystem.wheaterpredictor.core.events.EventPattern;
import com.solarsystem.wheaterpredictor.core.exceptions.InvalidPatternDefinitionException;

public class OrbitRelatedUniformEventPattern implements EventPattern {

	/**
	 * how many days are between two occurrences
	 */
	private Integer period;

	/**
	 * when is the first occurrence
	 */
	private Integer firstOccurrence;

	/**
	 * extension of event beyond event first day
	 */
	private Integer extension;

	public OrbitRelatedUniformEventPattern() {
		super();
	}

	/**
	 * 
	 * @param firstOccurrence
	 * @param secondOccurrence
	 * @param extension
	 */

	public OrbitRelatedUniformEventPattern(int firstOccurrence, int secondOccurrence, int extension) {

		this.firstOccurrence = firstOccurrence;
		this.period = secondOccurrence - firstOccurrence;
		this.extension = extension;

		validateRelations();
	}

	private void validateRelations() {
		if (this.period == null || this.period <= 0) {
			throw new InvalidPatternDefinitionException("Period must be greater than 0.");
		}

		if (this.extension == null || this.extension < 0 || this.extension >= this.period) {
			throw new InvalidPatternDefinitionException(
					"Extension must be greater or equals than 0 and smaller than period.");
		}
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
		validateRelations();
	}

	public Integer getFirstOccurrence() {
		return firstOccurrence;
	}

	public void setFirstOccurrence(Integer firstOccurrence) {
		this.firstOccurrence = firstOccurrence;
		if (this.firstOccurrence == null || this.firstOccurrence < 0) {
			throw new InvalidPatternDefinitionException("FirtsOccurrence must be greater or equals than 0.");
		}
	}

	public Integer getExtension() {
		return extension;
	}

	public void setExtension(Integer extension) {
		this.extension = extension;
		validateRelations();
	}

	@Override
	public Boolean isDayInPattern(int day) {

		Integer rem;
		if (day >= 0) {
			rem = (day - firstOccurrence) % period;
		} else {
			rem = (day - firstOccurrence + period) % period;
		}

		// check if day is a event starting date or it's in the event extension
		// range
		boolean result = false;
		if (rem == 0) {
			result = true;
		} else if (rem > 0) {
			if (day > firstOccurrence) {
				result = rem <= extension;
			} else {
				result = firstOccurrence - period + extension >= day;
			}
		} else {
			// rem < 0
			result = period + rem <= extension;
		}

		return result;
	}
}
