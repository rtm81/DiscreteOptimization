package algorithm;

import tsp.Publisher;
import tsp.TourConfigurationCollection;

public interface InitializationStrategy extends Publisher {

	public TourConfigurationCollection calculate();
}
