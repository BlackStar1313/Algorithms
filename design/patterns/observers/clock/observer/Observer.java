package design.patterns.observers.clock.observers;

public interface Observer {
	
	/**
	 * 
	 * @param h
	 * @param m
	 * @param s
	 */
	void update(int h, int m, int s);
}
