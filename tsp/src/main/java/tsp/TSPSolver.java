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
import algorithm.opti.genetic.GAStrategy;

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
		OptimizeStrategy optimizeStrategy =
//				new TwoOptAdvanced();
				new GAStrategy();
//				new TwoOpt();

		forwardListener(optimizeStrategy);
		
		configuration = optimizeStrategy.calculate(problemData, configuration);
		return configuration;
	}

	public TourConfigurationCollection init() {
		final InitializationStrategy initializationStrategy = new SortedDistance(problemData);
		forwardListener(initializationStrategy);
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
