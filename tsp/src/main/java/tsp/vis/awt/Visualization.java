package tsp.vis.awt;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.vis.ScreenPoint;
import tsp.vis.VisualizationData;
import tsp.vis.VisualizationData.TourCallBack;
import tsp.vis.VisualizationData.PointsCallBack;

@Deprecated
public class Visualization extends Frame {

	
	/**
	 * just for the compiler
	 */
	private static final long serialVersionUID = 1L;
	private TourConfiguration configuration = null;
	private final VisualizationData visualizationData;
	private volatile boolean isDisposed = false;
	private DrawingPanel drawingPanel;
	
	private Object syncObj = new Object();


	public Visualization(TourConfiguration configuration, ProblemData problemData) {
		this(problemData);
		this.configuration = configuration;
	}
	public Visualization(ProblemData problemData) {
		visualizationData = new VisualizationData(problemData);
		this.setTitle("TSP Configuration");
		this.setSize(1000, 1000);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				dispose();
				isDisposed = true;
			}
		});
		
		setLayout(new GridLayout(1,1)); 
		
//		Panel panel = new Panel();
//		panel.add(new Button("OK"));
//		panel.add(new Button("Abbrechen"));
//		panel.setSize(1000, 100);
//		add(panel);
		
		drawingPanel = new DrawingPanel();
		drawingPanel.setSize(1000, 1000);
		add(drawingPanel);
		
//		pack();
		this.setVisible(true);
	}
	
	class DrawingPanel extends Panel
	  {
	    public void paint(final Graphics graphics)
	    {
//			Insets insts = getInsets();
			int iSizeX = getSize().width;// - insts.left - insts.right;
			int iSizeY = getSize().height;// - insts.top - insts.bottom;
			
			graphics.setColor(Color.blue);
			visualizationData.forAllPoints(iSizeX, iSizeY, new PointsCallBack() {

				@Override
				public void forScreenPoint(ScreenPoint screenPoint) {
					graphics.drawOval((int) screenPoint.x - 1,
							(int) screenPoint.y - 1, 2, 2);
				}
				
				
			});
			
			
			TourConfiguration configurationCopy;
			synchronized (syncObj) {
				if (configuration == null) {
					return;
				}
				configurationCopy = configuration.copy();
			}				
			
			graphics.setColor(Color.black);
			
			visualizationData.forTour(iSizeX, iSizeY, new TourCallBack() {
				
				@Override
				public void forScreenPoint(ScreenPoint screenPoint) {
					graphics.drawOval((int)screenPoint.x - 3, (int)screenPoint.y-3, 6, 6);
				}
				
				@Override
				public void forLine(ScreenPoint startPoint, ScreenPoint endPoint) {
					graphics.drawLine((int)startPoint.x, (int)startPoint.y, (int)endPoint.x, (int)endPoint.y);
				}

				@Override
				public void forTourLength(double tourLength) {
					graphics.drawString("" + tourLength, 100, 100);
				}
			});
	    }
	  }  
	



	public void setConfiguration(TourConfiguration configuration) {
		synchronized (syncObj) {
			this.configuration = configuration.copy();
		}
		drawingPanel.repaint();
	}
	public boolean isDisposed() {
		return isDisposed;
	}
}
