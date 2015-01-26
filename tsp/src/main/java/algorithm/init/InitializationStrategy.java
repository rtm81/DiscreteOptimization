package algorithm.init;

import tsp.Publisher;
import tsp.util.ProblemData;
import tsp.util.TourConfigurationCollection;

public interface InitializationStrategy extends Publisher {

	public TourConfigurationCollection calculate(ProblemData problemData);
}
