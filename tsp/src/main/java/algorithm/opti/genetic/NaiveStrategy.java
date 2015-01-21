package algorithm.opti.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tsp.util.Point;
import tsp.util.TourConfiguration;
import algorithm.opti.genetic.Crossover.CrossoverTour2Strategie;

import com.google.common.collect.TreeMultimap;

@Deprecated
public class NaiveStrategy implements CrossoverTour2Strategie {

	enum State {
		UNKNOWN, INSIDE, OUTSIDE;
	}

	@Override
	public List<Integer> calc(TourConfiguration parent2,
			final Integer startPoint,
			final Integer endPoint, Set<Integer> parent1SubTour) {

		int problemSize = parent2.getSize();
		int childIndex = parent1SubTour.size();

		class Bla {
			int parent2TourIndex;
			double distance;

			Bla(int parent2TourIndex, double distance) {
				this.parent2TourIndex = parent2TourIndex;
				this.distance = distance;
			}

			@Override
			public String toString() {
				return "Bla [parent2TourIndex=" + parent2TourIndex
						+ ", distance=" + distance + "]";
			}

		}
		List<Integer> parent2Tour = new ArrayList<>();
		Map<Integer, Bla> missingPossibleMatches = new HashMap<>();

		State state = State.UNKNOWN;
		for (int parent2Index = 0; parent2Tour.size()
				+ missingPossibleMatches.size() < problemSize
				- childIndex; parent2Index++) {
			Integer parent2Point = parent2.get(parent2Index % problemSize);
			if (parent2Point.equals(endPoint)) {
				state = State.OUTSIDE;
				// continue;
			}
			if (parent2Point.equals(startPoint)) {
				state = State.INSIDE;
				continue;
			}
			if (state == State.UNKNOWN) {
				continue;
			}
			if (parent1SubTour.contains(parent2Point)) {
				continue;
			}
			if (state == State.OUTSIDE) {
				parent2Tour.add(parent2Point);

			} else if (state == State.INSIDE) {
				int size = parent2Tour.size();
				if (size == 0) {
					missingPossibleMatches.put(parent2Point, new Bla(size,
							Double.MAX_VALUE));
				} else {
					Point point = parent2.getPoint(parent2Point);
					Point lastPoint = parent2.getPoint(parent2Tour
							.get(size - 1));
					missingPossibleMatches.put(parent2Point, new Bla(size,
							lastPoint.distance(point)));
				}
			}
		}
		
		for (int i = 0; i < parent2Tour.size(); i++) {
			Point currentPoint = parent2.getPoint(parent2Tour.get(i));
			for (Entry<Integer, Bla> entry : missingPossibleMatches
					.entrySet()) {
				Point point = parent2.getPoint(entry.getKey());
				double distance = currentPoint.distance(point);
				if (distance < entry.getValue().distance) {
					entry.setValue(new Bla(parent2Tour.size(), distance));
				}
			}
		}

		TreeMultimap<Integer, Integer> treeMap = TreeMultimap.create();

		for (Entry<Integer, Bla> entry : missingPossibleMatches.entrySet()) {
			treeMap.put(entry.getValue().parent2TourIndex, entry.getKey());
		}
		int index = 0;
		for (Entry<Integer, Integer> entry : treeMap.entries()) {
			parent2Tour.add(entry.getKey() + index, entry.getValue());
			index++;
		}
		return parent2Tour;
	}
}