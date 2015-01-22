package algorithm.opti.genetic;

import tsp.AbstractPublisher;
import tsp.util.ProblemData;
import tsp.util.TourConfigurationCollection;
import algorithm.opti.OptimizeStrategy;

public class GAStrategy extends AbstractPublisher implements OptimizeStrategy {

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

	@Override
	public boolean isSolveAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSolveAll(boolean solveAll) {
		// TODO Auto-generated method stub

	}

}
