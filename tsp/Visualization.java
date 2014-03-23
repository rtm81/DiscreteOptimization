import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Visualization extends Frame {

	
	/**
	 * just for the compiler
	 */
	private static final long serialVersionUID = 1L;
	private TourConfiguration configuration = null;
	private ProblemData problemData = null;

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
				System.exit(0);
			}
		});
	}
	
	public void paint(Graphics graphics) {
		Insets insts = getInsets();
		int iOriginX = insts.left;
		int iOriginY = insts.top;
		int iSizeX = getSize().width - insts.left - insts.right;
		int iSizeY = getSize().height - insts.top - insts.bottom;
//		graphics.setColor(Color.blue);
//		graphics.drawRoundRect(iOriginX, iOriginY, iSizeX - 1, iSizeY - 1, 16, 16);
//		FontMetrics fm = graphics.getFontMetrics(graphics.getFont());
//		String s = "" + iSizeX + " x " + iSizeY + " Pixel";
//		graphics.drawString(s, (iSizeX - fm.stringWidth(s)) / 2,
//				(iSizeY - fm.getHeight()) / 2 + fm.getAscent());
		
		graphics.setColor(Color.black);
		
		if (problemData != null && configuration != null) {
			Point lastPoint = getScreenPoint(iSizeX, iSizeY, configuration.getPoint(configuration.getSize() - 1));
			for (int j = 0; j < configuration.getSize(); j++) {
				Point screenPoint = getScreenPoint(iSizeX, iSizeY, configuration.getPoint(j));
				graphics.drawOval((int)screenPoint.x - 3, (int)screenPoint.y-3, 6, 6);
				graphics.drawLine((int)lastPoint.x, (int)lastPoint.y, (int)screenPoint.x, (int)screenPoint.y);
				lastPoint = screenPoint;
			}
			
		}
		
	}

	private Point getScreenPoint(int iSizeX, int iSizeY, Point point) {
		int screenX = (int)Math.floor( iSizeX * ((point.x - problemData.getSmallest().x) / (problemData.getLargest().x - problemData.getSmallest().x))) +5;
		int screenY = (int)Math.floor( iSizeY * ((point.y - problemData.getSmallest().y) / (problemData.getLargest().y - problemData.getSmallest().y))) +5;
		Point screenPoint = new Point(screenX, screenY);
		return screenPoint;
	}
}
