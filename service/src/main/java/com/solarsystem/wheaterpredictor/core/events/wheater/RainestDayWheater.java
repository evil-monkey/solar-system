package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;

public class RainestDayWheater extends RelatedWheaterEventType {

	private SortedMap<Integer, Collection<RectangularCoord>> patternCalculatorBuffer;

	public RainestDayWheater(RainyWheater rainyWheater) {
		patternCalculatorBuffer = new TreeMap<>();
	}

	private OrbitRelatedUniformEventPattern progressivePattern;

	public RainestDayWheater() {
		progressivePattern = null;
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
	protected void addPositionsData(Collection<RectangularCoord> positions, Integer day)
			throws PatternCalculationError {
		patternCalculatorBuffer.put(day, positions);
	}

	@Override
	protected void calculatePatternFromPositions(Integer period) {
		if (patternCalculatorBuffer.size() < 1) {
			throw new PatternCalculationError("No position to calculate a pattern");
		}
		if (period < 1) {
			throw new PatternCalculationError("Invalid period to calculate a pattern");
		}

		SortedMap<Double, Integer> perimetersByDay = new TreeMap<>();

		patternCalculatorBuffer.keySet()
				.forEach(day -> perimetersByDay.put(getPerimeter(patternCalculatorBuffer.get(day)), day));
		Integer firstOccurrence = perimetersByDay.get(perimetersByDay.lastKey());
		progressivePattern.setFirstOccurrence(firstOccurrence);
		progressivePattern.setPeriod(period);
		progressivePattern.setExtension(0);

	}

	private Double getPerimeter(Collection<RectangularCoord> vertices) {

		List<RectangularCoord> v = new ArrayList<>(vertices);
		return Math
				.sqrt(Math.pow((v.get(1).getX() - v.get(0).getX()), 2)
						* Math.pow((v.get(1).getY() - v.get(0).getY()), 2))
				+ Math.sqrt(Math.pow((v.get(2).getX() - v.get(1).getX()), 2)
						* Math.pow((v.get(2).getY() - v.get(1).getY()), 2))
				+ Math.sqrt(Math.pow((v.get(2).getX() - v.get(0).getX()), 2)
						* Math.pow((v.get(2).getY() - v.get(0).getY()), 2));
	}

}
