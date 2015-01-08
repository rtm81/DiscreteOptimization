package tsp;

public interface Publisher extends Runnable {

	public void addListener(ConfigurationChangedListener configurationChangedListener);
}
