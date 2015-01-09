package algorithm.opti.genetic;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import tsp.util.Point;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;

public class CrossoverTest {

	Crossover crossover = new Crossover();

	@Test
	public void testCrossover() throws Exception {

		ProblemData problemData = new ProblemData(Lists.newArrayList(new Point(
				0, 0, 1), new Point(0, 0, 1), new Point(0, 0, 2)));

		TourConfiguration parent2 = new TourConfiguration(problemData,
				ImmutableMap.of(1, 1, 2, 2, 3, 3));
		TourConfiguration parent1 = new TourConfiguration(problemData,
				ImmutableMap.of(1, 1, 2, 3, 3, 2));
		TourConfiguration child = crossover.crossover(parent1, parent2);
	}

}
