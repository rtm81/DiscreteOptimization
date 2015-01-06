package algorithm;

import java.util.Arrays;

import tsp.ProblemData;

public class DistanceMatrix {

	public double [] [] calculate(ProblemData problemData) {
		double [] [] distancMatrix = new double[problemData.getProblemSize()][problemData.getProblemSize()];
		for (int i = 0; i < distancMatrix.length; i++) {
			for (int j = 0; j < distancMatrix.length; j++) {
				if (i == j) {
					distancMatrix[i][j] = Double.NaN;
					continue;
				}
				distancMatrix[i][j] = problemData.get(i).distance(problemData.get(j));
			}
		}
		return distancMatrix;
	}
	
	public void print(ProblemData problemData) {
		System.out.println(Arrays.deepToString(calculate(problemData)));
	}
	
}
