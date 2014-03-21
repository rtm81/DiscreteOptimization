import solver.constraints.IntConstraintFactory;
import solver.search.loop.monitors.SMF;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.IntVar;
import solver.variables.VariableFactory;


public class CSPSolver {
	
	Configuration solve(ProblemData problemData, int currentSize) {
		boolean findSolution = true;
		Configuration configurationCSP = null;;
		while (true) {
			currentSize--;
			solver.Solver solver = new solver.Solver("graph coloring");
			// 2. Create variables through the variable factory
			int nodeCount = problemData.getNodeCount();
			IntVar[] vars = new IntVar[nodeCount];
			for (int i = 0; i < nodeCount; i++) {
				vars[i] = VariableFactory.bounded("" + i, 0, currentSize, solver);
			}
	
			// 3. Create and post constraints by using constraint factories
			
			int[][] edges = problemData.getEdges();
			int edgeCount = problemData.getEdgeCount();
			for (int i = 0; i < edgeCount; i++) {
				int node1 = edges[i][0];
				int node2 = edges[i][1];
				solver.post(IntConstraintFactory.arithm(vars[node1], "!=", vars[node2])); 
			}
			
			// 4. Define the search strategy
//			solver.set(IntStrategyFactory.inputOrder_InDomainMin(vars));  // 6.9
//			solver.set(IntStrategyFactory.firstFail_InDomainMin(vars));  // 2.8
//			solver.set(IntStrategyFactory.firstFail_InDomainMiddle(vars));  // long
//			solver.set(IntStrategyFactory.firstFail_InDomainMax(vars));  // 2.8
//			solver.set(IntStrategyFactory.maxReg_InDomainMin(vars));  // 6.9
//			solver.set(IntStrategyFactory.random(vars, System.currentTimeMillis()));  // long
			solver.set(IntStrategyFactory.domOverWDeg_InDomainMin(vars, System.currentTimeMillis()));  // 0.4
			SMF.limitTime(solver, 10 * 60 * 1000); // 10 min
			// 5. Launch the resolution process
			findSolution = solver.findSolution();
			if (!findSolution) {
				break;
			}
			if (Solver.debug) {
				System.out.println("solution for " + currentSize);
			}
			configurationCSP = Configuration.create(problemData);
			for (IntVar var : vars) {
				configurationCSP.setColor(Integer.valueOf(var.getName()), var.getValue());
			}
		}
		return configurationCSP;
	}
}