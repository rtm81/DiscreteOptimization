package tsp.util;

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
	
	public TourConfiguration getMostUnfitting() {
		if (population.isEmpty()) {
			throw new IllegalStateException("population is empty");
		}
		
		TourConfiguration mostUnfitting = population.get(0);
		for (TourConfiguration tourConfiguration : population) {
			if (tourConfiguration.calculateTourLength() > mostUnfitting
					.calculateTourLength()) {
				mostUnfitting = tourConfiguration;
			}
		}
		return mostUnfitting;
	}
}
