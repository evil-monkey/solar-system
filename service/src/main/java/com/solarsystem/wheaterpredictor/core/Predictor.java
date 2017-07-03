package com.solarsystem.wheaterpredictor.core;

import java.util.Collection;

import com.solarsystem.wheaterpredictor.core.events.EventType;
import com.solarsystem.wheaterpredictor.core.exceptions.PredictorException;

public interface Predictor {
	
	/**
	 * Predicts events for a specific day
	 * @param day
	 * @return
	 * @throws PredictorException 
	 */
	Collection<EventType> predict(Integer day);
	
}
