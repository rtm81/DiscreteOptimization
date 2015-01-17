package algorithm.opti.genetic;

import java.util.Random;

import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;

public class Population extends TourConfigurationCollection {

	private final Random random = new Random();

	public Population() {
		super();
	}
	
	public Population(TourConfigurationCollection configuration) {
		super(configuration);
	}

	public double getTourLength() {
		double result = 0.0d;
		for (TourConfiguration tourConfiguration : population) {
			result += tourConfiguration.calculateTourLength();
		}
		return result;
	}
	
	public TourConfiguration selectMember() {

		// Get the total fitness
		double totalFitness = 0.0;
		for (TourConfiguration tourConfiguration : population) {
			double fitness = calculateFitness(tourConfiguration);
			totalFitness += fitness;
		}
		double slice = totalFitness * random.nextDouble();

		// Loop to find the node
		double ttot = 0.0;
		for (TourConfiguration tourConfiguration : population) {
			double fitness = calculateFitness(tourConfiguration);
			ttot += fitness;
			if (ttot >= slice) {
				return tourConfiguration;
			}
		}
		throw new RuntimeException("My algorithm sucks!");
	}

	public double calculateFitness(TourConfiguration tourConfiguration) {
		TourConfiguration fittest = getFittest();
		TourConfiguration mostUnfitting = getMostUnfitting();
		double fittestLength = fittest.calculateTourLength();
		double mostUnfittingLength = mostUnfitting.calculateTourLength();
		double lowerBound = fittestLength;
		double upperBound = mostUnfittingLength + 0.1d * (mostUnfittingLength - fittestLength);
		
		double currentTourLength = tourConfiguration.calculateTourLength();
		double result = 1.0d - (currentTourLength - fittestLength) / (upperBound - fittestLength);
		
		return result;
	}
	
    public TourConfiguration getRandomTour() {
		return population.get(random.nextInt(population.size()));
    }

}
