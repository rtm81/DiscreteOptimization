package algorithm.init;

import tsp.AbstractPublisher;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;

public class SimpleInitializationStrategy extends AbstractPublisher implements InitializationStrategy {

	
	@Override
	public TourConfigurationCollection calculate(ProblemData problemData) {
		TourConfigurationCollection tourConfigurationCollection = new TourConfigurationCollection();
		TourConfiguration configuration = TourConfiguration.create(problemData);
		for (int i = 0; i < problemData.getProblemSize(); i++) {
			configuration.setStep(i, i);
		}
		tourConfigurationCollection.addTour(configuration);
		return tourConfigurationCollection;
	}

}
