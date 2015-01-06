package algorithm.genetic;

import java.util.ArrayList;
import java.util.List;

import tsp.ProblemData;
import tsp.TSPSolver.ConfigurationChangedListener;
import tsp.TourConfiguration;
import algorithm.OptimizeStrategy;

public class GAStrategy implements OptimizeStrategy {

	List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	@Override
	public TourConfiguration calculate(ProblemData problemData,
			TourConfiguration configuration) {
		GA ga = new GA(0.015d, 5, false);
		Population population = new Population();
		population.addTour(configuration);
		for (int i = 0; i < 50; i++) {
			population.addTour(TourConfiguration.createRandom(problemData));
		}
		
		for (int i = 0; i < 100; i++) {
			population = ga.evolvePopulation(population);
			
			for (ConfigurationChangedListener configurationChangedListener : listener) {
				configurationChangedListener.changePerformed(population.getFittest());
			}
		}
		return population.getFittest();
	}

	@Override
	public void addListener(List<ConfigurationChangedListener> listeners) {
		listener.addAll(listeners);
	}

}
