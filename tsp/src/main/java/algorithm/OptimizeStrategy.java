package algorithm;

import tsp.ProblemData;
import tsp.Publisher;
import tsp.TourConfigurationCollection;

public interface OptimizeStrategy extends Publisher {

	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection configuration);

}
