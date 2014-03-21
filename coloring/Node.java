import java.util.LinkedHashSet;
import java.util.Set;


class Node {
	private final int id;
	Set<Edge> edges = new LinkedHashSet<Edge>();
	Integer color = null;
	
	Node(final int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Node "+id+" [" + edges + ", color=" + color + "]";
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	public int getRank() {
		return edges.size();
	}
}