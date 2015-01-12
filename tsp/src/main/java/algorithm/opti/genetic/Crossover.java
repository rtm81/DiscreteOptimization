package algorithm.opti.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.TreeMultimap;

import tsp.util.Point;
import tsp.util.TourConfiguration;

public class Crossover {

	private Random random;
	
	private enum State {
		UNKNOWN, INSIDE, OUTSIDE;
	}

	public Crossover() {
		this(new Random());
	}

	public Crossover(Random random) {
		this.random = random;
	}

	public TourConfiguration crossover(TourConfiguration parent1,
			TourConfiguration parent2) {
		int problemSize = parent1.getSize();
		int length = random.nextInt(problemSize - 1) + 1;
		if (length == 1) {
			return parent2;
		}
		if (length == problemSize) {
			return parent1;
		}
		
		int startPosition = random.nextInt(problemSize);

		TourConfiguration child = TourConfiguration.create(parent1);

		final Integer startPoint = parent1.get(startPosition);
		final Integer endPoint = parent1.get((startPosition + length - 1) % problemSize);
		
		int childIndex = 0;
		Set<Integer> parent1SubTour = new HashSet<>();
		for(int parent1Index = startPosition; parent1Index < startPosition + length - 1 /* the last is not added */; 
				parent1Index++, childIndex++) {
			Integer parent1Point = parent1.get(parent1Index % problemSize);
			child.setStep(childIndex, parent1Point);
			parent1SubTour.add(parent1Point);
		}
		
		class Bla {
			int parent2TourIndex;
			double distance;
			Bla(int parent2TourIndex, double distance){
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
		int parent2Index = 0;
		State state = State.UNKNOWN;
		for(; parent2Tour.size() + missingPossibleMatches.size() < problemSize - childIndex; parent2Index++) {
			Integer parent2Point = parent2.get(parent2Index % problemSize);
			if (parent2Point.equals(endPoint)) {
				state = State.OUTSIDE;
//				continue;
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
					missingPossibleMatches.put(parent2Point, new Bla(size, Double.MAX_VALUE));
				} else {
					Point point = parent2.getPoint(parent2Point);
					Point lastPoint = parent2.getPoint(parent2Tour.get(size - 1));
					missingPossibleMatches.put(parent2Point, new Bla(size, lastPoint.distance(point)));
				}
//				parent2Tour.add(parent2Point);
			}
		}
		
		for (int i = 0; i < parent2Tour.size(); i++) {
			Point currentPoint = parent2.getPoint(parent2Tour.get(i));
			for (Entry<Integer, Bla> entry : missingPossibleMatches.entrySet()) {
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
		int blub = 0;
		for (Entry<Integer, Integer> entry : treeMap.entries()) {
			parent2Tour.add(entry.getKey() + blub, entry.getValue());
			blub++;
		}
		
		for (int i = 0; i < parent2Tour.size(); i++, childIndex++) {
			child.setStep(childIndex, parent2Tour.get(i));
		}
		
		return child;
	}
}
