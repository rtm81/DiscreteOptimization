package algorithm.opti;

import tsp.AbstractPublisher;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;

import com.google.common.collect.Lists;

public class TwoOpt extends AbstractPublisher implements OptimizeStrategy {

	protected static final int DEFAULT_MAX_NUMBER_OF_SWAPS = 10000;

	private boolean solveAll = false;

	@Override
	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection tourConfigurationCollection) {
		TourConfigurationCollection resultTourConfigurationCollection = new TourConfigurationCollection();
		if (!solveAll) {
			TourConfiguration configuration = tourConfigurationCollection
					.getFittest();
			configuration = twoOpt(problemData, configuration);
			resultTourConfigurationCollection.addTour(configuration);
		} else {
			for (TourConfiguration tourConfiguration : tourConfigurationCollection) {
				resultTourConfigurationCollection.addTour(twoOpt(problemData,
						tourConfiguration));
			}
		}
		return resultTourConfigurationCollection;
	}

	protected TourConfiguration twoOpt(ProblemData problemData,
			TourConfiguration configuration) {
		// 2-opt
		final int numberOfNodesEligibleToBeSwapped = problemData
				.getProblemSize();

		start_again: for (int numberOfSwaps = 0; numberOfSwaps < DEFAULT_MAX_NUMBER_OF_SWAPS; numberOfSwaps++) {
			double bestDistance = configuration.calculateTourLength();
			for (int i = 0; i < numberOfNodesEligibleToBeSwapped - 1; i++) {
				for (int k = i + 1; k < numberOfNodesEligibleToBeSwapped; k++) {
					TourConfiguration swappedTour = optSwap(configuration, i, k);

					double swapDistance = swappedTour.calculateTourLength();
					if (swapDistance < bestDistance) {
						configuration = swappedTour;

						if (notify(configuration)) {
							return configuration;
						}

						continue start_again;
					}
				}
			}
			break;
		}
		return configuration;
	}

	protected TourConfiguration optSwap(TourConfiguration configuration,
			final int i, final int k) {
		TourConfiguration resultTour = TourConfiguration.create(configuration);
		// 1. take route[0] to route[i-1] and add them in order to new_route
		resultTour.setSteps(0, configuration.getSteps(0, i));
		// 2. take route[i] to route[k] and add them in reverse order to
		// new_route
		resultTour.setSteps(i,
				Lists.reverse(configuration.getStepsList(i, k - i + 1)));
		// 3. take route[k+1] to end and add them in order to new_route
		resultTour.setSteps(k + 1, configuration.getSteps(k + 1,
				configuration.getSize() - (k + 1)));
		return resultTour;
	}

	public boolean isSolveAll() {
		return solveAll;
	}

	public void setSolveAll(boolean solveAll) {
		this.solveAll = solveAll;
	}
}
