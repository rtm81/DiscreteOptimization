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

	public Visualization(VisualizationData visualizationData) {
		this.visualizationData = visualizationData;
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
		shell.setText(TITLE);
		shell.setLayout(new FillLayout());

		canvas = new Canvas(shell, SWT.NONE);
		canvas.addPaintListener(new PaintListenerImplementation());

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
				public void forScreenPoint(ScreenPoint screenPoint) {
					gc.drawOval((int) screenPoint.x - 1,
							(int) screenPoint.y - 1, 2, 2);
				}
			});
			
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			visualizationData.forTour(clientArea.width, clientArea.height,
					new TourCallBack() {
				
				@Override
				public void forScreenPoint(ScreenPoint screenPoint) {
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
					gc.drawString("" + tourLength, 100, 100);
					shell.setText(TITLE + " " + tourLength);
				}
			});
		}
	}
}
