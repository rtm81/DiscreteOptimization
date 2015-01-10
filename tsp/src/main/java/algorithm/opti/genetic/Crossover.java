package algorithm.opti.genetic;

import java.util.Random;

import tsp.util.TourConfiguration;

public class Crossover {

	Random random;

	public TourConfiguration crossover(TourConfiguration parent1,
			TourConfiguration parent2) {
		int problemSize = parent1.getSize();
		int length = random.nextInt(problemSize - 1);
		if (length == 0) {
			return parent2;
		}
		if (length == problemSize - 1) {
			return parent1;
		}
		
		int startPosition = random.nextInt(problemSize);

		TourConfiguration child = TourConfiguration.create(parent1);

		Integer startPoint = parent1.get(startPosition);
		Integer endPoint = parent1.get((startPosition + length - 1) % problemSize);
		
		int parent2Index = 0;
		int childIndex = 0;
		for(; parent2Index < problemSize; parent2Index++, childIndex++) {
			Integer parent2Point = parent2.get(parent2Index);
			if (parent2Point.equals(startPoint)) {
				break;
			}
			child.setStep(childIndex, parent2Point);
		}
		
		for(int j = startPosition; j < startPosition + length; j++, childIndex++) {
			Integer parent1Point = parent1.get(j);
			child.setStep(childIndex, parent1Point);
		}
		
		boolean endPointFound = false;
		for(; childIndex < problemSize && parent2Index < (problemSize * 2); parent2Index++) {
			Integer parent2Point = parent2.get(parent2Index % problemSize);
			if (parent2Point.equals(endPoint)) {
				endPointFound = true;
				continue;
			}
			if (endPointFound) {
				child.setStep(childIndex++, parent2Point);
			}
		}
		
		
		return child;
	}
}
