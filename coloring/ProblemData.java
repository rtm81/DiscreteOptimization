
public class ProblemData {

	private final int nodeCount;
	private final int edgeCount;
	private final int[][] edges;
	
	public ProblemData(int nodeCount, int edgeCount, int[][] edges) {
		this.nodeCount = nodeCount;
		this.edgeCount = edgeCount;
		this.edges = edges;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	public int getEdgeCount() {
		return edgeCount;
	}

	public int[][] getEdges() {
		return edges;
	}

}
