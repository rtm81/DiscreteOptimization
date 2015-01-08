import java.io.IOException;

import tsp.ConfigurationChangedListener;
import tsp.ProblemData;
import tsp.TSPSolver;
import tsp.TourConfiguration;
import tsp.TourConfigurationCollection;
import tsp.vis.VisualizationData;
import tsp.vis.swt.Visualization;


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
		boolean visualize = false;

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


		TSPSolver tspSolver = new TSPSolver(problemData);
		if (visualize) {
			solveWithVisual(problemData, tspSolver);
		} else {
			printSolution(solve(tspSolver));
		}
	}
	
	private static TourConfiguration solve(TSPSolver tspSolver) {
		return tspSolver.solve();
	}
	
	private static void solveWithVisual(final ProblemData problemData, final TSPSolver tspSolver) {
		final VisualizationData visualizationData = new VisualizationData(problemData);
		
		tspSolver.addListener(new ConfigurationChangedListener() {
			
			@Override
			public boolean changePerformed(TourConfiguration configuration) {
				final TourConfiguration configurationCopy = configuration.copy();
				visualizationData.setConfiguration(configurationCopy);
				return true;
			}
		});
		
		Visualization visualization = new Visualization(visualizationData, tspSolver);
		visualization.display();
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
