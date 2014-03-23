import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;


public class Solver {

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

//		double [] [] distancMatrix = new double[nodeCount][nodeCount];
//		for (int i = 0; i < distancMatrix.length; i++) {
//			for (int j = 0; j < distancMatrix.length; j++) {
//				if (i == j) {
//					distancMatrix[i][j] = Double.NaN;
//					continue;
//				}
//				distancMatrix[i][j] = distance(points.get(i), points.get(j));
//			}
//		}
		
//		System.out.println(Arrays.deepToString(distancMatrix));
		
		
		// build a trivial solution
		// visit the nodes in the order they appear in the file
		int[] solution = new int[nodeCount];
		solution[0] = 0;
		for (int i = 1; i < solution.length; i++) {
			
			double[] distances = new double[nodeCount];
			for (int j = 0; j < nodeCount; j++) {
				if (Ints.contains(solution, j) || i == j) {
					distances[j] = Double.NaN;
					continue;
				}
				distances[j] = distance(points.get(solution[i-1]), points.get(j));
			}
			solution[i] = smallest(distances);
		}

		// calculate the length of the tour
		double obj = distance(points.get(solution[nodeCount - 1]), points.get(solution[0]));
		for (int i = 0; i < solution.length - 1; i++) {
			obj += distance(points.get(solution[i]), points.get(solution[i + 1]));
		}

		// prepare the solution in the specified output format

		System.out.println(obj + " 0");
		for (int i = 0; i < solution.length; i++) {
			System.out.print(solution[i] + " ");
		}
		System.out.println("");
	}

	public static double distance(Point p1, Point p2) {
		return distance(p1.x, p1.y, p2.x, p2.y);
	}
	public static double distance(float x1, float y1, float x2, float y2) {
		double deltaX = x1 - x2;
		double deltaY = y1 - y2;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
	static class Point {
		private float x;
		private float y;
		
		Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
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
