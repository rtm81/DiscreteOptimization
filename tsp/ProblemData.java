import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ProblemData {
	
	final List<Point> points;
	private final Point smallest;
	private final Point largest;
	
	
	public static ProblemData getProblemDataFromFile(String fileName) throws IOException {
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
		String firstLine = lines.get(0);
		int nodeCount = Integer.parseInt(firstLine);
		
		
		List<Point> points = new ArrayList<>(nodeCount);
		for (int i = 1; i < nodeCount + 1; i++) {
			String line = lines.get(i);
			String[] node = line.split("\\s+");
			points.add(new Point(Float.valueOf(node[0]), Float.valueOf(node[1]), i - 1));
		}
		return new ProblemData(points);
	}

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
