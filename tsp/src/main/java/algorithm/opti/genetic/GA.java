package algorithm.opti.genetic;

import java.util.Random;

import tsp.AbstractPublisher;
import tsp.util.TourConfiguration;


public class GA extends AbstractPublisher {

    private final double mutationRate;
    private final int tournamentSize;
    private final boolean elitism;
    private final Random random;
    private final Crossover crossover;
    
    public GA (double mutationRate, int tournamentSize, boolean elitism, Random random) {
    	this.mutationRate = mutationRate;
    	this.tournamentSize = tournamentSize;
    	this.elitism = elitism;
    	this.random = random;
    	this.crossover = new Crossover(random);
    }
    
    public GA () {
    	this(0.001d, 5, true, new Random());
    }
	
    public GA(double mutationRate, int tournamentSize, boolean elitism) {
    	this(mutationRate, tournamentSize, elitism, new Random());
	}

	public Population evolvePopulation(Population population) {
    	Population newPopulation = new Population();
    	
    	int elitismOffset = 0;
        if (elitism) {
            newPopulation.addTour(population.getFittest());
            elitismOffset = 1;
        }
        
        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < population.populationSize(); i++) {
            // Select parents
            TourConfiguration parent1 = population.selectMember();
            TourConfiguration parent2 = population.selectMember();
            // Crossover parents
            TourConfiguration child = crossover.crossover(parent1, parent2);
            // Add child to new population
            newPopulation.addTour(child);
			// Visualization.visualize(parent1, "parent1");
			// Visualization.visualize(parent2, "parent2");
			// Visualization.visualize(child, "child");
        }
        
        // Mutate the new population a bit to add some new genetic material
		for (TourConfiguration tourConfiguration : newPopulation) {
			mutate(tourConfiguration);
		}

		double oldTourLength = population.getTourLength();
        double newTourLength = newPopulation.getTourLength();
        
        return newPopulation;
    	
    }


    
    // Mutate a tour using swap mutation
    private void mutate(TourConfiguration tour) {
        // Loop through tour cities
        for(int tourPos1=0; tourPos1 < tour.getSize(); tourPos1++){
            // Apply mutation rate
            if(random.nextDouble() < mutationRate){
                // Get a second random position in the tour
                int tourPos2 = random.nextInt(tour.getSize());

                // Get the cities at target position in tour
                Integer city1 = tour.get(tourPos1);
                Integer city2 = tour.get(tourPos2);

                // Swap them around
                tour.setStep(tourPos2, city1);
                tour.setStep(tourPos1, city2);
            }
        }
    }

}
