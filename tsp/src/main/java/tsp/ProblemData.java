package tsp;
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

		
		try (BufferedReader input = new BufferedReader(new FileReader(fileName))) {
			String line = null;
			while ((line = input.readLine()) != null) {
				lines.add(line);
			}
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
		float smallestX = Float.MAX_VALUE;
		float smallestY = Float.MAX_VALUE;
		float largestX = Float.MIN_VALUE;
		float largestY = Float.MIN_VALUE;
		for (Point point : points) {
			if (point.getX() > largestX) {
				largestX = point.getX();
			}
			if (point.getX() < smallestX) {
				smallestX = point.getX();
			}
			if (point.getY() > largestY) {
				largestY = point.getY();
			}
			if (point.getY() < smallestY) {
				smallestY = point.getY();
			}
		}
		this.smallest = new Point(smallestX, smallestY);
		this.largest = new Point(largestX, largestY);
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
