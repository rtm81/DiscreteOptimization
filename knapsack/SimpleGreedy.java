import java.util.Collections;
import java.util.LinkedList;
import java.util.List;




public class SimpleGreedy implements SolverInterface {

	@Override
	public int solve(ProblemData problemData,
			int[] taken) {
		int value = 0;
        int weight = 0;
		
        double[] v_per_w = new double[problemData.items];
        List<ValuePerKilo> v_per_w_index = new LinkedList<ValuePerKilo>();
        for(int i=0; i < problemData.items; i++){
        	v_per_w[i] = ((double)problemData.values[i]) / ((double)problemData.weights[i]);
        	v_per_w_index.add(new ValuePerKilo(i, v_per_w[i]));
        }
        Collections.sort(v_per_w_index, Collections.reverseOrder());
        if (Solver.debug) {
        	System.err.println(v_per_w_index);
        }
        
        for (ValuePerKilo valuePerKilo : v_per_w_index) {
        	int i = valuePerKilo.index;
	      if(weight + problemData.weights[i] <= problemData.capacity){
	          taken[i] = 1;
	          value += problemData.values[i];
	          weight += problemData.weights[i];
	      } else {
	          taken[i] = 0;
	      }
		}
		return value;
	}
    
    private static class ValuePerKilo implements Comparable<ValuePerKilo>{
    	private final int index;
    	private final double value;
    	
    	public ValuePerKilo(int index, double value) {
			this.index = index;
			this.value = value;
		}

		@Override
		public int compareTo(ValuePerKilo o) {
			return Double.valueOf(value).compareTo(Double.valueOf(o.value));
		}
		
		@Override
		public String toString() {
			return "["+index+", "+value+"]";
		}
    }
}
