package tsp.vis.swt;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import tsp.ConfigurationChangedListener;
import tsp.TSPSolver;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.vis.VisualizationData;
import algorithm.init.FileInputInitializationStrategy;
import algorithm.init.FileSelecter;
import algorithm.init.InitializationStrategy;
import algorithm.init.NearestNeighbor;
import algorithm.init.SimpleInitializationStrategy;
import algorithm.init.SortedDistance;
import algorithm.opti.OptimizeStrategy;
import algorithm.opti.TwoOpt;
import algorithm.opti.TwoOptAdvanced;
import algorithm.opti.genetic.GAStrategy;

public class Starter {

	private Display display;
	
	public static void main(String[] args) {
		Starter starter = new Starter();
		starter.display();
	}

	public void display() {
		display = new Display();
		
		final Shell shell = new Shell(display);
		shell.setSize(100, 100);
		shell.setText("TSP Starter");
		
		Menu menuBar = new Menu(shell, SWT.BAR);
	    MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setText("&File");
	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    fileMenuHeader.setMenu(fileMenu);
		MenuItem optionsMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		optionsMenuHeader.setText("&Options");
		Menu optionsMenu = new Menu(shell, SWT.DROP_DOWN);
		optionsMenuHeader.setMenu(optionsMenu);

		final MenuItem multipleItem = new MenuItem(fileMenu, SWT.CHECK);
		multipleItem.setText("&Multiple");

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
		        
				VisualizationStarter visualizationStarter = new VisualizationStarter();
				if (multipleItem.getSelection()) {
					visualizationStarter.startVisualization(selected);
					visualizationStarter.startVisualization(selected);
				} else {
					visualizationStarter.startVisualization(selected);
				}
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

	private class VisualizationStarter {
		final TSPData tspData = new TSPData();
		private Shell newShell;

		private final class SelectionAdapterExtension extends SelectionAdapter {
			@Override
			public void widgetSelected(SelectionEvent event) {
				MenuItem item = (MenuItem) event.widget;
				if (item.getSelection()) {
					Object data = item.getData();
					if (data instanceof FileSelecter) {
						((FileSelecter) data).selectFile(newShell);
					}
					tspData.initStrategy = (InitializationStrategy) data;
				}
			}
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
			final VisualizationData visualizationData = createVisualizationData(
					problemData, tspSolver);

			final Visualization visualization = createVisualization(visualizationData);

			newShell = new Shell(display);
			createMenu(selected, tspSolver, visualization, tspData, newShell);
			visualization.display(newShell);
		}

		protected void createMenu(final String selected,
				final TSPSolver tspSolver, final Visualization visualization,
				final TSPData tspData, final Shell newShell) {
			Menu menuBar = new Menu(newShell, SWT.BAR);

			// ACTIONS
			MenuItem actionsMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
			actionsMenuHeader.setText("&Actions");

			Menu actionsPullDownMenu = new Menu(newShell, SWT.DROP_DOWN);
			actionsMenuHeader.setMenu(actionsPullDownMenu);

			addMenuItem(actionsPullDownMenu, new GAStrategy(), tspData);
			addMenuItem(actionsPullDownMenu, new TwoOpt(), tspData);
			addMenuItem(actionsPullDownMenu, new TwoOptAdvanced(), tspData);

			// OPTIONS
			MenuItem optionsMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
			optionsMenuHeader.setText("&Options");
			Menu optionsPullDownMenu = new Menu(newShell, SWT.DROP_DOWN);
			optionsMenuHeader.setMenu(optionsPullDownMenu);

			final MenuItem menuItem = new MenuItem(optionsPullDownMenu,
					SWT.CHECK);
			menuItem.setText("&Solve All");
			menuItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					tspData.solveAll = menuItem.getSelection();
				}

			});

			// INIT
			MenuItem initMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
			initMenuHeader.setText("Init");
			Menu initPullDownMenu = new Menu(newShell, SWT.DROP_DOWN);
			initMenuHeader.setMenu(initPullDownMenu);

			MenuItem sortedDistanceMenu = new MenuItem(initPullDownMenu,
					SWT.RADIO);
			sortedDistanceMenu
					.addSelectionListener(new SelectionAdapterExtension());
			sortedDistanceMenu.setText("SortedDistance");
			sortedDistanceMenu.setData(new SortedDistance());

			MenuItem nearestNeighborMenu = new MenuItem(initPullDownMenu,
					SWT.RADIO);
			nearestNeighborMenu
					.addSelectionListener(new SelectionAdapterExtension());
			nearestNeighborMenu.setText("NearestNeighbor");
			nearestNeighborMenu.setData(new NearestNeighbor());

			MenuItem fileInputInitializationStrategyMenu = new MenuItem(
					initPullDownMenu, SWT.RADIO);
			fileInputInitializationStrategyMenu
					.addSelectionListener(new SelectionAdapterExtension());
			fileInputInitializationStrategyMenu
					.setText("FileInputInitializationStrategy");
			fileInputInitializationStrategyMenu
					.setData(new FileInputInitializationStrategy());

			MenuItem simpleInitializationStrategyMenu = new MenuItem(
					initPullDownMenu, SWT.RADIO);
			simpleInitializationStrategyMenu
					.addSelectionListener(new SelectionAdapterExtension());
			simpleInitializationStrategyMenu
					.setText("SimpleInitializationStrategy");
			simpleInitializationStrategyMenu
					.setData(new SimpleInitializationStrategy());

			// START
			MenuItem startMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
			startMenuHeader.setText("Start");
			Menu startPullDownMenu = new Menu(newShell, SWT.DROP_DOWN);
			startMenuHeader.setMenu(startPullDownMenu);

			MenuItem startItem = new MenuItem(startPullDownMenu, SWT.PUSH);
			startItem.setText("&Start");
			startItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {

					OptimizeStrategy opt = tspData.optimizeStrategy;
					if (opt == null) {
						MessageBox dialog = new MessageBox(newShell,
								SWT.ICON_QUESTION | SWT.OK);
						dialog.setText("Select an action");
						dialog.setMessage("Select an action from the actions menu.");

						dialog.open();

					} else {

						opt.setSolveAll(tspData.solveAll);

						if (tspData.initStrategy != null) {
							tspSolver
									.setInitializationStrategy(tspData.initStrategy);
						}

						tspSolver.setOptimizeStrategy(opt);
						visualization.startThread(tspSolver, "TSP "
								+ opt.getClass().getSimpleName() + " "
								+ selected);
					}
				}

			});

			newShell.setMenuBar(menuBar);
		}

	protected Visualization createVisualization(
			final VisualizationData visualizationData) {
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
		return visualization;
	}

	protected VisualizationData createVisualizationData(
			ProblemData problemData, final TSPSolver tspSolver) {
		final VisualizationData visualizationData = new VisualizationData(problemData);
		
		tspSolver.addListener(new ConfigurationChangedListener() {
			
			@Override
			public boolean changePerformed(TourConfiguration configuration) {
				final TourConfiguration configurationCopy = configuration.copy();
				visualizationData.setConfiguration(configurationCopy);
				return false;
			}
		});
		return visualizationData;
	}

	protected void addMenuItem(Menu parentMenu, final OptimizeStrategy opt,
			final TSPData tspData) {
		MenuItem menuItem = new MenuItem(parentMenu, SWT.PUSH);
		menuItem.setText(opt.getClass().getSimpleName());
		menuItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				tspData.optimizeStrategy = opt;
			}
		});
	}

	}
	private static class TSPData {
		OptimizeStrategy optimizeStrategy = null;
		InitializationStrategy initStrategy = null;
		boolean solveAll = false;
	}
}
