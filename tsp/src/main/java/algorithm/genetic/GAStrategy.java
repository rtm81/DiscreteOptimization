package algorithm.genetic;

import java.util.ArrayList;
import java.util.List;

import tsp.AbstractPublisher;
import tsp.ConfigurationChangedListener;
import tsp.ProblemData;
import tsp.TourConfigurationCollection;
import algorithm.OptimizeStrategy;

public class GAStrategy extends AbstractPublisher implements OptimizeStrategy {

	List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	@Override
	public TourConfigurationCollection calculate(ProblemData problemData,
			TourConfigurationCollection configuration) {
		Population population = new Population(configuration);
//		int missingNumber = 50 - population.populationSize();
//		for (int i = 0; i < missingNumber; i++) {
//			population.addTour(TourConfiguration.createRandom(problemData));
//		}
		GA ga = new GA(1.0d / (problemData.getProblemSize() * population.populationSize() * 5)
				, 5
				, false);
		
		for (int i = 0; i < 100; i++) {
			population = ga.evolvePopulation(population);
			
			if (notify(population.getFittest())){
				return population;
			}
		}
		return population;
	}

}
