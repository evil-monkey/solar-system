package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.PolygonHelper;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public final class RainestDayWheater extends RelatedWheaterEventType {

	@Inject
	private PolygonHelper polygonHelper;

	private SortedMap<Integer, Collection<RectangularCoord>> patternCalculatorBuffer;

	private OrbitRelatedUniformEventPattern progressivePattern;

	public RainestDayWheater() {
		patternCalculatorBuffer = new TreeMap<>();
		progressivePattern = new OrbitRelatedUniformEventPattern();
	}

	@Override
	public String getName() {
		return "Día más lluvioso de la temporada";
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {
		return progressivePattern;
	}

	@Override
	public void addPositionsData(Collection<RectangularCoord> positions, Integer day) throws PatternCalculationError {
		patternCalculatorBuffer.put(day, positions);
	}

	@Override
	public void calculatePatternFromPositions(Integer period) {
		if (patternCalculatorBuffer.size() < 1) {
			throw new PatternCalculationError("No position to calculate a pattern");
		}
		if (period < 1) {
			throw new PatternCalculationError("Invalid period to calculate a pattern");
		}

		SortedMap<Double, Integer> perimetersByDay = new TreeMap<>();

		patternCalculatorBuffer.keySet().forEach(
				day -> perimetersByDay.put(polygonHelper.getPerimeter(patternCalculatorBuffer.get(day)), day));
		Integer firstOccurrence = perimetersByDay.get(perimetersByDay.lastKey());
		progressivePattern.setFirstOccurrence(firstOccurrence);
		progressivePattern.setExtension(0);
		progressivePattern.setPeriod(period);

	}

}
