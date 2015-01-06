package algorithm;

import tsp.ProblemData;
import tsp.TSPSolver.ConfigurationChangedListener;
import tsp.TourConfiguration;

public class TwoOptAdvanced extends TwoOpt {

	@Override
	public TourConfiguration calculate(ProblemData problemData,
			TourConfiguration configuration) {
		// 2-opt
		int numberOfNodesEligibleToBeSwapped = problemData.getProblemSize();
		int numberOfSwaps = 0;
		start_again:
			while (true) {
				int oldnumberOfSwaps = numberOfSwaps;
				iLoop:
				for (int i = 0; i < numberOfNodesEligibleToBeSwapped - 1; i++) {
					if (numberOfSwaps > 10000) {
						break;
					}
					double best_distance = configuration.calculateTourLength();
					for (int k = i + 1; k < numberOfNodesEligibleToBeSwapped; k++) {
						TourConfiguration new_route = optSwap(configuration, i, k);
						
						double new_distance = new_route.calculateTourLength();
						if (new_distance < best_distance) {
							configuration = new_route;
							numberOfSwaps++;
							for (ConfigurationChangedListener configurationChangedListener : listener) {
								if (configurationChangedListener.changePerformed(configuration)) {
									return configuration;
								}
							}
							i--;
							continue iLoop;
						}
					}
				}
				if (oldnumberOfSwaps == numberOfSwaps) {
					break start_again;
				}
			}
		return configuration;
	}
	
}
