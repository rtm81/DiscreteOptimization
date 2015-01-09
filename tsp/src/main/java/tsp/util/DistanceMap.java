package tsp.util;

import java.util.Comparator;
import java.util.NavigableSet;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.TreeMultimap;

public class DistanceMap {
	
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
	
	
	public DistanceMap(DistanceMap distanceMap) {
		this.distanceOrigin.putAll(distanceMap.distanceOrigin);
		this.inverseDistanceOrigin = distanceMap.inverseDistanceOrigin;
	}
	
	
	public DistanceMap(ProblemData problemData, Point origin) {
		for (Point point : problemData) {
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