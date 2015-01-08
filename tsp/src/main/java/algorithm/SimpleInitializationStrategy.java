package algorithm;

import tsp.AbstractPublisher;
import tsp.ProblemData;
import tsp.TourConfiguration;
import tsp.TourConfigurationCollection;

public class SimpleInitializationStrategy extends AbstractPublisher implements InitializationStrategy {

	private final ProblemData problemData;
	
	public SimpleInitializationStrategy(ProblemData problemData) {
		this.problemData = problemData;
	}
	
	@Override
	public TourConfigurationCollection calculate() {
		TourConfigurationCollection tourConfigurationCollection = new TourConfigurationCollection();
		TourConfiguration configuration = TourConfiguration.create(problemData);
		for (int i = 0; i < problemData.getProblemSize(); i++) {
			configuration.setStep(i, i);
		}
		tourConfigurationCollection.addTour(configuration);
		return tourConfigurationCollection;
	}

}
