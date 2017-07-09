package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.inject.Inject;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.TriangleHelper;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public class RainestDayWheater extends RelatedWheaterEventType {

	@Inject
	private TriangleHelper triangleHelper;

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

	public TriangleHelper getTriangleHelper() {
		return triangleHelper;
	}

	public void setTriangleHelper(TriangleHelper triangleHelper) {
		this.triangleHelper = triangleHelper;
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
				day -> perimetersByDay.put(triangleHelper.getPerimeter(patternCalculatorBuffer.get(day)), day));
		Integer firstOccurrence = perimetersByDay.get(perimetersByDay.lastKey());
		progressivePattern.setFirstOccurrence(firstOccurrence);
		progressivePattern.setPeriod(period);
		progressivePattern.setExtension(0);

	}

}
