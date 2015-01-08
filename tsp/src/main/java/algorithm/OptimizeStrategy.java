package algorithm;

import java.util.List;

import tsp.ConfigurationChangedListener;
import tsp.ProblemData;
import tsp.TourConfigurationCollection;

public interface OptimizeStrategy {

	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection configuration);

	public void addListener(List<ConfigurationChangedListener> listener);
}
