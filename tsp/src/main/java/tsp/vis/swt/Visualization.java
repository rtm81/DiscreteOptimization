package tsp.vis.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import tsp.util.TourConfiguration;
import tsp.vis.ScreenPoint;
import tsp.vis.VisualizationData;
import tsp.vis.VisualizationData.PointsCallBack;
import tsp.vis.VisualizationData.TourCallBack;

public class Visualization {

	private static final String TITLE = "TSP Configuration";

	private Canvas canvas;

	private final VisualizationData visualizationData;
	private volatile boolean isDisposed = false;

	private Shell shell;

	private String title;

	public Visualization(VisualizationData visualizationData) {
		this(visualizationData, TITLE);
	}
	public Visualization(VisualizationData visualizationData, String title) {
		this.visualizationData = visualizationData;
		this.title = title;
	}

	public void display() {
		display(new Display());
	}

	public void display(Display display) {
		display(new Shell(display));
	}

	public void display(final Shell shell) {
		this.shell = shell;
		shell.setSize(800, 800);
		shell.setText(title);
		shell.setLayout(new FillLayout());

		canvas = new Canvas(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListenerImplementation());

		Menu menuBar = shell.getMenu();
		if (menuBar == null) {
			menuBar = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menuBar);
		}
		MenuItem windowMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		windowMenuHeader.setText("&Window");
		
		Menu windowMenu = new Menu(shell, SWT.DROP_DOWN);
		windowMenuHeader.setMenu(windowMenu);
		
		MenuItem smallItem = new MenuItem(windowMenu, SWT.PUSH);
		smallItem.setText("Small");
		smallItem.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setSize(300, 300);
			}
		});
		MenuItem alignItem = new MenuItem(windowMenu, SWT.PUSH);
		alignItem.setText("Align");
		alignItem.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell[] shells = Display.getCurrent().getShells();
				for (int i = 0; i < shells.length; i++) {
					shells[i].setSize(300, 300);
					
					Monitor primary = Display.getCurrent().getPrimaryMonitor();
				    Rectangle bounds = primary.getBounds();
				    Rectangle rect = shell.getBounds();
				    
				    int x = bounds.x + (300 * i);
				    int y = bounds.y;
				    
				    shell.setLocation(x, y);
				}
			}
		});
		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch())
				shell.getDisplay().sleep();
		}
		isDisposed = true;
		shell.dispose();
	}

	public void redraw() {
		canvas.redraw();
	}

	public boolean isDisposed() {
		return isDisposed;
	}

	private class PaintListenerImplementation implements PaintListener {
		
		public void paintControl(PaintEvent e) {
			Rectangle clientArea = shell.getClientArea();
			final GC gc = e.gc;
			
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
			visualizationData.forAllPoints(clientArea.width, clientArea.height,
					new PointsCallBack() {
				
				@Override
				public void forScreenPoint(ScreenPoint screenPoint, int id) {
					gc.drawOval((int) screenPoint.x - 1,
							(int) screenPoint.y - 1, 2, 2);
					gc.drawText("" +id, (int)screenPoint.x + 5, (int)screenPoint.y + 5);
				}
			});
			
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			visualizationData.forTour(clientArea.width, clientArea.height,
					new TourCallBack() {
				
				@Override
				public void forScreenPoint(ScreenPoint screenPoint, int id) {
					gc.drawOval((int) screenPoint.x - 3,
							(int) screenPoint.y - 3, 6, 6);
				}
				
				@Override
				public void forLine(ScreenPoint startPoint,
						ScreenPoint endPoint) {
					gc.drawLine((int) startPoint.x, (int) startPoint.y,
							(int) endPoint.x, (int) endPoint.y);
				}
				
				@Override
				public void forTourLength(double tourLength) {
//					gc.drawString("" + tourLength, 100, 100);
					shell.setText(title + " " + tourLength);
				}
			});
		}
	}
	
	public static void visualize(final TourConfiguration tour, final String title) {
		
		final Display display = Display.getDefault();
		
		display.asyncExec(new Runnable() {
			
			@Override
			public void run() {
				VisualizationData visualizationData = new VisualizationData(tour.getProblemData(), tour);
				Visualization visualization = new Visualization(visualizationData, title);
				visualization.display(display);
			}
		});
		
	}
}
