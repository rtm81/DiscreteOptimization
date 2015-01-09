package algorithm.init;

import tsp.Publisher;
import tsp.util.TourConfigurationCollection;

public interface InitializationStrategy extends Publisher {

	public TourConfigurationCollection calculate();
}
