package tsp.util.parser;

import java.util.HashMap;
import java.util.Map;

public class TourConfigurationParser {

	public Map<Integer, Integer> parseTourSolution(String solutionString1) {
		Map<Integer, Integer> solutionList = new HashMap<Integer, Integer>();

		String[] split = solutionString1.split(", ");
		for (String string : split) {
			String[] split2 = string.split("=");
			solutionList.put(Integer.parseInt(split2[0]),
					Integer.parseInt(split2[1]));
		}
		return solutionList;
	}
}
