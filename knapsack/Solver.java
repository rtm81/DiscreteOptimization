import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver {
	
	static boolean debug = false;
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public static void solve(String[] args) throws IOException {
        String fileName = null;
        
        // get the temp file name
        for(String arg : args){
            if(arg.startsWith("-file=")){
                fileName = arg.substring(6);
            } 
        }
        if(fileName == null) {
        	System.err.println("no file");
            return;
        }
        
        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int items = Integer.parseInt(firstLine[0]);
        int capacity = Integer.parseInt(firstLine[1]);

        int[] values = new int[items];
        int[] weights = new int[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          values[i-1] = Integer.parseInt(parts[0]);
          weights[i-1] = Integer.parseInt(parts[1]);
        }

        int[] taken = new int[items];
        int value = solve(items, capacity, values, weights, taken);
        
        // prepare the solution in the specified output format
        System.out.println(value+" 0");
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }

	private static int solve(int items, int capacity, int[] values,
			int[] weights, int[] taken) {
		int value = 0;
        int weight = 0;
		
        double[] v_per_w = new double[items];
        List<ValuePerKilo> v_per_w_index = new LinkedList<Solver.ValuePerKilo>();
        for(int i=0; i < items; i++){
        	v_per_w[i] = ((double)values[i]) / ((double)weights[i]);
        	v_per_w_index.add(new ValuePerKilo(i, v_per_w[i]));
        }
        Collections.sort(v_per_w_index, Collections.reverseOrder());
        if (debug) {
        	System.err.println(v_per_w_index);
        }
        
        for (ValuePerKilo valuePerKilo : v_per_w_index) {
        	int i = valuePerKilo.index;
	      if(weight + weights[i] <= capacity){
	          taken[i] = 1;
	          value += values[i];
	          weight += weights[i];
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