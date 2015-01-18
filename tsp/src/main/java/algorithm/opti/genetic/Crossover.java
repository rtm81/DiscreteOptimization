package algorithm.opti.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
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
		
		System.out.println("parent1: " + parent1);
		System.out.println("parent2: " + parent2);
		System.out.println("length: " + length);
		System.out.println("startPosition: " + startPosition);

		TourConfiguration child = TourConfiguration.create(parent1);

		final Integer startPoint = parent1.get(startPosition);
		final Integer endPoint = parent1.get((startPosition + length - 1) % problemSize);
		
		System.out.println("startPoint: " + parent1.getPoint(startPosition));
		System.out.println("endPoint: " + parent1.getPoint((startPosition + length - 1) % problemSize));
		
		Set<Integer> parent1SubTour = setParent1Steps(parent1, problemSize, length,
				startPosition, child);
		
		int childIndex = parent1SubTour.size();
		
		System.out.println(childIndex);
		
		Map<Point, List<Integer>> connectionPoints = calculateConnectionPoints(
				parent2, problemSize, startPoint, endPoint, parent1SubTour);
		
		Point currentPoint = parent1.getPoint(startPosition);
		List<Integer> parent2Tour = calculateShortestTour(parent2,
				connectionPoints, currentPoint);
		
		
//		List<Integer> parent2Tour = simpleAlgorithm(parent2, problemSize, child, startPoint, endPoint,
//				childIndex, parent1SubTour);
		
		for (int i = 0; i < parent2Tour.size(); i++, childIndex++) {
			child.setStep(childIndex, parent2Tour.get(i));
		}
		
		return child;
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
				currentPoint = parent2.getProblemData().get(list.get(list.size() - 1));
			} else {
				parent2Tour.addAll(Lists.reverse(list));
				currentPoint = parent2.getProblemData().get(list.get(0));
			}
			connectionPoints.remove(currentPoint);
		}
		return parent2Tour;
	}

	public Map<Point, List<Integer>> calculateConnectionPoints(
			TourConfiguration parent2, int problemSize,
			final Integer startPoint, final Integer endPoint,
			Set<Integer> parent1SubTour) {
		int parent2Index = 0;
		List<Integer> currentParent2Tour = new ArrayList<>();
		List<List<Integer>> allParent2Tours = new ArrayList<>();
		for(; parent2Index < problemSize; parent2Index++) {
			Integer parent2Point = parent2.get(parent2Index % problemSize);
			if (parent2Point.equals(endPoint)) {
				continue;
			}
			if (parent2Point.equals(startPoint)) {
				continue;
			}
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
			currentParent2Tour = new ArrayList<>();
		}
		
		Map<Point, List<Integer>> connectionPoints = new HashMap<>();
		for (List<Integer> list : allParent2Tours) {
			connectionPoints.put(parent2.getProblemData().get(list.get(0)), list);
			connectionPoints.put(parent2.getProblemData().get(list.get(list.size() - 1)), list);
		}
		return connectionPoints;
	}

	public Set<Integer> setParent1Steps(TourConfiguration parent1, int problemSize,
			int length, int startPosition, TourConfiguration child) {
		Set<Integer> parent1SubTour = new HashSet<>();
		int childIndex = 0;
		for(int parent1Index = startPosition; parent1Index < startPosition + length; 
				parent1Index++, childIndex++) {
			Integer parent1Point = parent1.get(parent1Index % problemSize);
			child.setStep(childIndex, parent1Point);
			parent1SubTour.add(parent1Point);
		}
		return parent1SubTour;
	}

	public List<Integer> simpleAlgorithm(TourConfiguration parent2, int problemSize,
			TourConfiguration child, final Integer startPoint,
			final Integer endPoint, int childIndex, Set<Integer> parent1SubTour) {
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
		return parent2Tour;
	}
}
