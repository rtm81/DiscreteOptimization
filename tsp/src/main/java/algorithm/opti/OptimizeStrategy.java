package algorithm.opti;

import tsp.Publisher;
import tsp.util.ProblemData;
import tsp.util.TourConfigurationCollection;

public interface OptimizeStrategy extends Publisher {

	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection configuration);

	public boolean isSolveAll();

	public void setSolveAll(boolean solveAll);
}
