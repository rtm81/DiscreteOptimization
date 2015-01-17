package algorithm.opti.genetic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.eclipse.swt.widgets.Display;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tsp.util.Point;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.vis.VisualizationData;
import tsp.vis.swt.Visualization;

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
		Mockito.when(random.nextInt(3)).thenReturn(1);

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
		Mockito.when(random.nextInt(3)).thenReturn(1);
		
		TourConfiguration parent1 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 1, 2, 2, 3, 3));
		TourConfiguration parent2 = new TourConfiguration(problemData4Points,
				ImmutableMap.of(0,0, 1, 2, 2, 1, 3, 3));
		TourConfiguration child = crossover.crossover(parent1, parent2);
		assertThat(child.getSize(), is(4));
		assertThat(child.get(0), is(2));
		assertThat(child.get(1), is(3));
		assertThat(child.get(2), is(1));
		assertThat(child.get(3), is(0));
		
		assertThat(child.getPoint(0), is(new Point(0, 0, 2)));
		assertThat(child.getPoint(1), is(new Point(0, 0, 3)));
		assertThat(child.getPoint(2), is(new Point(0, 0, 1)));
		assertThat(child.getPoint(3), is(new Point(0, 0, 0)));
	}
	
	@Test
	public void testName() throws Exception {
		String problemDataString = "[points=[Point [x=27.0, y=68.0, id=0], Point [x=30.0, y=48.0, id=1], Point [x=43.0, y=67.0, id=2], Point [x=58.0, y=48.0, id=3], Point [x=58.0, y=27.0, id=4], Point [x=37.0, y=69.0, id=5], Point [x=38.0, y=46.0, id=6], Point [x=46.0, y=10.0, id=7], Point [x=61.0, y=33.0, id=8], Point [x=62.0, y=63.0, id=9], Point [x=63.0, y=69.0, id=10], Point [x=32.0, y=22.0, id=11], Point [x=45.0, y=35.0, id=12], Point [x=59.0, y=15.0, id=13], Point [x=5.0, y=6.0, id=14], Point [x=10.0, y=17.0, id=15], Point [x=21.0, y=10.0, id=16], Point [x=5.0, y=64.0, id=17], Point [x=30.0, y=15.0, id=18], Point [x=39.0, y=10.0, id=19], Point [x=32.0, y=39.0, id=20], Point [x=25.0, y=32.0, id=21], Point [x=25.0, y=55.0, id=22], Point [x=48.0, y=28.0, id=23], Point [x=56.0, y=37.0, id=24], Point [x=30.0, y=40.0, id=25], Point [x=37.0, y=52.0, id=26], Point [x=49.0, y=49.0, id=27], Point [x=52.0, y=64.0, id=28], Point [x=20.0, y=26.0, id=29], Point [x=40.0, y=30.0, id=30], Point [x=21.0, y=47.0, id=31], Point [x=17.0, y=63.0, id=32], Point [x=31.0, y=62.0, id=33], Point [x=52.0, y=33.0, id=34], Point [x=51.0, y=21.0, id=35], Point [x=42.0, y=41.0, id=36], Point [x=31.0, y=32.0, id=37], Point [x=5.0, y=25.0, id=38], Point [x=12.0, y=42.0, id=39], Point [x=36.0, y=16.0, id=40], Point [x=52.0, y=41.0, id=41], Point [x=27.0, y=23.0, id=42], Point [x=17.0, y=33.0, id=43], Point [x=13.0, y=13.0, id=44], Point [x=57.0, y=58.0, id=45], Point [x=62.0, y=42.0, id=46], Point [x=42.0, y=57.0, id=47], Point [x=16.0, y=57.0, id=48], Point [x=8.0, y=52.0, id=49], Point [x=7.0, y=38.0, id=50]]]";
		List<Point> pointList = parse(problemDataString);
		ProblemData problemData = new ProblemData(pointList);
		String solutionString1 = "0=22, 1=1, 2=25, 3=20, 4=37, 5=21, 6=29, 7=42, 8=11, 9=40, 10=18, 11=16, 12=44, 13=15, 14=38, 15=50, 16=39, 17=31, 18=49, 19=48, 20=32, 21=0, 22=33, 23=5, 24=2, 25=28, 26=45, 27=9, 28=10, 29=3, 30=46, 31=24, 32=41, 33=34, 34=23, 35=35, 36=4, 37=8, 38=12, 39=36, 40=6, 41=26, 42=47, 43=27, 44=30, 45=19, 46=7, 47=13, 48=43, 49=14, 50=17";
		Map<Integer, Integer> solutionList1 = parseTourSolution(solutionString1);
		String solutionString2 = "0=35, 1=23, 2=34, 3=24, 4=41, 5=27, 6=3, 7=46, 8=8, 9=4, 10=13, 11=7, 12=19, 13=40, 14=18, 15=11, 16=42, 17=29, 18=43, 19=21, 20=37, 21=20, 22=25, 23=1, 24=26, 25=6, 26=36, 27=12, 28=30, 29=31, 30=22, 31=48, 32=32, 33=0, 34=33, 35=5, 36=2, 37=28, 38=45, 39=9, 40=10, 41=47, 42=39, 43=50, 44=38, 45=15, 46=44, 47=16, 48=14, 49=49, 50=17";
		Map<Integer, Integer> solutionList2 = parseTourSolution(solutionString2);
		
		TourConfiguration parent1 = new TourConfiguration(problemData, solutionList1);
		TourConfiguration parent2 = new TourConfiguration(problemData, solutionList2);
		
		Mockito.when(random.nextInt(51)).thenReturn(42);
		Mockito.when(random.nextInt(50)).thenReturn(31);
		
		TourConfiguration child = crossover.crossover(parent1, parent2);
		Display display = new Display();
        Visualization.visualize(parent1, "parent1");
        Visualization.visualize(parent2, "parent2");
        Visualization.visualize(child, "child");
		while (!display.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
        System.out.println("" + child);
	}

	public Map<Integer, Integer> parseTourSolution(String solutionString1) {
		Map<Integer, Integer> solutionList = new HashMap<Integer, Integer>();

		String[] split = solutionString1.split(", ");
		for (String string : split) {
			String[] split2 = string.split("=");
			solutionList.put(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]));
		}
		return solutionList;
	}

	public List<Point> parse(String problemDataString) {
		String[] pointStrings = problemDataString.split(", Point ");
		List<Point> pointList = new ArrayList<>();
		for (String pointString : pointStrings) {
			String[] pointStringElement = pointString.replaceAll("[A-Za-z\\[\\]= ]", "").split(",");
			Point point = new Point(Float.parseFloat(pointStringElement[0]), Float.parseFloat(pointStringElement[1]), Integer.parseInt(pointStringElement[2]));
			pointList.add(point);
		}
		return pointList;
	}
}
