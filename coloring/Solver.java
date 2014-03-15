import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.TreeMultiset;


public class Solver {

	static boolean debug = false;
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

		if (debug) {
			System.out.println("problem:\t" + Arrays.deepToString(problemData.getEdges()));
		}
		ColorSelector fromLargestSetColorSelector;
		if (colorselectLargest) {
			fromLargestSetColorSelector = new FromLargestSetColorSelector();
		} else {
			fromLargestSetColorSelector = new FirstColorSelector();
		}
		
		Map<Integer, Node> nodes = new TreeMap<Integer, Node>();
		for (int[] edgeNodes : problemData.getEdges()) {
			int nodeNr1 = edgeNodes[0];
			int nodeNr2 = edgeNodes[1];
			Edge edge = new Edge(nodeNr1, nodeNr2);
			Node node1 = nodes.get(nodeNr1);
			if (node1 == null) {
				node1 = new Node(nodeNr1);
				nodes.put(nodeNr1, node1);
			}
			node1.edges.add(edge);
			Node node2 = nodes.get(nodeNr2);
			if (node2 == null) {
				node2 = new Node(nodeNr2);
				nodes.put(nodeNr2, node2);
			}
			node2.edges.add(edge);
		}
		Comparator<? super Node> comparator = new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return Integer.valueOf(o1.id).compareTo(Integer.valueOf(o1.id));
			}
		};
		
		TreeMap<Integer, Set<Node>> rankSortedNodes = new TreeMap<Integer, Set<Node>>();
		
		for (Node node : nodes.values()) {
			Integer rank = node.edges.size();
			Set<Node> set = rankSortedNodes.get(rank);
			if (set == null) {
				set = new LinkedHashSet<Node>();
				rankSortedNodes.put(rank, set);
			}
			set.add(node);
		}
		
		
		if (debug) {
			System.out.println("nodes:\t" + nodes);
			System.out.println("rank:\t" + rankSortedNodes);
		}
		
		Map<Integer, Set<Node>> allColors = new LinkedHashMap<Integer, Set<Node>>();
		for (Set<Node> rankNodes : rankSortedNodes.descendingMap().values()) {
			for (Node node : rankNodes) {
			
			if (node.color != null) continue;
			Set<Integer> neighborcolors = new HashSet<Integer>();
			for (Edge edge : node.edges) {
				neighborcolors.add(nodes.get(edge.node1).color);
				neighborcolors.add(nodes.get(edge.node2).color);
			}
			int possibleColor = 0;
			SetView<Integer> difference = Sets.difference(allColors.keySet(), neighborcolors);
			if (!difference.isEmpty()) {
				possibleColor = fromLargestSetColorSelector.getColor(allColors, difference);
			}
			
			while (neighborcolors.contains(possibleColor)) {
				possibleColor++;
			}
			node.color = possibleColor;
			Set<Node> set = allColors.get(possibleColor);
			if (set == null) {
				set = new LinkedHashSet<Node>();
				allColors.put(possibleColor, set);
			}
			set.add(node);
			}
		}
		Map<Integer, Integer> colorsize = new LinkedHashMap<Integer, Integer>();
		TreeMultimap<Integer,Integer> treeMultimap = TreeMultimap.create();
		for (Entry<Integer, Set<Node>> entry : allColors.entrySet()) {
			colorsize.put(entry.getKey(), entry.getValue().size());
			treeMultimap.put(entry.getValue().size(), entry.getKey());
		}

		if (debug) {
			System.out.println("nodes:\t" +nodes);
			System.out.println("allColors:\t" +allColors);
			System.out.println("colorsize:\t" +colorsize);
			System.out.println("colorsize rev:\t" +treeMultimap);
		}
		
		// prepare the solution in the specified output format
		System.out.println(allColors.size() + " 0");
		for (Entry<Integer, Node> entry : nodes.entrySet()) {
			System.out.print(entry.getValue().color + " ");
		}
		System.out.println("");
	}
	
	interface ColorSelector {
		public int getColor(Map<Integer, Set<Node>> allColors,
				SetView<Integer> difference);
	}
	
	static class FirstColorSelector implements ColorSelector {

		@Override
		public int getColor(Map<Integer, Set<Node>> allColors,
				SetView<Integer> difference) {
			return difference.iterator().next();
		}
		
	}
	static class FromLargestSetColorSelector implements ColorSelector {
		
		public int getColor(Map<Integer, Set<Node>> allColors,
				SetView<Integer> difference) {
			int possibleColor;
			int colorInlargestSet = -1;
			int largestSet = -1;
			for (Integer color : difference) {
				int size = allColors.get(color).size();
				if (size > largestSet) {
					largestSet = size;
					colorInlargestSet = color;
				}
			}
			
			possibleColor = colorInlargestSet;
			return possibleColor;
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

	static class Edge {
		int node1;
		int node2;
		public Edge(int i, int j) {
			this.node1 = i;
			this.node2 = j;
		}
		@Override
		public String toString() {
			return "Edge [" + node1 + ", " + node2 + "]";
		}
		
	}
	
	static class Node {
		private final int id;
		List<Edge> edges = new ArrayList<Edge>();
		Integer color = null;
		
		Node(final int id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return "Node "+id+" [" + edges + ", color=" + color + "]";
		}
		
	}
}
