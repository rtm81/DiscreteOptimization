package algorithm.opti.genetic;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import org.junit.Test;

import algorithm.opti.genetic.GA;


public class GATest {

	GA ga = new GA();
	
	@Test
	public void testIsInSubTour() throws Exception {
		assertThat(ga.isInSubTour(0, 1, 0), is(false));
		assertThat(ga.isInSubTour(0, 1, 1), is(false));
		assertThat(ga.isInSubTour(0, 1, 2), is(false));
		assertThat(ga.isInSubTour(0, 1, 3), is(false));
		
		assertThat(ga.isInSubTour(1, 0, 0), is(true));
		assertThat(ga.isInSubTour(1, 0, 1), is(true));
		assertThat(ga.isInSubTour(1, 0, 2), is(true));
		assertThat(ga.isInSubTour(1, 0, 3), is(true));
	}
	
	@Test
	public void testIsInSubTourLength() throws Exception {
		assertThat(ga.isInSubTour(0, 0, 3, 0), is(false));
		assertThat(ga.isInSubTour(0, 1, 3, 0), is(true));
		assertThat(ga.isInSubTour(0, 1, 3, 1), is(false));
		assertThat(ga.isInSubTour(0, 1, 3, 2), is(false));
		assertThat(ga.isInSubTour(1, 1, 3, 0), is(false));
		assertThat(ga.isInSubTour(0, 1, 3, 3), is(true));
		assertThat(ga.isInSubTour(2, 2, 3, 0), is(true));
		assertThat(ga.isInSubTour(2, 2, 3, 1), is(false));
		
		assertThat(ga.isInSubTour(2, 3, 3, 0), is(true));
		assertThat(ga.isInSubTour(2, 3, 3, 1), is(true));
		assertThat(ga.isInSubTour(2, 3, 3, 2), is(true));
	}
}
