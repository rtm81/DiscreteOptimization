package algorithm.opti;

import tsp.util.ProblemData;
import tsp.util.TourConfiguration;

public class TwoOptAdvanced extends TwoOpt {


	@Override
	protected TourConfiguration twoOpt(ProblemData problemData,
			TourConfiguration configuration) {
		// 2-opt
		int numberOfNodesEligibleToBeSwapped = problemData.getProblemSize();
		int numberOfSwaps = 0;
		int oldnumberOfSwaps;
		do {
			oldnumberOfSwaps = numberOfSwaps;

			iLoop: for (int i = 0; i < numberOfNodesEligibleToBeSwapped - 1; i++) {
				if (numberOfSwaps > DEFAULT_MAX_NUMBER_OF_SWAPS) {
					break;
				}
				double bestDistance = configuration.calculateTourLength();
				for (int k = i + 1; k < numberOfNodesEligibleToBeSwapped; k++) {
					TourConfiguration swappedTour = optSwap(configuration, i, k);

					double swapDistance = swappedTour.calculateTourLength();
					if (swapDistance < bestDistance) {
						configuration = swappedTour;
						numberOfSwaps++;
						if (notify(configuration)) {
							return configuration;
						}
						i--;
						continue iLoop;
					}
				}
			}
		} while (oldnumberOfSwaps != numberOfSwaps);

		return configuration;
	}
	

}
