package algorithm.opti.genetic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public void testSubTour() throws Exception {
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
		
//		Visualization.visualize(ImmutableMap.of(parent1, "parent1", parent2, "parent2", child, "child"));
		
		String expectedSolutionString2 = "0=47, 1=27, 2=30, 3=19, 4=7, 5=13, 6=43, 7=14, 8=17, 9=22, 10=1, 11=25, 12=20, 13=37, 14=21, 15=29, 16=42, 17=11, 18=40, 19=18, 20=16, 21=44, 22=15, 23=38, 24=50, 25=39, 26=31, 27=49, 28=48, 29=32, 30=0, 31=33, 32=26, 33=6, 34=36, 35=12, 36=41, 37=24, 38=34, 39=23, 40=35, 41=4, 42=8, 43=46, 44=3, 45=10, 46=9, 47=45, 48=28, 49=2, 50=5";
		TourConfiguration expectedChild = new TourConfiguration(problemData, parseTourSolution(expectedSolutionString2));

		assertThat(child, is(expectedChild));
	}

	@Test
	public void testSubTour2() throws Exception {
		String problemDataString = "TourConfiguration [solutionList={0=53, 1=14, 2=15, 3=75, 4=19, 5=71, 6=65, 7=37, 8=43, 9=67, 10=73, 11=87, 12=85, 13=48, 14=33, 15=59, 16=21, 17=70, 18=23, 19=8, 20=66, 21=64, 22=60, 23=81, 24=40, 25=47, 26=72, 27=94, 28=96, 29=12, 30=55, 31=98, 32=39, 33=28, 34=2, 35=68, 36=24, 37=46, 38=5, 39=29, 40=80, 41=38, 42=91, 43=57, 44=41, 45=50, 46=42, 47=77, 48=16, 49=7, 50=10, 51=74, 52=30, 53=18, 54=22, 55=99, 56=93, 57=51, 58=3, 59=89, 60=13, 61=17, 62=34, 63=88, 64=79, 65=45, 66=27, 67=1, 68=54, 69=86, 70=82, 71=25, 72=62, 73=44, 74=31, 75=97, 76=49, 77=20, 78=90, 79=63, 80=52, 81=95, 82=78, 83=83, 84=32, 85=4, 86=61, 87=76, 88=35, 89=84, 90=69, 91=6, 92=56, 93=58, 94=36, 95=0, 96=26, 97=9, 98=11, 99=92}, problemData=ProblemData [points=[Point [x=2482.0, y=1183.0, id=0], Point [x=3854.0, y=923.0, id=1], Point [x=376.0, y=825.0, id=2], Point [x=2519.0, y=135.0, id=3], Point [x=2945.0, y=1622.0, id=4], Point [x=953.0, y=268.0, id=5], Point [x=2628.0, y=1479.0, id=6], Point [x=2097.0, y=981.0, id=7], Point [x=890.0, y=1846.0, id=8], Point [x=2139.0, y=1806.0, id=9], Point [x=2421.0, y=1007.0, id=10], Point [x=2290.0, y=1810.0, id=11], Point [x=1115.0, y=1052.0, id=12], Point [x=2588.0, y=302.0, id=13], Point [x=327.0, y=265.0, id=14], Point [x=241.0, y=341.0, id=15], Point [x=1917.0, y=687.0, id=16], Point [x=2991.0, y=792.0, id=17], Point [x=2573.0, y=599.0, id=18], Point [x=19.0, y=674.0, id=19], Point [x=3911.0, y=1673.0, id=20], Point [x=872.0, y=1559.0, id=21], Point [x=2863.0, y=558.0, id=22], Point [x=929.0, y=1766.0, id=23], Point [x=839.0, y=620.0, id=24], Point [x=3893.0, y=102.0, id=25], Point [x=2178.0, y=1619.0, id=26], Point [x=3822.0, y=899.0, id=27], Point [x=378.0, y=1048.0, id=28], Point [x=1178.0, y=100.0, id=29], Point [x=2599.0, y=901.0, id=30], Point [x=3416.0, y=143.0, id=31], Point [x=2961.0, y=1605.0, id=32], Point [x=611.0, y=1384.0, id=33], Point [x=3113.0, y=885.0, id=34], Point [x=2597.0, y=1830.0, id=35], Point [x=2586.0, y=1286.0, id=36], Point [x=161.0, y=906.0, id=37], Point [x=1429.0, y=134.0, id=38], Point [x=742.0, y=1025.0, id=39], Point [x=1625.0, y=1651.0, id=40], Point [x=1187.0, y=706.0, id=41], Point [x=1787.0, y=1009.0, id=42], Point [x=22.0, y=987.0, id=43], Point [x=3640.0, y=43.0, id=44], Point [x=3756.0, y=882.0, id=45], Point [x=776.0, y=392.0, id=46], Point [x=1724.0, y=1642.0, id=47], Point [x=198.0, y=1810.0, id=48], Point [x=3950.0, y=1558.0, id=49], Point [x=1380.0, y=939.0, id=50], Point [x=2848.0, y=96.0, id=51], Point [x=3510.0, y=1671.0, id=52], Point [x=457.0, y=334.0, id=53], Point [x=3888.0, y=666.0, id=54], Point [x=984.0, y=965.0, id=55], Point [x=2721.0, y=1482.0, id=56], Point [x=1286.0, y=525.0, id=57], Point [x=2716.0, y=1432.0, id=58], Point [x=738.0, y=1325.0, id=59], Point [x=1251.0, y=1832.0, id=60], Point [x=2728.0, y=1698.0, id=61], Point [x=3815.0, y=169.0, id=62], Point [x=3683.0, y=1533.0, id=63], Point [x=1247.0, y=1945.0, id=64], Point [x=123.0, y=862.0, id=65], Point [x=1234.0, y=1946.0, id=66], Point [x=252.0, y=1240.0, id=67], Point [x=611.0, y=673.0, id=68], Point [x=2576.0, y=1676.0, id=69], Point [x=928.0, y=1700.0, id=70], Point [x=53.0, y=857.0, id=71], Point [x=1807.0, y=1711.0, id=72], Point [x=274.0, y=1420.0, id=73], Point [x=2574.0, y=946.0, id=74], Point [x=178.0, y=24.0, id=75], Point [x=2678.0, y=1825.0, id=76], Point [x=1795.0, y=962.0, id=77], Point [x=3384.0, y=1498.0, id=78], Point [x=3520.0, y=1079.0, id=79], Point [x=1256.0, y=61.0, id=80], Point [x=1424.0, y=1728.0, id=81], Point [x=3913.0, y=192.0, id=82], Point [x=3085.0, y=1528.0, id=83], Point [x=2573.0, y=1969.0, id=84], Point [x=463.0, y=1670.0, id=85], Point [x=3875.0, y=598.0, id=86], Point [x=298.0, y=1513.0, id=87], Point [x=3479.0, y=821.0, id=88], Point [x=2542.0, y=236.0, id=89], Point [x=3955.0, y=1743.0, id=90], Point [x=1323.0, y=280.0, id=91], Point [x=3447.0, y=1830.0, id=92], Point [x=2936.0, y=337.0, id=93], Point [x=1621.0, y=1830.0, id=94], Point [x=3373.0, y=1646.0, id=95], Point [x=1393.0, y=1368.0, id=96], Point [x=3874.0, y=1318.0, id=97], Point [x=938.0, y=955.0, id=98], Point [x=3022.0, y=474.0, id=99]]], tourLength=26480.88355706564]";
		String solutionString1 = "";
		Map<Integer, Integer> solutionList1 = parseTourSolution(solutionString1);
		String solutionString2 = "TourConfiguration [solutionList={0=99, 1=93, 2=22, 3=17, 4=34, 5=88, 6=79, 7=45, 8=27, 9=1, 10=54, 11=86, 12=82, 13=25, 14=62, 15=44, 16=31, 17=51, 18=3, 19=89, 20=13, 21=18, 22=30, 23=74, 24=10, 25=0, 26=36, 27=58, 28=56, 29=6, 30=69, 31=61, 32=76, 33=35, 34=84, 35=11, 36=9, 37=26, 38=72, 39=47, 40=40, 41=94, 42=81, 43=60, 44=64, 45=66, 46=23, 47=70, 48=8, 49=21, 50=59, 51=33, 52=85, 53=87, 54=73, 55=67, 56=28, 57=2, 58=37, 59=65, 60=71, 61=43, 62=19, 63=15, 64=14, 65=53, 66=46, 67=5, 68=29, 69=80, 70=38, 71=91, 72=57, 73=41, 74=50, 75=12, 76=55, 77=98, 78=39, 79=68, 80=24, 81=75, 82=48, 83=96, 84=42, 85=77, 86=16, 87=7, 88=4, 89=32, 90=83, 91=78, 92=95, 93=52, 94=92, 95=63, 96=20, 97=90, 98=49, 99=97}, problemData=ProblemData [points=[Point [x=2482.0, y=1183.0, id=0], Point [x=3854.0, y=923.0, id=1], Point [x=376.0, y=825.0, id=2], Point [x=2519.0, y=135.0, id=3], Point [x=2945.0, y=1622.0, id=4], Point [x=953.0, y=268.0, id=5], Point [x=2628.0, y=1479.0, id=6], Point [x=2097.0, y=981.0, id=7], Point [x=890.0, y=1846.0, id=8], Point [x=2139.0, y=1806.0, id=9], Point [x=2421.0, y=1007.0, id=10], Point [x=2290.0, y=1810.0, id=11], Point [x=1115.0, y=1052.0, id=12], Point [x=2588.0, y=302.0, id=13], Point [x=327.0, y=265.0, id=14], Point [x=241.0, y=341.0, id=15], Point [x=1917.0, y=687.0, id=16], Point [x=2991.0, y=792.0, id=17], Point [x=2573.0, y=599.0, id=18], Point [x=19.0, y=674.0, id=19], Point [x=3911.0, y=1673.0, id=20], Point [x=872.0, y=1559.0, id=21], Point [x=2863.0, y=558.0, id=22], Point [x=929.0, y=1766.0, id=23], Point [x=839.0, y=620.0, id=24], Point [x=3893.0, y=102.0, id=25], Point [x=2178.0, y=1619.0, id=26], Point [x=3822.0, y=899.0, id=27], Point [x=378.0, y=1048.0, id=28], Point [x=1178.0, y=100.0, id=29], Point [x=2599.0, y=901.0, id=30], Point [x=3416.0, y=143.0, id=31], Point [x=2961.0, y=1605.0, id=32], Point [x=611.0, y=1384.0, id=33], Point [x=3113.0, y=885.0, id=34], Point [x=2597.0, y=1830.0, id=35], Point [x=2586.0, y=1286.0, id=36], Point [x=161.0, y=906.0, id=37], Point [x=1429.0, y=134.0, id=38], Point [x=742.0, y=1025.0, id=39], Point [x=1625.0, y=1651.0, id=40], Point [x=1187.0, y=706.0, id=41], Point [x=1787.0, y=1009.0, id=42], Point [x=22.0, y=987.0, id=43], Point [x=3640.0, y=43.0, id=44], Point [x=3756.0, y=882.0, id=45], Point [x=776.0, y=392.0, id=46], Point [x=1724.0, y=1642.0, id=47], Point [x=198.0, y=1810.0, id=48], Point [x=3950.0, y=1558.0, id=49], Point [x=1380.0, y=939.0, id=50], Point [x=2848.0, y=96.0, id=51], Point [x=3510.0, y=1671.0, id=52], Point [x=457.0, y=334.0, id=53], Point [x=3888.0, y=666.0, id=54], Point [x=984.0, y=965.0, id=55], Point [x=2721.0, y=1482.0, id=56], Point [x=1286.0, y=525.0, id=57], Point [x=2716.0, y=1432.0, id=58], Point [x=738.0, y=1325.0, id=59], Point [x=1251.0, y=1832.0, id=60], Point [x=2728.0, y=1698.0, id=61], Point [x=3815.0, y=169.0, id=62], Point [x=3683.0, y=1533.0, id=63], Point [x=1247.0, y=1945.0, id=64], Point [x=123.0, y=862.0, id=65], Point [x=1234.0, y=1946.0, id=66], Point [x=252.0, y=1240.0, id=67], Point [x=611.0, y=673.0, id=68], Point [x=2576.0, y=1676.0, id=69], Point [x=928.0, y=1700.0, id=70], Point [x=53.0, y=857.0, id=71], Point [x=1807.0, y=1711.0, id=72], Point [x=274.0, y=1420.0, id=73], Point [x=2574.0, y=946.0, id=74], Point [x=178.0, y=24.0, id=75], Point [x=2678.0, y=1825.0, id=76], Point [x=1795.0, y=962.0, id=77], Point [x=3384.0, y=1498.0, id=78], Point [x=3520.0, y=1079.0, id=79], Point [x=1256.0, y=61.0, id=80], Point [x=1424.0, y=1728.0, id=81], Point [x=3913.0, y=192.0, id=82], Point [x=3085.0, y=1528.0, id=83], Point [x=2573.0, y=1969.0, id=84], Point [x=463.0, y=1670.0, id=85], Point [x=3875.0, y=598.0, id=86], Point [x=298.0, y=1513.0, id=87], Point [x=3479.0, y=821.0, id=88], Point [x=2542.0, y=236.0, id=89], Point [x=3955.0, y=1743.0, id=90], Point [x=1323.0, y=280.0, id=91], Point [x=3447.0, y=1830.0, id=92], Point [x=2936.0, y=337.0, id=93], Point [x=1621.0, y=1830.0, id=94], Point [x=3373.0, y=1646.0, id=95], Point [x=1393.0, y=1368.0, id=96], Point [x=3874.0, y=1318.0, id=97], Point [x=938.0, y=955.0, id=98], Point [x=3022.0, y=474.0, id=99]]], tourLength=25421.831056752584]";
		Map<Integer, Integer> solutionList2 = parseTourSolution(solutionString2);
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
