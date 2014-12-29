import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Visualization extends Frame {

	
	/**
	 * just for the compiler
	 */
	private static final long serialVersionUID = 1L;
	private TourConfiguration configuration = null;
	private ProblemData problemData = null;
	private boolean isDisposed = false;

	public static void main(String[] args) {
		new Visualization();
	}

	public Visualization(TourConfiguration configuration, ProblemData problemData) {
		this();
		this.configuration = configuration;
		this.problemData = problemData;
	}
	public Visualization() {
		this.setTitle("TSP Configuration");
		this.setSize(1000, 1000);
		this.setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				dispose();
				isDisposed = true;
//				System.exit(0);
			}
		});
	}
	
	public void paint(Graphics graphics) {
		if (problemData == null || configuration == null) {
			return;
		}
//		Insets insts = getInsets();
		int iSizeX = getSize().width;// - insts.left - insts.right;
		int iSizeY = getSize().height;// - insts.top - insts.bottom;
		
		graphics.setColor(Color.black);
		ScreenPoint lastPoint = getScreenPoint(iSizeX, iSizeY, configuration.getPoint(configuration.getSize() - 1));
		for (int j = 0; j < configuration.getSize(); j++) {
			ScreenPoint screenPoint = getScreenPoint(iSizeX, iSizeY, configuration.getPoint(j));
			graphics.drawOval((int)screenPoint.x - 3, (int)screenPoint.y-3, 6, 6);
			graphics.drawLine((int)lastPoint.x, (int)lastPoint.y, (int)screenPoint.x, (int)screenPoint.y);
			lastPoint = screenPoint;
		}
		
		// tour length
		graphics.drawString("" + configuration.calculateTourLength(), 100, 100);
	}

	private ScreenPoint getScreenPoint(int iSizeX, int iSizeY, Point point) {
		float border = 0.08f;
		int screenX = (int)Math.floor( (1.0f - border) * iSizeX * ((point.x - problemData.getSmallest().x) / (problemData.getLargest().x - problemData.getSmallest().x)) + (border / 2.0f) * iSizeX);
		int screenY = (int)Math.floor( (1.0f - border) * iSizeY * ((point.y - problemData.getSmallest().y) / (problemData.getLargest().y - problemData.getSmallest().y)) + (border / 2.0f) * iSizeX);
		ScreenPoint screenPoint = new ScreenPoint(screenX, screenY);
		return screenPoint;
	}

	public void setConfiguration(TourConfiguration configuration) {
		this.configuration = configuration;
		this.repaint();
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
