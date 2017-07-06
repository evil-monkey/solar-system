package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.Collection;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;

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
	protected abstract void addPositionsData(Collection<RectangularCoord> positions, Integer day);

	protected abstract void calculatePatternFromPositions(Integer period) throws PatternCalculationError;
}
