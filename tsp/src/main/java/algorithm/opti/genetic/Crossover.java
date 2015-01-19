package algorithm.opti.genetic;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tsp.util.Point;
import tsp.util.TourConfiguration;

import com.google.common.collect.Collections2;

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
		
		Set<Integer> parent1SubTour = setSteps(parent1, startPosition, length,
				problemSize, child);
		
		int childIndex = parent1SubTour.size();
		
		LOGGER.debug("childIndex: {}", childIndex);
		
		CrossoverTour2Strategie connectionPointsStrategy = 
				new ConnectionPointsStrategy();
		List<Integer> parent2Tour = connectionPointsStrategy.calc(parent2,
				problemSize, startPointIndex, endPointIndex, childIndex, parent1SubTour);
		
		for (int i = 0; i < parent2Tour.size(); i++, childIndex++) {
			child.setStep(childIndex, parent2Tour.get(i));
		}
		
		return child;
	}
	
	public interface CrossoverTour2Strategie {
		public List<Integer> calc(TourConfiguration parent2, int problemSize,
				final Integer startPoint, final Integer endPoint,
				int childIndex, Set<Integer> parent1SubTour);
	}

	public Set<Integer> setSteps(TourConfiguration inputTour,
			int startPosition, int length, int problemSize,
			TourConfiguration outputTour) {
		Set<Integer> result = new LinkedHashSet<>();
		for (int currentPosition = startPosition; currentPosition < startPosition
				+ length; currentPosition++) {
			Integer pointIndex = inputTour.get(currentPosition % problemSize);
			outputTour.setStep(currentPosition - startPosition, pointIndex);
			result.add(pointIndex);
		}
		return result;
	}

	public static <E> Collection<List<E>> generatePerm(List<E> original) {
		return Collections2.permutations(original);
	}
}
