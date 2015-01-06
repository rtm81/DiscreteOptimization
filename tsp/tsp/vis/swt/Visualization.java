package tsp.vis.swt;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import tsp.ProblemData;

public class Visualization {
	Display display;

	Shell shell;

	private ProblemData problemData;

	Visualization(ProblemData problemData) {
		this.problemData = problemData;
		display = new Display();
		shell = new Shell(display);
		shell.setSize(1000, 1000);

		shell.setText("TSP Configuration");

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
