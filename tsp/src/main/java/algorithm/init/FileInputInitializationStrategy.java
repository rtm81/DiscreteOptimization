package algorithm.init;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import tsp.AbstractPublisher;
import tsp.util.ProblemData;
import tsp.util.TourConfiguration;
import tsp.util.TourConfigurationCollection;
import tsp.util.parser.TourConfigurationParser;

public class FileInputInitializationStrategy extends AbstractPublisher
		implements InitializationStrategy, FileSelecter {

	private String selectedFile;

	@Override
	public TourConfigurationCollection calculate(ProblemData problemData) {
		TourConfigurationCollection tourConfigurationCollection = new TourConfigurationCollection();

		TourConfigurationParser tourConfigurationParser = new TourConfigurationParser();

		try (BufferedReader br = new BufferedReader(
				new FileReader(selectedFile))) {
			for (String line; (line = br.readLine()) != null;) {
				Map<Integer, Integer> parseTourSolution = tourConfigurationParser
						.parseTourSolutionWithUnknown(line);
				TourConfiguration tourConfiguration = new TourConfiguration(
						problemData, parseTourSolution);
				tourConfigurationCollection.addTour(tourConfiguration);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return tourConfigurationCollection;
	}

	@Override
	public void selectFile(Shell shell) {
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Open");
		fd.setFilterPath("./tsp/data");
		selectedFile = fd.open();
	}

}
