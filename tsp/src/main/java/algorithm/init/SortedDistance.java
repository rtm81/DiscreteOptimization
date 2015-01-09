package algorithm.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import tsp.AbstractPublisher;
import tsp.ConfigurationChangedListener;
import tsp.util.DistanceMap;
import tsp.util.Point;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;

import com.google.common.base.Function;

public class SortedDistance extends AbstractPublisher implements InitializationStrategy  {

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
		
//		Random random = new Random();
//		for (int j = 0; j < 7; j++) {
//			int start;
//			if (j == 0) {
//				start = 0;
//			} else {
//				start = random.nextInt(problemData.getProblemSize());
//			}
//			for (int i = 1; i <= 7; i++) {
//				tourConfigurationCollection.addTour(new TourCalculation(i, start).calculateTour());
//			}
//		}
		
		tourConfigurationCollection.addTour(new TourCalculation(5, 0).calculateTour());
		
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
