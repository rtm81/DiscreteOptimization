import java.util.List;

import com.google.common.primitives.Ints;


public class TourConfiguration {

	private final int[] solutions;
	private final ProblemData problemData;
	private Double tourLength;

	public TourConfiguration(ProblemData problemData) {
		solutions = new int[problemData.getProblemSize()];
		for (int i = 0; i < solutions.length; i++) {
			solutions[i] = -1;
		}
		this.problemData = problemData;
	}

	public static TourConfiguration create(ProblemData problemData) {
		return new TourConfiguration(problemData);
	}

	public static TourConfiguration create(TourConfiguration configuration) {
		return new TourConfiguration(configuration.problemData);
	}
	public void setStep(int index, int value) {
		solutions[index] = value;
		tourLength = null;
	}

	public boolean contains(int j) {
		return Ints.contains(solutions, j);
	}

	public int get(int i) {
		return solutions[i];
	}

	public double calculateTourLength() {
		if (tourLength == null) {
			tourLength = calculateTourLength(problemData.points, solutions);
		}
		return tourLength;
	}
	
	private static double calculateTourLength(List<Point> points,
			int[] solution) {
		double obj = points.get(solution[solution.length - 1]).distance(points.get(solution[0]));
		for (int i = 0; i < solution.length - 1; i++) {
			obj += points.get(solution[i]).distance(points.get(solution[i + 1]));
		}
		return obj;
	}

	public int getSize() {
		return problemData.getProblemSize();
	}

	public Point getPoint(int i) {
		return problemData.get(solutions[i]);
	}


}
