package algorithm.genetic;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Test;


public class GATest {

	GA ga = new GA();
	
	@Test
	public void testIsInSubTour() throws Exception {
		assertThat(ga.isInSubTour(0, 1, 0), Matchers.is(false));
		assertThat(ga.isInSubTour(0, 1, 1), Matchers.is(false));
		assertThat(ga.isInSubTour(0, 1, 2), Matchers.is(false));
		assertThat(ga.isInSubTour(0, 1, 3), Matchers.is(false));
		
		assertThat(ga.isInSubTour(1, 0, 0), Matchers.is(true));
		assertThat(ga.isInSubTour(1, 0, 1), Matchers.is(true));
		assertThat(ga.isInSubTour(1, 0, 2), Matchers.is(true));
		assertThat(ga.isInSubTour(1, 0, 3), Matchers.is(true));
	}
	
	@Test
	public void testIsInSubTourLength() throws Exception {
		assertThat(ga.isInSubTour(0, 0, 3, 0), Matchers.is(false));
		assertThat(ga.isInSubTour(0, 1, 3, 0), Matchers.is(true));
		assertThat(ga.isInSubTour(0, 1, 3, 1), Matchers.is(false));
		assertThat(ga.isInSubTour(0, 1, 3, 2), Matchers.is(false));
		assertThat(ga.isInSubTour(1, 1, 3, 0), Matchers.is(false));
		assertThat(ga.isInSubTour(0, 1, 3, 3), Matchers.is(true));
		assertThat(ga.isInSubTour(2, 2, 3, 0), Matchers.is(true));
	}
}
