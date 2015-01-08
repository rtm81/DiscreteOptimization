package tsp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;


public class TourConfiguration {

	/**
	 * key - position in this tour
	 * value - index of the point according to the ProblemData instance
	 */
	private final Map<Integer, Integer> solutionList;
	
	
	private final ProblemData problemData;
	private Double tourLength;

	/**
	 * Construct an empty tour.
	 */
	public TourConfiguration(ProblemData problemData) {
		this(problemData, new LinkedHashMap<Integer, Integer>());
	}
	
	private TourConfiguration(ProblemData problemData, Map<Integer, Integer> solutionList) {
		this.problemData = problemData;
		this.solutionList = solutionList;
	}

	public static TourConfiguration create(ProblemData problemData) {
		return new TourConfiguration(problemData);
	}

	public static TourConfiguration create(TourConfiguration configuration) {
		return new TourConfiguration(configuration.problemData);
	}
	
	public TourConfiguration copy() {
		return new TourConfiguration(this.problemData, ImmutableMap.copyOf(solutionList));
	}
	
    // Creates a random individual
    public static TourConfiguration createRandom(ProblemData problemData) {
    	
    	TourConfiguration tourConfiguration = new TourConfiguration(problemData);
    	List<Integer> list = new ArrayList<Integer>(problemData.getProblemSize());
    	for(int i= 0; i < problemData.getProblemSize(); i++) {
    		list.add(i);
    	}
    	// Randomly reorder the tour
    	Collections.shuffle(list);
    	
        // Loop through all our destination cities and add them to our tour
    	int i = 0;
        for (int cityIndex: list) {
        	tourConfiguration.setStep(i++, problemData.get(cityIndex).getId());
        }
        
        return tourConfiguration;
    }
	
	public void setStep(int tourPosition, int pointIndex) {
		solutionList.put(tourPosition, pointIndex);
		tourLength = null;
	}

	public boolean contains(int pointIndex) {
		return solutionList.containsValue(pointIndex);
	}

	public Integer get(int tourPosition) {
		return solutionList.get(tourPosition);
	}

	public double calculateTourLength() {
		if (tourLength == null) {
			tourLength = calculateTourLength(problemData.points, solutionList);
		}
		return tourLength;
	}
	
	private double calculateTourLength(List<Point> points,
			Map<Integer, Integer> solution) {
		double obj = points.get(solution.get(solution.size() - 1)).distance(points.get(solution.get(0)));
		for (int i = 0; i < solution.size() - 1; i++) {
			obj += points.get(solution.get(i)).distance(points.get(solution.get(i + 1)));
		}
		return obj;
	}

	public int getSize() {
		return solutionList.size();
	}

	public Point getPoint(int tourPosition) {
		Integer index = solutionList.get(tourPosition);
		if (index == null) {
			throw new IllegalArgumentException("No position ["+tourPosition+"]");
		}
		return problemData.get(index);
	}


	@Override
	public String toString() {
		return "" + calculateTourLength();
	}
	
}
