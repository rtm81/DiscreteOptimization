package tsp.vis.awt;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import tsp.Point;
import tsp.ProblemData;
import tsp.TourConfiguration;


public class Visualization extends Frame {

	
	/**
	 * just for the compiler
	 */
	private static final long serialVersionUID = 1L;
	private TourConfiguration configuration = null;
	private ProblemData problemData = null;
	private volatile boolean isDisposed = false;
	private DrawingPanel drawingPanel;


	public Visualization(TourConfiguration configuration, ProblemData problemData) {
		this(problemData);
		this.configuration = configuration;
	}
	public Visualization(ProblemData problemData) {
		this.problemData = problemData;
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
	    public void paint(Graphics graphics)
	    {
			if (problemData == null || configuration == null) {
				return;
			}
//			Insets insts = getInsets();
			int iSizeX = getSize().width;// - insts.left - insts.right;
			int iSizeY = getSize().height;// - insts.top - insts.bottom;
			
			{
				graphics.setColor(Color.blue);
				for (int j = 0; j < problemData.getProblemSize(); j++) {
					ScreenPoint screenPoint = getScreenPoint(iSizeX, iSizeY, problemData.get(j));
					graphics.drawOval((int)screenPoint.x - 1, (int)screenPoint.y-1, 2, 2);
				}
			}
			
			graphics.setColor(Color.black);
			int size = configuration.getSize();
			if (size > 0) {
				ScreenPoint lastPoint = getScreenPoint(iSizeX, iSizeY, configuration.getPoint(size - 1));
				for (int j = 0; j < size; j++) {
					ScreenPoint screenPoint = getScreenPoint(iSizeX, iSizeY, configuration.getPoint(j));
					graphics.drawOval((int)screenPoint.x - 3, (int)screenPoint.y-3, 6, 6);
					graphics.drawLine((int)lastPoint.x, (int)lastPoint.y, (int)screenPoint.x, (int)screenPoint.y);
					lastPoint = screenPoint;
				}
				// tour length
				graphics.drawString("" + configuration.calculateTourLength(), 100, 100);
			}
			
	    }
	  }  
	

	private ScreenPoint getScreenPoint(int iSizeX, int iSizeY, Point point) {
		float border = 0.08f;
		int screenX = (int)Math.floor( (1.0f - border) * iSizeX * ((point.getX() - problemData.getSmallest().getX()) / (problemData.getLargest().getX() - problemData.getSmallest().getX())) + (border / 2.0f) * iSizeX);
		int screenY = (int)Math.floor( (1.0f - border) * iSizeY * ((point.getY() - problemData.getSmallest().getY()) / (problemData.getLargest().getY() - problemData.getSmallest().getY())) + (border / 2.0f) * iSizeX);
		ScreenPoint screenPoint = new ScreenPoint(screenX, screenY);
		return screenPoint;
	}

	public void setConfiguration(TourConfiguration configuration) {
		this.configuration = configuration;
		drawingPanel.repaint();
	}
	public boolean isDisposed() {
		return isDisposed;
	}

	private static class ScreenPoint {
		float x;
		float y;
		
		ScreenPoint(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
}
