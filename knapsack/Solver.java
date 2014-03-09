import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        
        ProblemData problemData = getProblemDataFromFile(fileName);

        int[] taken = new int[problemData.items];
        
        SolverInterface greedy = new Dynamic();
        int value = greedy.solve(problemData, taken);
        
        // prepare the solution in the specified output format
        System.out.println(value+" 0");
        for(int i=0; i < taken.length; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");
    }

    public static ProblemData getProblemDataFromFile(String fileName)
			throws FileNotFoundException, IOException {
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
        ProblemData problemData = new ProblemData(items, capacity, values, weights);
		return problemData;
	}

}