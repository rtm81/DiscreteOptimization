package tsp.vis;

import tsp.AbstractPublisher;
import tsp.util.Point;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;

public class VisualizationData extends AbstractPublisher {
	
	private final ProblemData problemData;
	private TourConfiguration configuration = null;
	
	private final Object syncObj = new Object();
	
	public VisualizationData(ProblemData problemData) {
		this.problemData = problemData;
	}

	public ScreenPoint getScreenPoint(int iSizeX, int iSizeY, Point point) {
		float border = 0.08f;
		int screenX = (int)Math.floor( (1.0f - border) * iSizeX * ((point.getX() - problemData.getSmallest().getX()) / (problemData.getLargest().getX() - problemData.getSmallest().getX())) + (border / 2.0f) * iSizeX);
		int screenY = (int)Math.floor( (1.0f - border) * iSizeY * ((point.getY() - problemData.getSmallest().getY()) / (problemData.getLargest().getY() - problemData.getSmallest().getY())) + (border / 2.0f) * iSizeX);
		ScreenPoint screenPoint = new ScreenPoint(screenX, screenY);
		return screenPoint;
	}
	
	public void setConfiguration(TourConfiguration configuration) {
		synchronized (syncObj) {
			this.configuration = configuration;
		}
		notify(configuration);
	}
	
	public void forAllPoints (int iSizeX, int iSizeY, PointsCallBack forAllCallBack) {
		for (Point point : problemData) {
			ScreenPoint screenPoint = getScreenPoint(iSizeX, iSizeY, point);
			forAllCallBack.forScreenPoint(screenPoint);
		}
	}
	
	public void forTour(int iSizeX, int iSizeY, TourCallBack callBack) {
		TourConfiguration tour;
		synchronized (syncObj) {
			if (configuration == null) {
				return;
			}
			tour = configuration.copy();
		}
		int size = tour.getSize();
		if (size > 0) {
			ScreenPoint lastPoint = getScreenPoint(iSizeX, iSizeY, tour.getPoint(size - 1));
			for (int j = 0; j < size; j++) {
				ScreenPoint screenPoint = getScreenPoint(iSizeX, iSizeY, tour.getPoint(j));
				callBack.forScreenPoint(screenPoint);
				callBack.forLine(lastPoint, screenPoint);
				lastPoint = screenPoint;
			}
			// tour length
			callBack.forTourLength(tour.calculateTourLength());
		}
	}
	
	public interface PointsCallBack {
		public void forScreenPoint(ScreenPoint screenPoint);
	}
	
	public interface TourCallBack extends PointsCallBack{
		public void forLine(ScreenPoint startPoint, ScreenPoint endPoint);
		public void forTourLength(double tourLength);
	}
}
