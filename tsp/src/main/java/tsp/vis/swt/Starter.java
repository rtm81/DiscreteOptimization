package tsp.vis.swt;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import tsp.ConfigurationChangedListener;
import tsp.TSPSolver;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.vis.VisualizationData;
import algorithm.opti.OptimizeStrategy;
import algorithm.opti.TwoOpt;
import algorithm.opti.TwoOptAdvanced;
import algorithm.opti.genetic.GAStrategy;

public class Starter {

	private Display display;
	private Shell shell;
	
	public static void main(String[] args) {
		Starter starter = new Starter();
		starter.display();
	}

	public void display() {
		display = new Display();
		
		shell = new Shell(display);
		shell.setSize(100, 100);
		shell.setText("TSP Starter");
		
		Menu menuBar = new Menu(shell, SWT.BAR);
	    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("&File");
	    
	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);

	    MenuItem fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
	    fileSaveItem.setText("&Open");
	    fileSaveItem.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open");
		        fd.setFilterPath("./tsp/data");
		        String[] filterExt = { "tsp*", "*" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        
		        startVisualization(selected);
			}
		});
	    
	    shell.setMenuBar(menuBar);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void startVisualization(final String selected) {
		ProblemData problemData;
		try {
			problemData = ProblemData.getProblemDataFromFile(selected);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		final TSPSolver tspSolver = new TSPSolver(problemData);
		final VisualizationData visualizationData = new VisualizationData(problemData);
		
		tspSolver.addListener(new ConfigurationChangedListener() {
			
			@Override
			public boolean changePerformed(TourConfiguration configuration) {
				final TourConfiguration configurationCopy = configuration.copy();
				visualizationData.setConfiguration(configurationCopy);
				return false;
			}
		});
		
		final Visualization visualization = new Visualization(visualizationData);
		
		visualizationData.addListener(new ConfigurationChangedListener() {
			
			@Override
			public boolean changePerformed(TourConfiguration configuration) {
				if (visualization.isDisposed()) {
					return true;
				} else {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (visualization.isDisposed()) {
								return;
							}
							visualization.redraw();
						}
					});
					return false;
				}
			}
		});

		Shell newShell = new Shell(display);
		Menu menuBar = new Menu(newShell, SWT.BAR);
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&Actions");

		Menu fileMenu = new Menu(newShell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		addMenuItem(selected, tspSolver, fileMenu, new GAStrategy(), "GA",
				visualization);
		addMenuItem(selected, tspSolver, fileMenu, new TwoOpt(), "TwoOpt",
				visualization);
		addMenuItem(selected, tspSolver, fileMenu, new TwoOptAdvanced(),
				"TwoOptAdvanced", visualization);
		newShell.setMenuBar(menuBar);

		visualization.display(newShell);
	}

	protected void addMenuItem(final String selected,
			final TSPSolver tspSolver, Menu fileMenu,
			final OptimizeStrategy opt, final String title,
			final Visualization visualization) {
		MenuItem menuItem = new MenuItem(fileMenu, SWT.PUSH);
		menuItem.setText("&Start " + title);
		menuItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				tspSolver.setOptimizeStrategy(opt);
				visualization.startThread(tspSolver, "TSP " + title + " "
						+ selected);
			}
		});
	}
}
