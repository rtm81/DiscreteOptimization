package tsp.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;


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
	
	public TourConfiguration(ProblemData problemData, Map<Integer, Integer> solutionList) {
		this.problemData = problemData;
		this.solutionList = solutionList;
	}

	public static TourConfiguration create(ProblemData problemData) {
		return new TourConfiguration(problemData);
	}

	public static TourConfiguration create(TourConfiguration configuration) {
		return new TourConfiguration(configuration.getProblemData());
	}
	
	public TourConfiguration copy() {
		return new TourConfiguration(this.getProblemData(), ImmutableMap.copyOf(solutionList));
	}
	
    // Creates a random individual
    public static TourConfiguration createRandom(ProblemData problemData) {
    	
    	TourConfiguration tourConfiguration = new TourConfiguration(problemData);
    	List<Integer> list = new ArrayList<Integer>(problemData.getProblemSize());
    	for(int i= 0; i < problemData.getProblemSize(); i++) {
    		list.add(i);
    	}
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

	public void setSteps(int startTourPosition, List<Integer> pointIndexes) {
		for (Integer pointIndex : pointIndexes) {
			solutionList.put(startTourPosition++, pointIndex);
		}
		tourLength = null;
	}

	public boolean contains(int pointIndex) {
		return solutionList.containsValue(pointIndex);
	}

	public Integer get(int tourPosition) {
		return solutionList.get(tourPosition);
	}

	public List<Integer> getSteps() {
		List<Integer> result = new ArrayList<Integer>();
		for (Integer pointIndex : solutionList.values()) {
			result.add(pointIndex);
		}
		return result;
	}
	public List<Integer> getSteps(int startTourPosition, int length) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = startTourPosition; i < startTourPosition + length; i++) {
			result.add(solutionList.get(i));
		}
		return result;
	}

	public double calculateTourLength() {
		if (tourLength == null) {
			tourLength = calculateTourLength(getProblemData().getPoints(), solutionList);
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
		Integer index = get(tourPosition);
		if (index == null) {
			throw new IllegalArgumentException("No position ["+tourPosition+"]");
		}
		return getProblemData().get(index);
	}


	@Override
	public String toString() {
		return "TourConfiguration [solutionList=" + solutionList
				+ ", problemData=" + problemData + ", tourLength=" + calculateTourLength()
				+ "]";
	}

	public ProblemData getProblemData() {
		return problemData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((problemData == null) ? 0 : problemData.hashCode());
		result = prime * result
				+ ((solutionList == null) ? 0 : solutionList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TourConfiguration other = (TourConfiguration) obj;
		if (problemData == null) {
			if (other.problemData != null)
				return false;
		} else if (!problemData.equals(other.problemData))
			return false;
		if (solutionList == null) {
			if (other.solutionList != null)
				return false;
		} else if (!solutionList.equals(other.solutionList))
			return false;
		return true;
	}

	
}
