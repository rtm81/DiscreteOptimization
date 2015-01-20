package algorithm.opti.genetic;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import tsp.util.TourConfiguration;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public class ConnectionPointsPermutationStrategy extends
		ConnectionPointsStrategy {

	@Override
	public List<Integer> calc(TourConfiguration parent2, int problemSize,
			Integer startPointIndex, Integer endPointIndex, int childIndex,
			Set<Integer> parent1SubTour) {

		// Point currentPoint = parent2.getProblemData().get(endPointIndex);
		List<List<Integer>> allParent2Tours = calculateConnectedSubtours(
				parent2, problemSize, parent1SubTour);

		if (allParent2Tours.size() > 7) {
			return super.calc(parent2, problemSize, startPointIndex,
					endPointIndex,
					childIndex, parent1SubTour);
		}

		int size = allParent2Tours.size();
		long bin = ((long) Math.pow(2, size)) - 1;
		// BigInteger bigInteger =
		// BigInteger.valueOf(2).pow(size).subtract(BigInteger.ONE);

		Collection<List<List<Integer>>> permutations = Collections2
				.permutations(allParent2Tours);
		TourConfiguration fittestTour = null;
		double fittestTourLength = Double.MAX_VALUE;
		for (List<List<Integer>> currentPermutation : permutations) {
			for (long i = 0; i <= bin; i++) {
				TourConfiguration currentTour = TourConfiguration
						.create(parent2.getProblemData());
				BitSet bitSet = BitSet.valueOf(new long[] { i });

				int currentTourIndex = 0;
				currentTour.setStep(currentTourIndex++, endPointIndex);
				for (int j = 0; j < currentPermutation.size(); j++) {
					List<Integer> subTour = currentPermutation.get(j);
					if (bitSet.get(j)) {
						subTour = Lists.reverse(subTour);
					}
					for (Integer pointIndex : subTour) {
						currentTour.setStep(currentTourIndex++, pointIndex);
					}
				}
				currentTour.setStep(currentTourIndex++, startPointIndex);
				if (currentTour.calculateTourLength() < fittestTourLength) {
					fittestTourLength = currentTour.calculateTourLength();
					fittestTour = currentTour;
				}
			}
		}

		return fittestTour.getSteps(1, fittestTour.getSize() - 2);
	}

	public static <E> Collection<List<E>> generatePerm(List<E> original) {
		return Collections2.permutations(original);
	}
}
