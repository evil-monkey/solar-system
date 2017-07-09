package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.Collection;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public abstract class RelatedWheaterEventType extends WheaterEventType {

	/**
	 * Intended for progressive related event pattern calculator
	 * 
	 * @param positions
	 * @param day
	 * @return
	 * @throws PatternCalculationError
	 * @throws EventPredictionException 
	 */
	public abstract void addPositionsData(Collection<RectangularCoord> positions, Integer day);

	public abstract void calculatePatternFromPositions(Integer period) throws PatternCalculationError;
}
