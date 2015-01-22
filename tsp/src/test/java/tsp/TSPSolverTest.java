package tsp;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import tsp.util.ProblemData;
import tsp.util.TourConfiguration;

public class TSPSolverTest {
	
	@Rule
	public TestName testname = new TestName();
	
	@BeforeClass
	public static void setup () throws Exception {
		
		// for precise test timing
		for (int i = 0; i < 10; i++) {
			ProblemData problemData = createProblemData("tsp_100_1");
			solve(problemData, Double.MAX_VALUE);
		}
	}

	@Test
	public void tsp1() throws Exception {
		ProblemData problemData = createProblemData("tsp_51_1");

		solve(problemData, (430.0d));
	}

	@Test
	public void tsp2() throws Exception {
		ProblemData problemData = createProblemData("tsp_100_3");
		solve(problemData, (20800.0d));
	}

	@Test
	public void tsp3() throws Exception {
		ProblemData problemData = createProblemData("tsp_200_2");
		solve(problemData, (30000.0d));
	}

	@Test
	public void tsp4() throws Exception {
		ProblemData problemData = createProblemData("tsp_574_1");
		solve(problemData, (37600.0d));
	}

	@Test
	public void tsp5() throws Exception {
		ProblemData problemData = createProblemData("tsp_1889_1");
		solve(problemData, (323000.0d));
	}

	@Test
	public void tsp6() throws Exception {
		ProblemData problemData = createProblemData("tsp_33810_1");
		solve(problemData, (78478868.0d));
	}

	@Test
	public void tsp_100_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, (22039.0d));
	}

	@Test(timeout=150)
	public void tsp_100_2() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, (22976.0d));
	}

	@Test(timeout=150)
	public void tsp_100_3() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, (22398.0d));
	}

	@Test(timeout=150)
	public void tsp_100_4() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, (22979.0d));
	}

	@Test(timeout=150)
	public void tsp_100_5() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, (23346.0d));
	}

	@Test(timeout=150)
	public void tsp_100_6() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 8437.0d);
	}

	@Test(timeout=200)
	public void tsp_101_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 679.0d);
	}

	@Test(timeout=150)
	public void tsp_105_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 16041.0d);
	}

	@Test(timeout=150)
	public void tsp_107_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 44958.0d);
	}

	@Test(timeout=300)
	public void tsp_124_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 59628.0d);
	}

	@Test(timeout=300)
	public void tsp_127_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 126710.0d);
	}

	@Test(timeout=300)
	public void tsp_136_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 106218.0d);
	}

	@Test(timeout=400)
	public void tsp_144_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 59208.0d);
	}

	@Test(timeout=500)
	public void tsp_150_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 28225.0d);
	}

	@Test(timeout=400)
	public void tsp_150_2() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 27483.0d);
	}

	@Test(timeout=9000)
	public void tsp_400_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 16715.0d);
	}

	@Test(timeout=120000)
	public void tsp_783_1() throws Exception {
		ProblemData problemData = createProblemData(testname.getMethodName());

		solve(problemData, 9675.0d);
	}
	
	public static void solve(ProblemData problemData, double expectedTourLength) {
		TSPSolver tspSolver = new TSPSolver(problemData);
		TourConfiguration configuration = tspSolver.solve();
		
		assertThat(configuration.calculateTourLength(), lessThan(expectedTourLength));
	}
	
	public static ProblemData createProblemData(String fileName) throws IOException {
		ProblemData problemData = ProblemData.getProblemDataFromFile("/home/roscoe/git/DiscreteOptimization/tsp/data/" + fileName);
		return problemData;
	}
}
