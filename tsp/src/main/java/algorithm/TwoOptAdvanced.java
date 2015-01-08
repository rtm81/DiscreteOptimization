package algorithm;

import tsp.ConfigurationChangedListener;
import tsp.ProblemData;
import tsp.TourConfiguration;
import tsp.TourConfigurationCollection;

public class TwoOptAdvanced extends TwoOpt {

	@Override
	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection tourConfigurationCollection) {
		TourConfiguration configuration = tourConfigurationCollection.getFittest();
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
							if (notify(configuration)){
								return tourConfigurationCollection;
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
		return tourConfigurationCollection;
	}
	
}
