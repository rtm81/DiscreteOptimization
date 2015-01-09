package algorithm.opti.genetic;

import tsp.util.TourConfiguration;

public class Crossover {

    public TourConfiguration crossover(TourConfiguration parent1, TourConfiguration parent2) {
        TourConfiguration child = TourConfiguration.create(parent1);
        
        return child;
    }
}
