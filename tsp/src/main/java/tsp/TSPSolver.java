package tsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;
import algorithm.init.InitializationStrategy;
import algorithm.init.SortedDistance;
import algorithm.opti.OptimizeStrategy;
import algorithm.opti.genetic.GAStrategy;

public class TSPSolver extends AbstractPublisher implements RunnablePublisher {
	
	public static final Logger LOGGER = LoggerFactory
			.getLogger(TSPSolver.class);

	private final ProblemData problemData;
	
	private OptimizeStrategy optimizeStrategy = new GAStrategy();

	private InitializationStrategy initializationStrategy;

	public TSPSolver(ProblemData problemData){
		this.problemData = problemData;
		this.initializationStrategy = new SortedDistance();
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

		forwardListener(getOptimizeStrategy());
		
		configuration = getOptimizeStrategy().calculate(problemData,
				configuration);
		return configuration;
	}

	public TourConfigurationCollection init() {
		forwardListener(getInitializationStrategy());
		return getInitializationStrategy().calculate(problemData);
	}

	@Override
	public void run() {
		try {
			TourConfiguration tourConfiguration = solve();
			LOGGER.info("done " + tourConfiguration);
			notify(tourConfiguration);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public OptimizeStrategy getOptimizeStrategy() {
		return optimizeStrategy;
	}

	public void setOptimizeStrategy(OptimizeStrategy optimizeStrategy) {
		this.optimizeStrategy = optimizeStrategy;
	}

	public InitializationStrategy getInitializationStrategy() {
		return initializationStrategy;
	}

	public void setInitializationStrategy(InitializationStrategy initializationStrategy) {
		this.initializationStrategy = initializationStrategy;
	}
	
}
