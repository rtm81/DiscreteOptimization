package algorithm;

import java.util.ArrayList;
import java.util.List;

import tsp.ProblemData;
import tsp.TSPSolver.ConfigurationChangedListener;
import tsp.TourConfiguration;
import tsp.TourConfigurationCollection;

public class TwoOpt implements OptimizeStrategy {
	
	List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	public void addListener(ConfigurationChangedListener configurationChangedListener) {
		listener.add(configurationChangedListener);
	}

	public void addListener(List<ConfigurationChangedListener> listeners) {
		listener.addAll(listeners);
	}
	
	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection tourConfigurationCollection) {
		TourConfiguration configuration = tourConfigurationCollection.getFittest();
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
						
						for (ConfigurationChangedListener configurationChangedListener : listener) {
							if (configurationChangedListener.changePerformed(configuration)) {
								return tourConfigurationCollection;
							}
						}
						
						continue start_again;
					}
				}
			}
			break;
		}
		return tourConfigurationCollection;
	}
	
	protected TourConfiguration optSwap(TourConfiguration configuration,
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
}
