import java.io.IOException;

import tsp.TSPSolver;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.vis.swt.Starter;


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
			System.err.println("usage: " + Solver.class.getCanonicalName() + " -file=<file>" );
			return;
		}
		
		ProblemData problemData = ProblemData.getProblemDataFromFile(fileName);
		TSPSolver tspSolver = new TSPSolver(problemData);
		printSolution(solve(tspSolver));
	}
	
	private static TourConfiguration solve(TSPSolver tspSolver) {
		return tspSolver.solve();
	}
	

	private static void printSolution(TourConfiguration configuration) {
		// calculate the length of the tour
		double obj = configuration.calculateTourLength();

		// prepare the solution in the specified output format
		System.out.println(obj + " 0");
		for (int i = 0; i < configuration.getSize(); i++) {
			System.out.print(configuration.get(i) + " ");
		}
		System.out.println("");
	}

}
