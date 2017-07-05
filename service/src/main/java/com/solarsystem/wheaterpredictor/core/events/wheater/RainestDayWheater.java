package com.solarsystem.wheaterpredictor.core.events.wheater;

import com.solarsystem.wheaterpredictor.core.events.CyclicEventPattern;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;

public class RainestDayWheater extends WheaterEventType {
	
	private RainyWheater mainWheaterEvent;

	public RainestDayWheater(RainyWheater rainyWheater) {
		this.mainWheaterEvent = rainyWheater;
	}

	@Override
	public String getName() {
		return "Día más lluvioso de la temporada";
	}

	@Override
	protected CyclicEventPattern obtainPattern() throws PatternCalculationError {
		// TODO Auto-generated method stub
		return null;
	}

}
