import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;


public class Solver {
	
	static boolean visualize = false;

	/**
	 * The main class
	 */
	public static void main(String[] args) {
		try {
			solve(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the instance, solve it, and print the solution in the standard
	 * output
	 */
	public static void solve(String[] args) throws IOException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null) {
			System.err.println("no file");
			return;
		}
		
		ProblemData problemData = getProblemDataFromFile(fileName);
		

		TourConfiguration configuration = TourConfiguration.create(problemData);

//		calculateDistanceMatrix(nodeCount, points);
		
		
		configuration.setStep(0, 0);
		for (int i = 1; i < problemData.getProblemSize(); i++) {
			
			double[] distances = new double[problemData.getProblemSize()];
			for (int j = 0; j < problemData.getProblemSize(); j++) {
				if (configuration.contains(j) || i == j) {
					distances[j] = Double.NaN;
					continue;
				}
				distances[j] = problemData.get(configuration.get(i - 1)).distance(problemData.get(j));
			}
			configuration.setStep(i, smallest(distances));
		}

		// 2-opt
		int numberOfNodesEligibleToBeSwapped = problemData.getProblemSize();
		int numberOfSwaps = 0;
		start_again:
		while (true) {
			if (numberOfSwaps > 10000) {
				break;
			}
			double best_distance = configuration.calculateTourLength();
			for (int i = 0; i < numberOfNodesEligibleToBeSwapped - 1; i++) {
				for (int k = i + 1; k < numberOfNodesEligibleToBeSwapped; k++) {
					TourConfiguration new_route = optSwap(configuration, i, k);

					double new_distance = new_route.calculateTourLength();
					if (new_distance < best_distance) {
						configuration = new_route;
						numberOfSwaps++;
						continue start_again;
					}
				}
			}
			break;
		}
		
		// calculate the length of the tour
		double obj = configuration.calculateTourLength();

		// prepare the solution in the specified output format
		System.out.println(obj + " 0");
		for (int i = 0; i < problemData.getProblemSize(); i++) {
			System.out.print(configuration.get(i) + " ");
		}
		System.out.println("");
		
		if (visualize) {
			Visualization visualization = new Visualization(configuration, problemData);
			while(true);
		}
	}

	private static TourConfiguration optSwap(TourConfiguration configuration,
			int i, int k) {
		TourConfiguration new_route = TourConfiguration.create(configuration);
		// 1. take route[0] to route[i-1] and add them in order to new_route
		for (int j = 0; j < i; j++) {
			new_route.setStep(j, configuration.get(j));
		}
		// 2. take route[i] to route[k] and add them in reverse order to
		// new_route
		for (int j = i; j <= k; j++) {
			new_route.setStep(j, configuration.get(k - (j - i)));
		}
		// 3. take route[k+1] to end and add them in order to new_route
		for (int j = k + 1; j < configuration.getSize(); j++) {
			new_route.setStep(j, configuration.get(j));
		}
		return new_route;
	}

	private static ProblemData getProblemDataFromFile(String fileName) throws IOException {
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String firstLine = lines.get(0);
		int nodeCount = Integer.parseInt(firstLine);
		
		
		List<Point> points = new ArrayList<>(nodeCount);
		for (int i = 1; i < nodeCount + 1; i++) {
			String line = lines.get(i);
			String[] node = line.split("\\s+");
			points.add(new Point(Float.valueOf(node[0]), Float.valueOf(node[1])));
		}
		return new ProblemData(points);
	}



	private static void calculateDistanceMatrix(int nodeCount,
			List<Point> points) {
		double [] [] distancMatrix = new double[nodeCount][nodeCount];
		for (int i = 0; i < distancMatrix.length; i++) {
			for (int j = 0; j < distancMatrix.length; j++) {
				if (i == j) {
					distancMatrix[i][j] = Double.NaN;
					continue;
				}
				distancMatrix[i][j] = points.get(i).distance(points.get(j));
			}
		}
		System.out.println(Arrays.deepToString(distancMatrix));
	}


	public static int smallest(double[] array) {
		int result = -1;
		double smallest = Double.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < smallest) {
				result = i;
				smallest = array[i];
			}
		}
		return result;
	}
}
