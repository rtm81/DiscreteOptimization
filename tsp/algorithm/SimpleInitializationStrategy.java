package algorithm;

import tsp.ProblemData;
import tsp.TourConfiguration;

public class SimpleInitializationStrategy implements InitializationStrategy {

	private final ProblemData problemData;
	
	public SimpleInitializationStrategy(ProblemData problemData) {
		this.problemData = problemData;
	}
	
	@Override
	public TourConfiguration calculate() {
		TourConfiguration configuration = TourConfiguration.create(problemData);
		for (int i = 0; i < problemData.getProblemSize(); i++) {
			configuration.setStep(i, i);
		}
		return configuration;
	}

}
