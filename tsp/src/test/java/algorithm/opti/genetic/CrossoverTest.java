package algorithm.opti.genetic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tsp.util.Point;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@RunWith(MockitoJUnitRunner.class)
public class CrossoverTest {

	@InjectMocks
	Crossover crossover = new Crossover();

	@Mock
	Random random;
	
	ProblemData problemData4Points = new ProblemData(ImmutableList.of(new Point(0,
			0, 0), new Point(0, 0, 1), new Point(0, 0, 2), new Point(0, 0,
					3)));
	
	@Test
	public void testCrossoverReturnFirst() throws Exception {
		
		Mockito.when(random.nextInt(4)).thenReturn(0);
		Mockito.when(random.nextInt(3)).thenReturn(3);
		
		TourConfiguration parent1 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 1, 2, 2, 3, 3));
		TourConfiguration parent2 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 2, 2, 1, 3, 3));
		TourConfiguration child = crossover.crossover(parent1, parent2);
		assertThat(child, is(parent1));
	}
	
	@Test
	public void testCrossoverReturnSecond() throws Exception {
		
		Mockito.when(random.nextInt(4)).thenReturn(0);
		Mockito.when(random.nextInt(3)).thenReturn(0);
		
		TourConfiguration parent1 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 1, 2, 2, 3, 3));
		TourConfiguration parent2 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 2, 2, 1, 3, 3));
		TourConfiguration child = crossover.crossover(parent1, parent2);
		assertThat(child, is(parent2));
	}
	
	@Test
	public void testCrossover() throws Exception {
		
		Mockito.when(random.nextInt(4)).thenReturn(0);
		Mockito.when(random.nextInt(3)).thenReturn(2);

		TourConfiguration parent1 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 1, 2, 2, 3, 3));
		TourConfiguration parent2 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 2, 2, 1, 3, 3));
		TourConfiguration child = crossover.crossover(parent1, parent2);
		assertThat(child.getSize(), is(4));
		assertThat(child.get(0), is(0));
		assertThat(child.get(1), is(1));
		assertThat(child.get(2), is(2));
		assertThat(child.get(3), is(3));

		assertThat(child.getPoint(0), is(new Point(0, 0, 0)));
		assertThat(child.getPoint(1), is(new Point(0, 0, 1)));
		assertThat(child.getPoint(2), is(new Point(0, 0, 2)));
		assertThat(child.getPoint(3), is(new Point(0, 0, 3)));
	}
	
	@Test
	public void testCrossover2_2() throws Exception {
		
		Mockito.when(random.nextInt(4)).thenReturn(2);
		Mockito.when(random.nextInt(3)).thenReturn(2);
		
		TourConfiguration parent1 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 1, 2, 2, 3, 3));
		TourConfiguration parent2 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 2, 2, 1, 3, 3));
		TourConfiguration child = crossover.crossover(parent1, parent2);
		assertThat(child.getSize(), is(4));
		assertThat(child.get(0), is(0));
		assertThat(child.get(1), is(2));
		assertThat(child.get(2), is(3));
		assertThat(child.get(3), is(1));
		
		assertThat(child.getPoint(0), is(new Point(0, 0, 0)));
		assertThat(child.getPoint(1), is(new Point(0, 0, 2)));
		assertThat(child.getPoint(2), is(new Point(0, 0, 3)));
		assertThat(child.getPoint(3), is(new Point(0, 0, 1)));
	}
}
