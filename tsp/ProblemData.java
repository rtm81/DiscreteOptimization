import java.util.Collections;
import java.util.List;


public class ProblemData {
	
	final List<Point> points;
	private final Point smallest;
	private final Point largest;

	public ProblemData(List<Point> points) {
		this.points = Collections.unmodifiableList(points);
		Point smallest = new Point(Float.MAX_VALUE, Float.MAX_VALUE);
		Point largest = new Point(Float.MIN_VALUE, Float.MIN_VALUE);
		for (Point point : points) {
			if (point.x > largest.x) {
				largest.x = point.x;
			}
			if (point.x < smallest.x) {
				smallest.x = point.x;
			}
			if (point.y > largest.y) {
				largest.y = point.y;
			}
			if (point.y < smallest.y) {
				smallest.y = point.y;
			}
		}
		this.smallest = smallest;
		this.largest = largest;
	}
	
	public int getProblemSize(){
		return points.size();
	}

	public Point get(int i) {
		return points.get(i);
	}

	public Point getSmallest() {
		return smallest;
	}

	public Point getLargest() {
		return largest;
	}
}
