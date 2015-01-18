package tsp.util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ProblemData implements Iterable<Point> {
	
	private final List<Point> points;
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
		return getPoints().size();
	}

	public Point get(int i) {
		return getPoints().get(i);
	}

	public Point getSmallest() {
		return smallest;
	}

	public Point getLargest() {
		return largest;
	}

	@Override
	public Iterator<Point> iterator() {
		return getPoints().iterator();
	}

	public List<Point> getPoints() {
		return points;
	}

	@Override
	public String toString() {
		return "ProblemData [points=" + points + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemData other = (ProblemData) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}
	
	
	
}
