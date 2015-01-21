package algorithm.opti.genetic;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tsp.util.Point;
import tsp.util.TourConfiguration;

public class Crossover {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(Crossover.class);

	private Random random;
	
	public Crossover() {
		this(new Random());
	}

	public Crossover(Random random) {
		this.random = random;
	}

	public TourConfiguration crossover(TourConfiguration parent1,
			TourConfiguration parent2) {
		int problemSize = parent1.getSize();
		int length = random.nextInt(problemSize - 1) + 1;
		if (length == 1) {
			return parent2;
		}
		if (length == problemSize) {
			return parent1;
		}
		
		int startPosition = random.nextInt(problemSize);
		
		LOGGER.debug("parent1: {}", parent1);
		LOGGER.debug("parent2: {}", parent2);
		LOGGER.debug("length: {}", length);
		LOGGER.debug("startPosition: {}", startPosition);

		TourConfiguration child = TourConfiguration.create(parent1);

		final Integer startPointIndex = parent1.get(startPosition);
		final Integer endPointIndex = parent1.get((startPosition + length - 1) % problemSize);
		Point endPoint = parent1.getPoint((startPosition + length - 1)
				% problemSize);
		Point point = parent2.getProblemData().get(endPointIndex);
		if (!endPoint.equals(point)) {
			throw new RuntimeException();
		}
		
		LOGGER.debug("startPoint: {}", parent1.getPoint(startPosition));
		LOGGER.debug("endPoint: {}", endPoint);
		
		LinkedHashSet<Integer> parent1SubTour = parent1.getSteps(startPosition, length);
		child.setSteps(0, parent1SubTour);
		
		
		CrossoverTour2Strategie connectionPointsStrategy = new ConnectionPointsPermutationStrategy();
		List<Integer> parent2Tour = connectionPointsStrategy.calc(parent2,
				startPointIndex, endPointIndex, parent1SubTour);

		int childIndex = parent1SubTour.size();
		
		LOGGER.debug("childIndex: {}", childIndex);
		for (int i = 0; i < parent2Tour.size(); i++, childIndex++) {
			child.setStep(childIndex, parent2Tour.get(i));
		}
		
		return child;
	}
	
	public interface CrossoverTour2Strategie {
		public List<Integer> calc(TourConfiguration parent2,
				final Integer startPoint, final Integer endPoint,
				Set<Integer> parent1SubTour);
	}


}
