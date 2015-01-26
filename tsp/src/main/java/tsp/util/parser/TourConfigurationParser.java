package tsp.util.parser;

import java.util.HashMap;
import java.util.Map;

public class TourConfigurationParser {

	private static final String SOLUTION_LIST_PREFIX = "[solutionList={";

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

	public Map<Integer, Integer> parseTourSolutionWithUnknown(
			String solutionString1) {

		int indexOfPrefix = solutionString1.indexOf(SOLUTION_LIST_PREFIX);
		if (indexOfPrefix != -1) {
			solutionString1 = solutionString1.substring(indexOfPrefix
					+ SOLUTION_LIST_PREFIX.length());
			int indexOfEnd = solutionString1.indexOf("},");
			if (indexOfEnd != -1) {
				solutionString1 = solutionString1.substring(0, indexOfEnd);
			}
		}

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
