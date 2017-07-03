package com.solarsystem.wheaterpredictor.core.events;

public class CyclicEventPattern {

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

	public CyclicEventPattern(Integer firstOccurrence,
			Integer secondOccurrence, Integer extension) {
		this.firstOccurrence = firstOccurrence;
		this.period = secondOccurrence - firstOccurrence;
		this.extension = extension;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getFirstOccurrence() {
		return firstOccurrence;
	}

	public void setFirstOccurrence(Integer firstOccurrence) {
		this.firstOccurrence = firstOccurrence;
	}

	public Integer getExtension() {
		return extension;
	}

	public void setExtension(Integer extension) {
		this.extension = extension;
	}

	public Boolean isDayInPattern(Integer day) {
		Integer rem = (day % period) - firstOccurrence;

		// check if day is a event starting date or it's in the event extension
		// range
		return rem == 0 || rem <= extension;
	}
}
