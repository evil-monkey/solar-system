package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.PolygonHelper;
import com.solarsystem.wheaterpredictor.core.orbits.OrbitRelatedUniformEventPattern;
import com.solarsystem.wheaterpredictor.core.orbits.PolarCoord.RectangularCoord;

public final class RainyWheater extends WheaterEventType {

	@Inject
	private PolygonHelper polygonHelper;

	@Override
	public String getName() {
		return "Clima lluvioso";
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {

		// assumes sun is in solar system center (0,0)
		RectangularCoord sunPosition = new RectangularCoord(0.0d, 0.0d);

		Integer firstOccurrence = null;
		Integer secondOcurrence = null;
		Integer extension = 0;
		Boolean count = false;

		for (int day = 0; day < this.getPatternCalculationMaxIterations(); day++) {
			final int predictionDay = day;

			// uses a set to disable repeat position (config, error) then a list
			// to retrieve elements by index later
			List<RectangularCoord> positions = new ArrayList<RectangularCoord>(
					this.getOrbits().stream().map(orbit -> orbit.calculatePosition(predictionDay).getRectangularCoord())
							.collect(Collectors.toSet()));

			if (polygonHelper.isPointBetweenVertices(positions, sunPosition)) {
				// solo cuenta para la primera, asume q la segunda tiene la
				// misma extensión
				if (count) {
					extension++;
					updatePositionsForRelatedEvents(predictionDay, positions);
				} else {
					if (firstOccurrence == null) {
						firstOccurrence = day;
						count = true;
						updatePositionsForRelatedEvents(predictionDay, positions);
					} else {
						// obtuvo la segunda ocurrencia, sale
						secondOcurrence = day;
						break;
					}

				}
			} else {
				count = false;
			}

		}

		if (firstOccurrence == null || secondOcurrence == null) {
			throw new PatternCalculationError("Can't get a pattern for event.");
		}

		updatePeriodForRelatedEvents(secondOcurrence - firstOccurrence);

		return new OrbitRelatedUniformEventPattern(firstOccurrence, secondOcurrence, extension);

	}

	private void updatePeriodForRelatedEvents(int period) {
		if (this.getRelatedEventTypes() != null) {
			this.getRelatedEventTypes().stream()
					.forEach(relatedEvent -> relatedEvent.calculatePatternFromPositions(period));
		}
	}

	private void updatePositionsForRelatedEvents(final int predictionDay, List<RectangularCoord> positions) {
		if (this.getRelatedEventTypes() != null) {
			this.getRelatedEventTypes().stream()
					.forEach(relatedEvent -> relatedEvent.addPositionsData(positions, predictionDay));
		}
	}

}
