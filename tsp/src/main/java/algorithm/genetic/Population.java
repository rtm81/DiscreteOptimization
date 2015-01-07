package algorithm.genetic;

import java.util.Random;

import tsp.TourConfiguration;
import tsp.TourConfigurationCollection;

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
		return 1.0d / tourConfiguration.calculateTourLength();
	}
	
    public TourConfiguration getRandomTour() {
		return population.get(random.nextInt(population.size()));
    }

}
