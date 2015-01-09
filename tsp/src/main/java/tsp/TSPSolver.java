package tsp;

import java.util.ArrayList;
import java.util.List;

import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;
import algorithm.init.InitializationStrategy;
import algorithm.init.SortedDistance;
import algorithm.opti.OptimizeStrategy;
import algorithm.opti.TwoOptAdvanced;

public class TSPSolver extends AbstractPublisher implements RunnablePublisher {
	
	private final ProblemData problemData;
	

	
	public TSPSolver(ProblemData problemData){
		this.problemData = problemData;
	}

	public TourConfiguration solve() {
		TourConfigurationCollection tourConfigurationCollection = init();
		
		TourConfiguration fittest = tourConfigurationCollection.getFittest();
		if (notify(fittest)) {
			return fittest;
		}
		
		tourConfigurationCollection = calculate(tourConfigurationCollection);
		return tourConfigurationCollection.getFittest();
	}



	public TourConfigurationCollection calculate(TourConfigurationCollection configuration) {
		OptimizeStrategy twoOpt =
				new TwoOptAdvanced();
//				new GAStrategy();
//				new TwoOpt();

		forwardListener(twoOpt);
		
		configuration = twoOpt.calculate(problemData, configuration);
		return configuration;
	}

	public TourConfigurationCollection init() {
		final InitializationStrategy initializationStrategy;
		SortedDistance sortedDistance = new SortedDistance(problemData);
		forwardListener(sortedDistance);
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
