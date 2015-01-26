package tsp.vis.swt;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.widgets.Display;

import tsp.util.TourConfiguration;
import tsp.vis.VisualizationData;

public class Utils {

	public static void visualize(Map<TourConfiguration, String > tours) {
		Display display = new Display();
		for (Entry<TourConfiguration, String> entry: tours.entrySet()) {
			Utils.visualize(entry.getKey(), entry.getValue());
		}
		while (!display.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
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
