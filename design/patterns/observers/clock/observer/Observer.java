package clock.observer;

/**
 * <code>Observer</code> defines an interface for objects that should be 
 * notified of changes by a subject.
 * 
 * @author Peguy Njoyim
 */
public interface Observer {
	
	/**
     * Invoked by a subject when its state changed.
     */
	void update();
}
