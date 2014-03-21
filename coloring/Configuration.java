import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;


class Configuration {
		private List<Node> nodes;
		private List<Edge> edges = new LinkedList<Edge>();
		Map<Integer, Set<Node>> allColors = new LinkedHashMap<Integer, Set<Node>>();
		
		Configuration(int nodeCount) {
			nodes = new ArrayList<Node>(nodeCount);
			for (int i = 0; i < nodeCount; i++) {
				nodes.add(null);
			}
		}
		
		public void setColor(Integer index, int color) {
			Node node = nodes.get(index);
			setColor(node, color);
		}

		static Configuration create(ProblemData problemData) {
			Configuration configuration = new Configuration(problemData.getNodeCount());
			// build nodes model
			for (int[] edgeNodes : problemData.getEdges()) {
				int nodeNr1 = edgeNodes[0];
				int nodeNr2 = edgeNodes[1];
				configuration.addEdge(nodeNr1, nodeNr2);
			}
			
			// sort rank
//			TreeMap<Integer, Set<Node>> rankSortedNodes = new TreeMap<Integer, Set<Node>>();
//			for (Node node : configuration.nodes) {
//				Integer rank = node.edges.size();
//				Set<Node> set = rankSortedNodes.get(rank);
//				if (set == null) {
//					set = new LinkedHashSet<Node>();
//					rankSortedNodes.put(rank, set);
//				}
//				set.add(node);
//			}
			
			return configuration;
		}

		private void addEdge(int nodeNr1, int nodeNr2) {
			Edge edge = new Edge(nodeNr1, nodeNr2);
			addEdgeToNode(nodeNr1, edge);
			addEdgeToNode(nodeNr2, edge);
			edges.add(edge);
		}


		private void addEdgeToNode(int nodeNr, Edge edge) {
			Node node = nodes.get(nodeNr);
			if (node == null) {
				node = new Node(nodeNr);
				nodes.set(nodeNr, node);
			}
			node.edges.add(edge);
		}
		
		public ImmutableList<Node> getInRankOrder() {
			Comparator<Node> comparator = new Comparator<Node>(){

				@Override
				public int compare(Node o1, Node o2) {
					return Integer.valueOf(o1.getRank()).compareTo(Integer.valueOf(o2.getRank()));
				}

				};
			return Ordering.from(comparator).reverse().immutableSortedCopy(nodes);
		}
		
		public ImmutableList<Node> getInIdOrder() {
			return ImmutableList.copyOf(nodes);
		}
		
		public Node getNode(int nr) {
			return nodes.get(nr);
		}
		
		public Set<Integer> getUsedColors() {
			return allColors.keySet();
		}
		
		public int getLargestClass() {
			return getLargestClass(allColors.keySet());
		}
		
		public int getLargestClass(Set<Integer> keys) {
			int colorInlargestSet = -1;
			int largestSet = -1;
			for (Integer color : keys) {
				Set<Node> set = allColors.get(color);
				if (set == null) {
					continue;
				}
				int size = set.size();
				if (size > largestSet) {
					largestSet = size;
					colorInlargestSet = color;
				}
			}
			
			return colorInlargestSet;
		}
		
		public void setColor(Node node, int color) {
			node.color = color;
			Set<Node> set = allColors.get(color);
			if (set == null) {
				set = new LinkedHashSet<Node>();
				allColors.put(color, set);
			}
			set.add(node);
		}
		
		public int getSolution() {
			return allColors.size();
		}
		public String getGraph() {
			StringBuilder stringBuilder = new StringBuilder("graph G {").append(System.lineSeparator());
			for (Edge edge : edges) {
				stringBuilder.append(edge.node1).append(" -- ").append(edge.node2).append(System.lineSeparator());
			}
			for (int i = 0; i < nodes.size(); i++) {
				stringBuilder.append(i).append(" [label = \"").append(nodes.get(i).color).append("\"]").append(System.lineSeparator());
			}
			stringBuilder.append("}");
			return stringBuilder.toString();
		}
		
		public Map<Integer, Integer> getColorSize() {
			Map<Integer, Integer> colorsize = new LinkedHashMap<Integer, Integer>();
			for (Entry<Integer, Set<Node>> entry : allColors.entrySet()) {
				Integer color = entry.getKey();
				int nrNodesWithColor = entry.getValue().size();
				colorsize.put(color, nrNodesWithColor);
			}
			return colorsize;
		}
		
		public TreeMultimap<Integer,Integer> getColorAppearance() {
			TreeMultimap<Integer,Integer> colorApperance = TreeMultimap.create();
			for (Entry<Integer, Set<Node>> entry : allColors.entrySet()) {
				Integer color = entry.getKey();
				int nrNodesWithColor = entry.getValue().size();
				colorApperance.put(nrNodesWithColor, color);
			}
			return colorApperance;
		}
	}