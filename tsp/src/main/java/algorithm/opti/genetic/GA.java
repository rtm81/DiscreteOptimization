package algorithm.opti.genetic;

import java.util.Random;

import org.eclipse.swt.widgets.Display;

import tsp.AbstractPublisher;
import tsp.util.TourConfiguration;
import tsp.vis.VisualizationData;
import tsp.vis.swt.Visualization;


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
//            Visualization.visualize(parent1, "parent1");
//            Visualization.visualize(parent2, "parent2");
//            Visualization.visualize(child, "child");
            System.out.println("hier");
        }
        
        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }
        double newTourLength = newPopulation.getTourLength();
        
        return newPopulation;
//        if (newTourLength < givenTourLength) {
//        	return newPopulation;
//        } else {
//        	return population;
//        }
    	
    }


    
    // Applies crossover to a set of parents and creates offspring
    public TourConfiguration crossover(TourConfiguration parent1, TourConfiguration parent2) {
        // Create new child tour
        TourConfiguration child = TourConfiguration.create(parent1);

        
        // Get start and end sub tour positions for parent1's tour
        int startPos = random.nextInt(parent1.getSize());
        int endPos = random.nextInt(parent1.getSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < parent1.getSize(); i++) {
            if (isInSubTour(startPos, endPos, parent1.getSize(), i)) {
            	child.setStep(i, parent1.get(i));
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.getSize(); i++) {
            // If child doesn't have the city add it
            if (child.contains(parent2.get(i))) {
            	continue;
            }
            // Loop to find a spare position in the child's tour
            for (int ii = 0; ii < parent2.getSize(); ii++) {
                // Spare position found, add city
                if (child.get(ii) == null) {
                    child.setStep(ii, parent2.get(i));
                    break;
                }
            }
        }
        return child;
    }

	protected boolean isInSubTour(int startPos, int endPos,
			int i) {
		// If our start position is less than the end position
		if (startPos < endPos && i > startPos && i < endPos) {
			return true;
		} // If our start position is larger
		else if (startPos > endPos) {
		    if (!(i < startPos && i > endPos)) {
		        return true;
		    }
		}
		return false;
	}
    
    // Selects candidate tour for crossover
    private TourConfiguration tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population();
        // For each place in the tournament get a random candidate tour and
        // add it
        for (int i = 0; i < tournamentSize; i++) {
            tournament.addTour(pop.getRandomTour());
        }
        // Get the fittest tour
        return tournament.getFittest();
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

	public boolean isInSubTour(int startPos, int length,
			int mod, int i) {
		i %= mod;
		if (isBetween(startPos, startPos + length, i)) {
			return true;
		}
		i += mod;
		if (isBetween(startPos, startPos + length, i)) {
			return true;
		}
		return false;
	}

	public boolean isBetween(int startPos, int endPos, int i) {
		return i >= startPos && i < endPos;
	}
}
