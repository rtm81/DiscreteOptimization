package tsp;

public interface ConfigurationChangedListener {
	
	/**
	 * 
	 * @param configuration the changed {@link TourConfiguration}
	 * @return {@code true} if the calculation is to be canceled.
	 */
	public boolean changePerformed(TourConfiguration configuration);
}