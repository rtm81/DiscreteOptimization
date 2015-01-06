package tsp;

import java.util.ArrayList;
import java.util.List;

import algorithm.InitializationStrategy;
import algorithm.OptimizeStrategy;
import algorithm.SortedDistance;
import algorithm.TwoOpt;
import algorithm.TwoOptAdvanced;
import algorithm.genetic.GAStrategy;

public class TSPSolver {
	
	List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	public void addListener(ConfigurationChangedListener configurationChangedListener) {
		listener.add(configurationChangedListener);
	}
	
	public interface ConfigurationChangedListener {
		
		/**
		 * 
		 * @param configuration the changed {@link TourConfiguration}
		 * @return {@code true} if the calculation is to be canceled.
		 */
		public boolean changePerformed(TourConfiguration configuration);
	}

	public TourConfiguration solve(ProblemData problemData) {
		TourConfiguration configuration = init(problemData);
		
		for (ConfigurationChangedListener configurationChangedListener : listener) {
			if (configurationChangedListener.changePerformed(configuration)) {
				return configuration;
			}
		}
		
		configuration = calculate(problemData, configuration);
		return configuration;
	}

	public TourConfiguration calculate(ProblemData problemData,
			TourConfiguration configuration) {
		OptimizeStrategy twoOpt = new GAStrategy();
//				new TwoOpt();

		twoOpt.addListener(listener);
		
		configuration = twoOpt.calculate(problemData, configuration);
		return configuration;
	}

	public TourConfiguration init(ProblemData problemData) {
		final InitializationStrategy initializationStrategy;
		SortedDistance sortedDistance = new SortedDistance(problemData);
		sortedDistance.addListener(listener);
		if (problemData.getProblemSize() < 2000) {
			initializationStrategy = sortedDistance;
		} else {
			initializationStrategy = sortedDistance;
		}
		TourConfiguration configuration = initializationStrategy.calculate();
		return configuration;
	}

	
}
