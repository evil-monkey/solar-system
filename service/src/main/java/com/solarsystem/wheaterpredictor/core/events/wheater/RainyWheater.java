package com.solarsystem.wheaterpredictor.core.events.wheater;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.solarsystem.wheaterpredictor.core.PolarCoord.RectangularCoord;
import com.solarsystem.wheaterpredictor.core.exceptions.PatternCalculationError;

public class RainyWheater extends WheaterEventType {

	private static final Logger LOGGER = LoggerFactory.getLogger(RainyWheater.class);

	@Override
	public String getName() {
		return "Clima lluvioso";
	}

	@Override
	protected OrbitRelatedUniformEventPattern obtainPattern() throws PatternCalculationError {

		// assumes sun is in solar system center (0,0)

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

			if (positions.size() < 3) {
				LOGGER.warn("Can't draw a triangle with these position!");
				throw new PatternCalculationError("Can't draw a triangle with these position!");
			}

			if (isSunBetweenPlanets(positions)) {
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

	private boolean isSunBetweenPlanets(List<RectangularCoord> positions) {
		int mainOrientation = getTriangleOrientation(positions);
		RectangularCoord sunPosition = new RectangularCoord(0.0d, 0.0d);

		boolean result = false;

		// A1A2P, A2A3P, A3A1P
		List<RectangularCoord> st = Lists.newArrayList(positions);
		st.set(2, sunPosition);
		if (mainOrientation * getTriangleOrientation(st) > 0) {
			st = new ArrayList<>(3);
			st.addAll(positions.subList(1, 2));
			st.add(sunPosition);
			if (mainOrientation * getTriangleOrientation(st) > 0) {
				st = new ArrayList<>(3);
				st.add(positions.get(2));
				st.add(positions.get(0));
				st.add(sunPosition);

				result = mainOrientation * getTriangleOrientation(st) > 0;
			}
		}

		return result;
	}

	private int getTriangleOrientation(List<RectangularCoord> vertices) {
		// (A1.x - A3.x) * (A2.y - A3.y) - (A1.y - A3.y) * (A2.x - A3.x)
		return (vertices.get(0).getX() - vertices.get(2).getX()) * (vertices.get(1).getY() - vertices.get(2).getY())
				- (vertices.get(0).getY() - vertices.get(2).getY())
						* (vertices.get(1).getX() - vertices.get(2).getX()) >= 0 ? 1 : -1;
	}

}
