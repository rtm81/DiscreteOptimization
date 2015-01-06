import java.io.IOException;

import tsp.ProblemData;
import tsp.TSPSolver;
import tsp.TourConfiguration;
import tsp.TSPSolver.ConfigurationChangedListener;
import tsp.vis.awt.Visualization;


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
			if (arg.startsWith("-visual")) {
				visualize = true;
			}
		}
		if (fileName == null) {
			System.err.println("usage: " + Solver.class.getCanonicalName() + " -file=<file> [-visual]" );
			return;
		}
		
		ProblemData problemData = ProblemData.getProblemDataFromFile(fileName);


		TSPSolver tspSolver = new TSPSolver();
		if (visualize) {
			solveWithVisual(problemData, tspSolver);
		} else {
			printSolution(solve(problemData, tspSolver));
		}
	}
	
	private static TourConfiguration solve(ProblemData problemData, TSPSolver tspSolver) {
		return tspSolver.solve(problemData);
	}
	
	private static void solveWithVisual(ProblemData problemData, TSPSolver tspSolver) {
		final Visualization visualization = new Visualization(problemData);
		
		tspSolver.addListener(new TSPSolver.ConfigurationChangedListener() {
			
			@Override
			public boolean changePerformed(TourConfiguration configuration) {
				if (visualization.isDisposed()) {
					return true;
				} else {
					visualization.setConfiguration(configuration);
					return false;
				}
			}
		});
		
		TourConfiguration configuration = tspSolver.init(problemData);
		
		visualization.setConfiguration(configuration);
		
		configuration = tspSolver.calculate(problemData, configuration);
		
		while(!visualization.isDisposed());
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
