package algorithm.opti.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tsp.util.Point;
import tsp.util.TourConfiguration;
import algorithm.opti.genetic.Crossover.CrossoverTour2Strategie;

import com.google.common.collect.Lists;

public class ConnectionPointsStrategy implements
		CrossoverTour2Strategie {

	@Override
	public List<Integer> calc(TourConfiguration parent2, Integer startPointIndex,
			Integer endPointIndex, Set<Integer> parent1SubTour) {

		int problemSize = parent2.getSize();


		List<List<Integer>> allParent2Tours = calculateConnectedSubtours(
				parent2, problemSize, parent1SubTour);

		return calc(parent2, allParent2Tours, endPointIndex);
	}

	protected List<Integer> calc(TourConfiguration parent2,
			List<List<Integer>> allParent2Tours, int endPointIndex) {
		Point currentPoint = parent2.getProblemData().get(endPointIndex);
		Map<Point, List<Integer>> connectionPoints = calculateConnectionPoints(
				parent2, allParent2Tours);
		List<Integer> parent2Tour = calculateShortestTour(parent2,
				connectionPoints, currentPoint);
		return parent2Tour;
	}

	protected Map<Point, List<Integer>> calculateConnectionPoints(
			TourConfiguration parent2, List<List<Integer>> allParent2Tours) {
		Map<Point, List<Integer>> connectionPoints = new HashMap<>();
		for (List<Integer> list : allParent2Tours) {
			connectionPoints.put(parent2.getProblemData().get(list.get(0)),
					list);
			connectionPoints
					.put(parent2.getProblemData().get(
							list.get(list.size() - 1)), list);
		}
		return connectionPoints;
	}


	protected List<List<Integer>> calculateConnectedSubtours(
			TourConfiguration parent2, int problemSize,
			Set<Integer> parent1SubTour) {
		List<Integer> currentParent2Tour = new ArrayList<>();
		List<List<Integer>> allParent2Tours = new ArrayList<>();
		for (int parent2Index = 0; parent2Index < problemSize; parent2Index++) {
			Integer parent2Point = parent2.get(parent2Index % problemSize);

			if (parent1SubTour.contains(parent2Point)) {
				if (!currentParent2Tour.isEmpty()) {
					allParent2Tours.add(currentParent2Tour);
					currentParent2Tour = new ArrayList<>();
				}
			} else {
				currentParent2Tour.add(parent2Point);
			}
		}
		if (!currentParent2Tour.isEmpty()) {
			allParent2Tours.add(currentParent2Tour);
		}
		return allParent2Tours;
	}

	public List<Integer> calculateShortestTour(TourConfiguration parent2,
			Map<Point, List<Integer>> connectionPoints, Point currentPoint) {
		List<Integer> parent2Tour = new ArrayList<>();

		while (!connectionPoints.isEmpty()) {

			Point nearestToCurrent = null;
			double nearestDistance = Double.MAX_VALUE;
			for (Point point : connectionPoints.keySet()) {
				double distanceToPoint = currentPoint.distance(point);
				if (distanceToPoint < nearestDistance) {
					nearestToCurrent = point;
					nearestDistance = distanceToPoint;
				}
			}

			List<Integer> list = connectionPoints.remove(nearestToCurrent);
			if (list.get(0).equals(nearestToCurrent.getId())) {
				parent2Tour.addAll(list);
				currentPoint = parent2.getProblemData().get(
						list.get(list.size() - 1));
			} else {
				parent2Tour.addAll(Lists.reverse(list));
				currentPoint = parent2.getProblemData().get(list.get(0));
			}
			connectionPoints.remove(currentPoint);
		}
		return parent2Tour;
	}
}