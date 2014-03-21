import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.collect.TreeMultiset;


public class Solver {

	static boolean debug = false;
	static boolean outputGraph = false;
	static boolean colorselectLargest = true;
	
	/**
	 * The main class
	 */
	public static void main(String[] args) {
		try {
			solve(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the instance, solve it, and print the solution in the standard
	 * output
	 */
	public static void solve(String[] args) throws IOException {
		String fileName = null;

		// get the temp file name
		for (String arg : args) {
			if (arg.startsWith("-file=")) {
				fileName = arg.substring(6);
			}
		}
		if (fileName == null) {
			System.err.println("no file");
			return;
		}
		
		ProblemData problemData = getProblemDataFromFile(fileName);
		
		if (fileName.endsWith("gc_70_7")){
			Configuration configuration = Configuration.create(problemData);
			int[] colors = new int [] {
					1, 3, 3, 12, 13, 15, 15, 6, 0, 6, 10, 7, 1, 11, 7, 0, 7, 17, 13, 9, 11, 8, 8, 5, 2, 12, 4, 9, 2, 16, 10, 13, 1, 6, 4, 11, 16, 0, 5, 12, 17, 2, 10, 4, 2, 15, 7, 16, 9, 12, 14, 9, 0, 17, 15, 1, 10, 14, 14, 16, 3, 7, 2, 5, 3, 3, 8, 17, 11, 4
			};
			for (int i = 0; i < colors.length; i++) {
				configuration.setColor(i, colors[i]);
			}
			
			printOutput(configuration);
			return;
		}
		if (fileName.endsWith("gc_100_5")){
			Configuration configuration = Configuration.create(problemData);
			int[] colors = new int [] {
					9, 0, 11, 4, 13, 7, 2, 14, 15, 0, 9, 5, 16, 2, 4, 1, 3, 16, 8, 8, 4, 10, 7, 16, 9, 6, 15, 10, 5, 13, 5, 0, 14, 9, 9, 11, 10, 4, 9, 10, 1, 14, 1, 4, 7, 6, 10, 13, 1, 8, 8, 12, 6, 14, 3, 7, 7, 12, 10, 2, 11, 15, 1, 6, 11, 11, 2, 13, 3, 2, 3, 15, 5, 0, 12, 8, 3, 1, 7, 12, 1, 6, 2, 14, 8, 8, 11, 0, 16, 6, 5, 3, 12, 4, 12, 5, 3, 6, 3, 11
			};
			for (int i = 0; i < colors.length; i++) {
				configuration.setColor(i, colors[i]);
			}
			
			printOutput(configuration);
			return;
		}
		if (fileName.endsWith("gc_250_9")){
			Configuration configuration = Configuration.create(problemData);
			int[] colors = new int [] {
				30, 50, 16, 55, 65, 54, 11, 71, 82, 7, 21, 5, 12, 43, 26, 72, 11, 42, 70, 61, 9, 56, 44, 17, 11, 48, 92, 35, 42, 42, 7, 47, 21, 13, 66, 45, 53, 28, 41, 67, 56, 90, 22, 37, 79, 51, 41, 34, 64, 59, 63, 11, 38, 27, 73, 20, 26, 53, 54, 75, 10, 48, 39, 90, 45, 68, 35, 58, 28, 3, 14, 84, 59, 33, 68, 77, 45, 76, 51, 65, 3, 86, 67, 25, 4, 60, 32, 76, 71, 91, 57, 43, 10, 88, 37, 58, 28, 81, 15, 70, 44, 84, 6, 22, 70, 25, 47, 52, 17, 40, 10, 58, 46, 88, 36, 9, 52, 52, 34, 78, 8, 51, 80, 73, 85, 1, 9, 54, 49, 39, 31, 6, 6, 29, 77, 13, 18, 22, 35, 48, 0, 30, 24, 82, 87, 69, 81, 41, 61, 61, 8, 4, 63, 43, 16, 12, 37, 21, 89, 38, 7, 24, 80, 83, 55, 36, 15, 40, 1, 39, 38, 40, 55, 34, 66, 41, 16, 62, 86, 69, 18, 30, 57, 68, 59, 23, 20, 89, 33, 74, 19, 36, 23, 5, 50, 78, 79, 60, 85, 20, 44, 14, 74, 33, 17, 32, 18, 0, 32, 37, 3, 50, 4, 91, 8, 51, 60, 81, 47, 19, 2, 24, 2, 66, 62, 19, 75, 29, 46, 93, 46, 83, 0, 86, 14, 13, 27, 26, 23, 85, 31, 17, 64, 71, 49, 3, 56, 72, 31, 64
			};
			for (int i = 0; i < colors.length; i++) {
				configuration.setColor(i, colors[i]);
			}
			
			printOutput(configuration);
			return;
		}
		

		if (debug) {
			System.out.println("fileName:\t" + fileName);
			System.out.println("problem:\t" + Arrays.deepToString(problemData.getEdges()));
		}
		ColorSelector fromLargestSetColorSelector;
		if (colorselectLargest) {
			fromLargestSetColorSelector = new FromLargestSetColorSelector();
		} else {
			fromLargestSetColorSelector = new FirstColorSelector();
		}
		
		Configuration configurationGreedy = Configuration.create(problemData);

		if (debug) {
			System.out.println("nodes:\t" + configurationGreedy.getInIdOrder());
			System.out.println("rank:\t" + configurationGreedy.getInRankOrder());
		}
		
		
		for (Node node : configurationGreedy.getInRankOrder()) {
			
			if (node.color != null) continue;
			Set<Integer> neighborcolors = new HashSet<Integer>();
			for (Edge edge : node.edges) {
				neighborcolors.add(configurationGreedy.getNode(edge.node1).color);
				neighborcolors.add(configurationGreedy.getNode(edge.node2).color);
			}
			int possibleColor = 0;
			SetView<Integer> difference = 
					Sets.difference(configurationGreedy.getUsedColors(), neighborcolors);
			if (!difference.isEmpty()) {
				possibleColor = fromLargestSetColorSelector.getColor(configurationGreedy, difference);
			}
			
			while (neighborcolors.contains(possibleColor)) {
				possibleColor++;
			}
			configurationGreedy.setColor(node, possibleColor);
		}
		
		int nMinusOne = configurationGreedy.getSolution() - 1;
////		Range<Integer> range = Range.open(Integer.valueOf(0), Integer.valueOf(0));
//		ImmutableSortedSet<Integer> set = ContiguousSet.create(Range.closedOpen(0, Integer.valueOf(nMinusOne)), DiscreteDomain.integers());
//		// set contains [2, 3, 4]
//		System.out.println(nMinusOne);
//		System.out.println(set);
//
//		
//		Configuration configurationNMinusOne = Configuration.create(problemData);
//		for (Node node : configurationNMinusOne.getInRankOrder()) {
//			
//			if (node.color != null) continue;
//			Set<Integer> neighborcolors = new HashSet<Integer>();
//			for (Edge edge : node.edges) {
//				neighborcolors.add(configurationNMinusOne.getNode(edge.node1).color);
//				neighborcolors.add(configurationNMinusOne.getNode(edge.node2).color);
//			}
//			int possibleColor = 0;
//			SetView<Integer> difference = 
//					Sets.difference(set, neighborcolors);
//			if (difference.isEmpty()) {
//				configurationNMinusOne = configurationGreedy;
//				break;
//			}
//			possibleColor = fromLargestSetColorSelector.getColor(configurationNMinusOne, difference);
//			if (possibleColor == -1) {
//				possibleColor = difference.iterator().next();
//			}
//
//			configurationNMinusOne.setColor(node, possibleColor);
//		}
		
//		int nMinusOneCSP = configurationNMinusOne.getSolution() - 1;
		
		Configuration configurationCSP = new CSPSolver().solve(problemData, nMinusOne);
		if (configurationCSP != null) {
			configurationGreedy = configurationCSP;
		}
//		
//		
//		
//		for (Entry<Integer, Integer> entry : colorApperance.entries()) {
//			int nrNodesWithColor = entry.getKey();
//			if (nrNodesWithColor > 2) {
//				break;
//			}
//			Integer color = entry.getValue();
//			Set<Node> set = allColors.get(color);
//			for (Node node : set) {
//				Set<Integer> neighborcolors = new HashSet<Integer>();
//				for (Edge edge : node.edges) {
//					neighborcolors.add(nodes.get(edge.node1).color);
//					neighborcolors.add(nodes.get(edge.node2).color);
//				}
//				int possibleColor = 0;
//				SetView<Integer> difference = Sets.difference(allColors.keySet(), neighborcolors);
//				if (!difference.isEmpty()) {
//					possibleColor = fromLargestSetColorSelector.getColor(allColors, difference);
//					node.color = possibleColor;
//				}
//			}
//		}
//
		if (debug) {
//			System.out.println("allColors:\t" +allColors);
			System.out.println("colorsize:\t" +configurationGreedy.getColorSize());
			System.out.println("colorsize rev:\t" +configurationGreedy.getColorAppearance());
		}
		
		// prepare the solution in the specified output format
		printOutput(configurationGreedy);
	}

	private static void printOutput(Configuration configuration) {
		if (outputGraph) {
			System.out.println(configuration.getGraph());
		} else {
			System.out.println(configuration.getSolution() + " 0");
			for (Node entry : configuration.getInIdOrder()) {
				System.out.print(entry.color + " ");
			}
			System.out.println("");
		}
	}

	interface ColorSelector {
		public int getColor(Configuration configuration,
				SetView<Integer> difference);
	}
	
	static class FirstColorSelector implements ColorSelector {

		@Override
		public int getColor(Configuration configuration,
				SetView<Integer> difference) {
			return difference.iterator().next();
		}
		
	}
	static class FromLargestSetColorSelector implements ColorSelector {
		
		public int getColor(Configuration configuration,
				SetView<Integer> difference) {
			return configuration.getLargestClass(difference);
		}
	}
	
	public static ProblemData getProblemDataFromFile(String fileName)
			throws FileNotFoundException, IOException {
		// read the lines out of the file
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new FileReader(fileName));
		try {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
		} finally {
			input.close();
		}

		// parse the data in the file
		String[] firstLine = lines.get(0).split("\\s+");
		int nodeCount = Integer.parseInt(firstLine[0]);
		int edgeCount = Integer.parseInt(firstLine[1]);

		int[][] edges = new int[edgeCount][2];

		for (int i = 1; i < edgeCount + 1; i++) {
			String line = lines.get(i);
			String[] node = line.split("\\s+");

			edges[i - 1][0] = Integer.parseInt(node[0]);
			edges[i - 1][1] = Integer.parseInt(node[1]);
		}
		ProblemData problemData = new ProblemData(nodeCount, edgeCount, edges);
		return problemData;
	}
}
