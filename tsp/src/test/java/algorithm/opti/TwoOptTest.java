package algorithm.opti;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;

import tsp.util.Point;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.parser.ProblemDataParser;
import tsp.util.parser.TourConfigurationParser;

public class TwoOptTest {

	TwoOpt twoOpt = new TwoOpt();

	private TourConfigurationParser tourConfigurationParser = new TourConfigurationParser();

	private ProblemDataParser problemDataParser = new ProblemDataParser();

	@Test
	public void testOptSwap() throws Exception {

		String problemDataString = "points=[Point [x=2482.0, y=1183.0, id=0], Point [x=3854.0, y=923.0, id=1], Point [x=376.0, y=825.0, id=2], Point [x=2519.0, y=135.0, id=3], Point [x=2945.0, y=1622.0, id=4], Point [x=953.0, y=268.0, id=5], Point [x=2628.0, y=1479.0, id=6], Point [x=2097.0, y=981.0, id=7], Point [x=890.0, y=1846.0, id=8], Point [x=2139.0, y=1806.0, id=9]";
		List<Point> pointList = problemDataParser.parse(problemDataString);
		ProblemData problemData = new ProblemData(pointList);
		TourConfiguration configuration = new TourConfiguration(
				problemData,
				tourConfigurationParser
						.parseTourSolution("0=71, 1=65, 2=37, 3=43, 4=19, 5=2, 6=28, 7=67, 8=73, 9=87"));

		TourConfiguration result = twoOpt.optSwap(configuration, 2, 5);

		TourConfiguration expectedTour = new TourConfiguration(
				problemData,
				tourConfigurationParser
						.parseTourSolution("0=71, 1=65, 2=2, 3=19, 4=43, 5=37, 6=28, 7=67, 8=73, 9=87"));

		assertThat(result, Matchers.is(expectedTour));
	}
}
