package tsp;

import java.util.ArrayList;
import java.util.List;

public class AbstractPublisher implements Publisher {
	private final List<ConfigurationChangedListener> listener = new ArrayList<>();
	
	@Override
	public void addListener(ConfigurationChangedListener... configurationChangedListeners) {
		for (ConfigurationChangedListener configurationChangedListener : configurationChangedListeners) {
			listener.add(configurationChangedListener);
		}
	}
	
	public void addListener(List<ConfigurationChangedListener> listener){
		this.listener.addAll(listener);
	}
	
	protected void forwardListener(Publisher publisher) {
		publisher.addListener(listener);
	}
	
	public boolean notify(TourConfiguration tour) {
		boolean quit = false;
		for (ConfigurationChangedListener configurationChangedListener : listener) {
			if (configurationChangedListener.changePerformed(tour)) {
				quit = true;
			}
		}
		return quit;
	}

}
