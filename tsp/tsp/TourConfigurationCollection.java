package tsp;

import java.util.ArrayList;
import java.util.List;

public class TourConfigurationCollection {

	protected final List<TourConfiguration> population;
	
	public TourConfigurationCollection(List<TourConfiguration> population) {
		this.population = population;
	}
	
	public TourConfigurationCollection() {
		this(new ArrayList<TourConfiguration>());
	}
	
	public TourConfigurationCollection(TourConfigurationCollection configuration) {
		this(configuration.population);
	}

	public void addTour (TourConfiguration tourConfiguration) {
		population.add(tourConfiguration);
	}
	
	public int populationSize() {
		return population.size();
	}

	public TourConfiguration getTour(int i) {
		return population.get(i);
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
}
