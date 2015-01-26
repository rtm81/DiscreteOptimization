package algorithm.init;

import tsp.AbstractPublisher;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;

public class NearestNeighbor extends AbstractPublisher implements InitializationStrategy {

	public int getIndexOfSmallestValue(double[] array) {
		int result = -1;
		double smallest = Double.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < smallest) {
				result = i;
				smallest = array[i];
			}
		}
		return result;
	}

	@Override
	public TourConfigurationCollection calculate(ProblemData problemData) {
		TourConfigurationCollection tourConfigurationCollection = new TourConfigurationCollection();
		TourConfiguration configuration = TourConfiguration.create(problemData);
		configuration.setStep(0, 0);
		for (int i = 1; i < problemData.getProblemSize(); i++) {
			
			double[] distances = new double[problemData.getProblemSize()];
			for (int j = 0; j < problemData.getProblemSize(); j++) {
				if (configuration.contains(j) || i == j) {
					distances[j] = Double.NaN;
					continue;
				}
				distances[j] = problemData.get(configuration.get(i - 1)).distance(problemData.get(j));
			}
			configuration.setStep(i, getIndexOfSmallestValue(distances));
		}
		tourConfigurationCollection.addTour(configuration);
		return tourConfigurationCollection;
	}
}
