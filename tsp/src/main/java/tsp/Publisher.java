package tsp;

import java.util.List;

public interface Publisher {

	public void addListener(ConfigurationChangedListener... configurationChangedListener);
	
	public void addListener(List<ConfigurationChangedListener> listener);
}
