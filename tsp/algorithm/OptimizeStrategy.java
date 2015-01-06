package algorithm;

import java.util.List;

import tsp.ProblemData;
import tsp.TSPSolver.ConfigurationChangedListener;
import tsp.TourConfiguration;

public interface OptimizeStrategy {

	public TourConfiguration calculate(ProblemData problemData,
			TourConfiguration configuration);

	public void addListener(List<ConfigurationChangedListener> listener);
}
