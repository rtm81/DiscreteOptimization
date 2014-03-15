import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;


public class Solver {

	static boolean debug = false;
	
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

		class Edge {
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
		
		class Node {
			List<Edge> edges = new ArrayList<Edge>();
			Integer color = null;
			@Override
			public String toString() {
				return "Node [" + edges + ", color=" + color + "]";
			}
			
		}
		if (debug) {
			System.out.println(Arrays.deepToString(problemData.getEdges()));
		}
		
		Map<Integer, Node> nodes = new TreeMap<Integer, Node>();
		for (int[] edgeNodes : problemData.getEdges()) {
			int nodeNr1 = edgeNodes[0];
			int nodeNr2 = edgeNodes[1];
			Edge edge = new Edge(nodeNr1, nodeNr2);
			Node node1 = nodes.get(nodeNr1);
			if (node1 == null) {
				node1 = new Node();
				nodes.put(nodeNr1, node1);
			}
			node1.edges.add(edge);
			Node node2 = nodes.get(nodeNr2);
			if (node2 == null) {
				node2 = new Node();
				nodes.put(nodeNr2, node2);
			}
			node2.edges.add(edge);
		}
		
		if (debug) {
			System.out.println(nodes);
			System.out.println(nodes.size());
		}
		
		Set<Integer> allColors = new HashSet<Integer>();
		for (Entry<Integer, Node> entry : nodes.entrySet()) {
			Integer nodeNr = entry.getKey();
			Node node = entry.getValue();
			if (node.color != null) continue;
			Set<Integer> neighborcolors = new HashSet<Integer>();
			for (Edge edge : node.edges) {
				neighborcolors.add(nodes.get(edge.node1).color);
				neighborcolors.add(nodes.get(edge.node2).color);
			}
			int possibleColor = 0;
			SetView<Integer> difference = Sets.difference(allColors, neighborcolors);
			if (!difference.isEmpty()) {
				possibleColor = difference.iterator().next();
			}
			
			while (neighborcolors.contains(possibleColor)) {
				possibleColor++;
			}
			node.color = possibleColor;
			allColors.add(possibleColor);
		}

		if (debug) {
			System.out.println(nodes);
			System.out.println(nodes.size());
		}
		
		// prepare the solution in the specified output format
		System.out.println(allColors.size() + " 0");
		for (Entry<Integer, Node> entry : nodes.entrySet()) {
			System.out.print(entry.getValue().color + " ");
		}
		System.out.println("");
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
