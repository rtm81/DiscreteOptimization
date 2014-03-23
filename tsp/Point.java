
class Point {
	float x;
	float y;
	
	Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Point point) {
		return distance(this, point);
	}
	
	public static double distance(Point p1, Point p2) {
		return distance(p1.x, p1.y, p2.x, p2.y);
	}
	public static double distance(float x1, float y1, float x2, float y2) {
		double deltaX = x1 - x2;
		double deltaY = y1 - y2;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}
}