package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;

import tsp.Point;
import tsp.ProblemData;
import tsp.TourConfigurationCollection;
import tsp.TSPSolver.ConfigurationChangedListener;
import tsp.TourConfiguration;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.TreeMultimap;

public class SortedDistance implements InitializationStrategy {

	private ProblemData problemData;
	private final DistanceMap distanceUpperRight;
	private final DistanceMap distanceOrigin;
	
	List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	public void addListener(ConfigurationChangedListener configurationChangedListener) {
		listener.add(configurationChangedListener);
	}

	public void addListener(List<ConfigurationChangedListener> listeners) {
		listener.addAll(listeners);
	}
	
	private static class DistanceMap {
		
		private static final Comparator<Double> doubleComparator = new Comparator<Double>() {

			@Override
			public int compare(Double o1, Double o2) {
				return Double.compare(o1, o2);
			}
		};
		
		private static final  Comparator<Point> pointComparator = new Comparator<Point>(){

			@Override
			public int compare(Point o1, Point o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		};
		
		private final TreeMultimap<Double, Point> distanceOrigin = 
				TreeMultimap.create(doubleComparator, pointComparator);
		private final ImmutableMultimap<Point,Double> inverseDistanceOrigin;
		
		
		DistanceMap(DistanceMap distanceMap) {
			this.distanceOrigin.putAll(distanceMap.distanceOrigin);
			this.inverseDistanceOrigin = distanceMap.inverseDistanceOrigin;
		}
		
		
		DistanceMap(ProblemData problemData, Point origin) {
			for (int i = 0; i < problemData.getProblemSize(); i++) {
				Point point = problemData.get(i);
				distanceOrigin.put(point.distance(origin), point);
			}
			inverseDistanceOrigin = ImmutableMultimap.copyOf(distanceOrigin).inverse();
		}
		
		public void remove(Point point) {
			distanceOrigin.remove(inverseDistanceOrigin.get(point).iterator().next(), point);
		}
		
		public Double get(Point point) {
			ImmutableCollection<Double> distances = inverseDistanceOrigin.get(point);
			if (distances.size() != 1) {
				throw new IllegalStateException("unexpected collection: " + distances);
			}
			return distances.iterator().next();
		}

		public NavigableSet<Point> get(Double value) {
			return distanceOrigin.get(value);
		}
		
		public Function<Double, Double> getNextHigherDistanceProvider () {
			return new Function<Double, Double>() {

					@Override
					public Double apply(Double higher) {
						return distanceOrigin.keySet().higher(higher);
					}};
		}
		
		public Function<Double, Double> getNextLowerDistanceProvider () {
			return new Function<Double, Double>() {

					@Override
					public Double apply(Double higher) {
						return distanceOrigin.keySet().lower(higher);
					}};
		}
	}
	
	public SortedDistance(ProblemData problemData) {
		this.problemData = problemData;
		
		Point origin = new Point(0.0f, 0.0f);
		Point upperRight = new Point(problemData.getLargest().getX(), 0.0f);
		
		distanceOrigin = new DistanceMap(problemData, origin);
		distanceUpperRight = new DistanceMap(problemData, upperRight);
	}
	
	
	@Override
	public TourConfigurationCollection calculate() {
		TourConfigurationCollection tourConfigurationCollection = new TourConfigurationCollection();
		
		Random random = new Random();
		for (int j = 0; j < 7; j++) {
			int start;
			if (j == 0) {
				start = 0;
			} else {
				start = random.nextInt(problemData.getProblemSize());
			}
			for (int i = 1; i <= 7; i++) {
				tourConfigurationCollection.addTour(new TourCalculation(i, start).calculateTour());
			}
		}
		
		return tourConfigurationCollection;
	}
	
	public class TourCalculation {
		
		private final int steps;
		private final DistanceMap sessionDistanceUpperRight;
		private final DistanceMap sessionDistanceOrigin;
		private final int startPoint;
		
		public TourCalculation() {
			this(7, 0);
		}
		
		public TourCalculation(int steps, int startPoint) {
			this.steps = steps;
			this.startPoint = startPoint % problemData.getProblemSize();
			this.sessionDistanceUpperRight = new DistanceMap(distanceUpperRight);
			this.sessionDistanceOrigin = new DistanceMap(distanceOrigin);
		}
		
		public TourConfiguration calculateTour() {
			TourConfiguration configuration = TourConfiguration.create(problemData);
			
			setStep(configuration, 0, problemData.get(startPoint));
			for (int i = 1; i < problemData.getProblemSize(); i++) {
				Point previousPoint = configuration.getPoint(i - 1);
				Set<Point> possibleNext = new HashSet<Point>();
				possibleNext.add(findNextNearest(previousPoint, configuration, sessionDistanceUpperRight));
				possibleNext.add(findNextNearest(previousPoint, configuration, sessionDistanceOrigin));
				
				setStep(configuration, i, getNearest(possibleNext, previousPoint));
			}
			return configuration;
		}
		
		private void setStep(TourConfiguration configuration, int index, Point point) {
			sessionDistanceOrigin.remove(point);
			sessionDistanceUpperRight.remove(point);
			configuration.setStep(index, point.getId());
			
			for (ConfigurationChangedListener configurationChangedListener : listener) {
				configurationChangedListener.changePerformed(configuration);
			}
		}
		
		private Point findNextNearest(
				Point previous,
				final TourConfiguration configuration, final DistanceMap distanceMap) {
			Set<Point> result = new HashSet<Point>();
			Double distanceOfPrevious = distanceMap.get(previous);
			
			result.add(findNextNearest(distanceMap, distanceOfPrevious,
					distanceMap.getNextHigherDistanceProvider(), previous));
			result.add(findNextNearest(distanceMap, distanceOfPrevious,
					distanceMap.getNextLowerDistanceProvider(), previous));
			return getNearest(result, previous);
		}
		

		private Point findNextNearest(DistanceMap distanceMap,
				Double distanceToCurrent, Function<Double, Double> nextDistanceProvider, Point current) {
			
			Double distance = distanceToCurrent;
			Set<Point> result = new HashSet<Point>();
			for (int i = 0; i < steps; i++) {
				distance = nextDistanceProvider.apply(distance);
				if (distance == null) {
					break;
				}
				
				result.addAll(distanceMap.get(distance));
			}
			return getNearest(result, current);
		}
	}


	
	public Point getNearest(Set<Point> neighbors, Point point) {
		Point result = null;
		double smallest = Double.MAX_VALUE;
		for (Point neighbor: neighbors) {
			if (neighbor == null) continue;
			double distance = neighbor.simpleDistance(point);
			if (distance < smallest) {
				result = neighbor;
				smallest = distance;
			}
		}
		return result;
	}




}
