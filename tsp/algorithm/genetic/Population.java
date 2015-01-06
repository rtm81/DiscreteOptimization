package algorithm.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tsp.TourConfiguration;

public class Population {

	private final List<TourConfiguration> population = new ArrayList<>();

	private final Random random = new Random();
	
	public void addTour (TourConfiguration tourConfiguration) {
		population.add(tourConfiguration);
	}
	
	public double getTourLength() {
		double result = 0.0d;
		for (TourConfiguration tourConfiguration : population) {
			result += tourConfiguration.calculateTourLength();
		}
		return result;
	}
	
	public TourConfiguration getFittest() {
		if (population.isEmpty()) {
			throw new IllegalStateException("population is empty");
		}

		TourConfiguration fittest = population.get(0);
		for (TourConfiguration tourConfiguration : population) {
			if (tourConfiguration.calculateTourLength() < fittest
					.calculateTourLength()) {
				fittest = tourConfiguration;
			}
		}
		return fittest;
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
    	
		int randomId = random.nextInt(population.size());
        return population.get(randomId);
    }

	public int populationSize() {
		return population.size();
	}

	public TourConfiguration getTour(int i) {
		return population.get(i);
	}
}
