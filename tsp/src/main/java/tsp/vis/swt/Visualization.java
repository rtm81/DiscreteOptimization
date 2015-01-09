package tsp.vis.swt;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import tsp.ConfigurationChangedListener;
import tsp.RunnablePublisher;
import tsp.util.TourConfiguration;
import tsp.vis.ScreenPoint;
import tsp.vis.VisualizationData;
import tsp.vis.VisualizationData.PointsCallBack;
import tsp.vis.VisualizationData.TourCallBack;

public class Visualization {
	private static final String TITLE = "TSP Configuration";

	Display display;
	Shell shell;
	private Canvas canvas;

	private final VisualizationData visualizationData;
	private final RunnablePublisher runnable;
	private volatile boolean isDisposed = false;

	public Visualization(VisualizationData visualizationData, RunnablePublisher runnable) {
		this.visualizationData = visualizationData;
		this.runnable = runnable;
	}
	
	public void display() {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(800, 800);
		shell.setText(TITLE);
		shell.setLayout(new FillLayout());
		
		canvas = new Canvas(shell, SWT.NONE);
		
		canvas.addPaintListener(new PaintListener(){
	        public void paintControl(PaintEvent e){
	            Rectangle clientArea = shell.getClientArea();
	            final GC gc = e.gc;
	         
	            gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLUE));
				visualizationData.forAllPoints(clientArea.width, clientArea.height, new PointsCallBack() {

					@Override
					public void forScreenPoint(ScreenPoint screenPoint) {
						gc.drawOval((int) screenPoint.x - 1,
								(int) screenPoint.y - 1, 2, 2);
					}
				});
				

				
				gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
				visualizationData.forTour(clientArea.width, clientArea.height, new TourCallBack() {
					
					@Override
					public void forScreenPoint(ScreenPoint screenPoint) {
						gc.drawOval((int)screenPoint.x - 3, (int)screenPoint.y-3, 6, 6);
					}
					
					@Override
					public void forLine(ScreenPoint startPoint, ScreenPoint endPoint) {
						gc.drawLine((int)startPoint.x, (int)startPoint.y, (int)endPoint.x, (int)endPoint.y);
					}

					@Override
					public void forTourLength(double tourLength) {
						gc.drawString("" + tourLength, 100, 100);
						shell.setText(TITLE + " " + tourLength);
					}
				});
	        }

	    });
		
		

		visualizationData.addListener(new ConfigurationChangedListener() {
			
			@Override
			public boolean changePerformed(TourConfiguration configuration) {
				if (isDisposed()) {
					return true;
				} else {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (isDisposed) {
								return;
							}
							canvas.redraw();
						}
					});
					return false;
				}
			}
		});
		
		Thread thread = new Thread(runnable);
		thread.setName("TSP");
		thread.start();
		
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		isDisposed = true;
	}
	

	public boolean isDisposed() {
		return isDisposed;
	}

}
