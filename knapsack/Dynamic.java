import java.util.Arrays;


public class Dynamic implements SolverInterface {

	@Override
	public int solve(ProblemData problemData, int[] taken) {
		
		int [][] matrix = new int[problemData.capacity + 1][problemData.items + 1];
		
		for(int item = 0; item < problemData.items; item++) {
			
			int weight = problemData.weights[item];
			int value = problemData.values[item];
			
			for(int capacity = 0; capacity <= problemData.capacity; capacity++) {
				int left = matrix[capacity][item];
				int itemAdded;
				if ((capacity - weight) >= 0 && (itemAdded = matrix[capacity - weight][item] + value) > left) {
					matrix[capacity][item + 1] = itemAdded;
				} else {
					matrix[capacity][item + 1] = left;
				}
			}
		}
		if (Solver.debug) {
			System.out.println(Arrays.deepToString(matrix));
		}
		
		// trace back
		
		int currentIndex = problemData.capacity;
		for (int item = problemData.items; item > 0; item--) {
			int currentValue = matrix[currentIndex][item];
			if (currentValue == matrix[currentIndex][item - 1]) {
				taken[item - 1] = 0;
			} else {
				taken[item - 1] = 1;
				currentIndex -= problemData.weights[item - 1];
			}
		}
		
		return matrix[problemData.capacity][problemData.items];
	}

}
