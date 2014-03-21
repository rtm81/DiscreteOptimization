
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