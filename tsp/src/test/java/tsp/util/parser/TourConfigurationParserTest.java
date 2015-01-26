package tsp.util.parser;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

public class TourConfigurationParserTest {

	@Test
	public void testName() throws Exception {
		String input = "23:20:02.549 [TSP GA /home/roscoe/git/DiscreteOptimization/tsp/data/tsp_51_1] INFO  tsp.TSPSolver - done TourConfiguration [solutionList={0=0, 1=32, 2=48, 3=17, 4=49, 5=31, 6=22, 7=1, 8=25, 9=20, 10=37, 11=21, 12=43, 13=39, 14=50, 15=38, 16=15, 17=14, 18=44, 19=16, 20=29, 21=42, 22=11, 23=18, 24=40, 25=19, 26=7, 27=13, 28=35, 29=4, 30=8, 31=46, 32=3, 33=27, 34=41, 35=24, 36=34, 37=23, 38=30, 39=12, 40=36, 41=6, 42=26, 43=47, 44=45, 45=9, 46=10, 47=28, 48=2, 49=5, 50=33}, problemData=ProblemData [points=[Point [x=27.0, y=68.0, id=0], Point [x=30.0, y=48.0, id=1], Point [x=43.0, y=67.0, id=2], Point [x=58.0, y=48.0, id=3], Point [x=58.0, y=27.0, id=4], Point [x=37.0, y=69.0, id=5], Point [x=38.0, y=46.0, id=6], Point [x=46.0, y=10.0, id=7], Point [x=61.0, y=33.0, id=8], Point [x=62.0, y=63.0, id=9], Point [x=63.0, y=69.0, id=10], Point [x=32.0, y=22.0, id=11], Point [x=45.0, y=35.0, id=12], Point [x=59.0, y=15.0, id=13], Point [x=5.0, y=6.0, id=14], Point [x=10.0, y=17.0, id=15], Point [x=21.0, y=10.0, id=16], Point [x=5.0, y=64.0, id=17], Point [x=30.0, y=15.0, id=18], Point [x=39.0, y=10.0, id=19], Point [x=32.0, y=39.0, id=20], Point [x=25.0, y=32.0, id=21], Point [x=25.0, y=55.0, id=22], Point [x=48.0, y=28.0, id=23], Point [x=56.0, y=37.0, id=24], Point [x=30.0, y=40.0, id=25], Point [x=37.0, y=52.0, id=26], Point [x=49.0, y=49.0, id=27], Point [x=52.0, y=64.0, id=28], Point [x=20.0, y=26.0, id=29], Point [x=40.0, y=30.0, id=30], Point [x=21.0, y=47.0, id=31], Point [x=17.0, y=63.0, id=32], Point [x=31.0, y=62.0, id=33], Point [x=52.0, y=33.0, id=34], Point [x=51.0, y=21.0, id=35], Point [x=42.0, y=41.0, id=36], Point [x=31.0, y=32.0, id=37], Point [x=5.0, y=25.0, id=38], Point [x=12.0, y=42.0, id=39], Point [x=36.0, y=16.0, id=40], Point [x=52.0, y=41.0, id=41], Point [x=27.0, y=23.0, id=42], Point [x=17.0, y=33.0, id=43], Point [x=13.0, y=13.0, id=44], Point [x=57.0, y=58.0, id=45], Point [x=62.0, y=42.0, id=46], Point [x=42.0, y=57.0, id=47], Point [x=16.0, y=57.0, id=48], Point [x=8.0, y=52.0, id=49], Point [x=7.0, y=38.0, id=50]]], tourLength=437.40866064475534]";

		TourConfigurationParser tourConfigurationParser = new TourConfigurationParser();
		Map<Integer, Integer> parseTourSolution = tourConfigurationParser
				.parseTourSolutionWithUnknown(input);
		assertThat(parseTourSolution.entrySet(), hasSize(51));
	}
}
