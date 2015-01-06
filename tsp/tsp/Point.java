package tsp;

public class Point {
	private final float x;
	private final float y;
	private final int id;
	
	public Point(float x, float y) {
		this(x, y, -1);
	}
	public Point(float x, float y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public double distance(Point point) {
		return distance(this, point);
	}
	
	public double simpleDistance(Point point) {
		return simpleDistance(this, point);
	}
	
	public static double distance(Point p1, Point p2) {
		return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	public static double simpleDistance(Point p1, Point p2) {
		return simpleDistance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	public static double distance(float x1, float y1, float x2, float y2) {
		return Math.sqrt(simpleDistance(x1, y1, x2, y2));
	}
	public static double simpleDistance(float x1, float y1, float x2, float y2) {
		double deltaX = x1 - x2;
		double deltaY = y1 - y2;
		return deltaX * deltaX + deltaY * deltaY;
	}
	public int getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + Float.floatToIntBits(getX());
		result = prime * result + Float.floatToIntBits(getY());
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
		Point other = (Point) obj;
		if (id != other.id)
			return false;
		if (Float.floatToIntBits(getX()) != Float.floatToIntBits(other.getX()))
			return false;
		if (Float.floatToIntBits(getY()) != Float.floatToIntBits(other.getY()))
			return false;
		return true;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	
}