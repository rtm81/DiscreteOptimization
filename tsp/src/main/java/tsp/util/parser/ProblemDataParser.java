package tsp.util.parser;

import java.util.ArrayList;
import java.util.List;

import tsp.util.Point;

public class ProblemDataParser {

	public List<Point> parse(String problemDataString) {
		String[] pointStrings = problemDataString.split(", Point ");
		List<Point> pointList = new ArrayList<>();
		for (String pointString : pointStrings) {
			String[] pointStringElement = pointString.replaceAll(
					"[A-Za-z\\[\\]= ]", "").split(",");
			Point point = new Point(Float.parseFloat(pointStringElement[0]),
					Float.parseFloat(pointStringElement[1]),
					Integer.parseInt(pointStringElement[2]));
			pointList.add(point);
		}
		return pointList;
	}
}
