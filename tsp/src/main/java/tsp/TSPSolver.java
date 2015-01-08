package tsp;

import java.util.ArrayList;
import java.util.List;

import algorithm.InitializationStrategy;
import algorithm.OptimizeStrategy;
import algorithm.SortedDistance;
import algorithm.TwoOptAdvanced;

public class TSPSolver implements Publisher {
	
	List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	private final ProblemData problemData;
	
	public void addListener(ConfigurationChangedListener configurationChangedListener) {
		listener.add(configurationChangedListener);
	}
	
	public TSPSolver(ProblemData problemData){
		this.problemData = problemData;
	}

	public TourConfiguration solve() {
		TourConfigurationCollection tourConfigurationCollection = init();
		
		for (ConfigurationChangedListener configurationChangedListener : listener) {
			if (configurationChangedListener.changePerformed(tourConfigurationCollection.getFittest())) {
				return tourConfigurationCollection.getFittest();
			}
		}
		
		tourConfigurationCollection = calculate(tourConfigurationCollection);
		return tourConfigurationCollection.getFittest();
	}

	public TourConfigurationCollection calculate(TourConfigurationCollection configuration) {
		OptimizeStrategy twoOpt =
				new TwoOptAdvanced();
//				new GAStrategy();
//				new TwoOpt();

		twoOpt.addListener(listener);
		
		configuration = twoOpt.calculate(problemData, configuration);
		return configuration;
	}

	public TourConfigurationCollection init() {
		final InitializationStrategy initializationStrategy;
		SortedDistance sortedDistance = new SortedDistance(problemData);
		sortedDistance.addListener(listener);
		if (problemData.getProblemSize() < 2000) {
			initializationStrategy = sortedDistance;
		} else {
			initializationStrategy = sortedDistance;
		}
		return initializationStrategy.calculate();
	}

	@Override
	public void run() {
		try {
			solve();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	
}
