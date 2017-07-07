package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;
import com.solarsystem.wheaterpredictor.core.helpers.TriangleHelper;

public class RainyWheater extends WheaterEventType {

	private static final Logger LOGGER = LoggerFactory.getLogger(RainyWheater.class);

	@Inject
	private TriangleHelper triangleHelper;
	
	@Override
	public String getName() {
		return "Clima lluvioso";
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {

		// assumes sun is in solar system center (0,0)
		RectangularCoord sunPosition = new RectangularCoord(0.0d, 0.0d);

		if (this.getOrbits() == null && this.getOrbits().size() != 3) {
			LOGGER.warn("Only 3 planets determines a rainy wheater!");
			throw new PatternCalculationError("Only 3 planets determines a rainy wheater!");
		}

		Integer firstOccurrence = null;
		Integer secondOcurrence = null;
		Integer extension = 0;
		Boolean count = false;

		for (int day = 1; day < SECOND_OCCURRENCE_ITERATIONS_LIMIT; day++) {
			final int predictionDay = day;

			// uses a set to disable repeat position (config, error) then a list
			// to retrieve elements by index later
			List<RectangularCoord> positions = new ArrayList<RectangularCoord>(
					this.getOrbits().stream().map(orbit -> orbit.calculatePosition(predictionDay).getRectangularCoord())
							.collect(Collectors.toSet()));

			if (triangleHelper.isSunBetweenPlanets(positions, sunPosition)) {
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
						secondOcurrence = day;
					}

				}
			} else {
				count = false;
				// chequea si esta terminando la segunda ocurrencia
				if (secondOcurrence != null) {
					break;
				}
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

	public TriangleHelper getTriangleHelper() {
		return triangleHelper;
	}

	public void setTriangleHelper(TriangleHelper triangleHelper) {
		this.triangleHelper = triangleHelper;
	}

}
