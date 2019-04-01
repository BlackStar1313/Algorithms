package design.patterns.observers.clock.util;

import java.awt.Point;

/**
 * A singelton for managing the positions of application windows.
 * 
 * @author Peguy Njoyim
 */
public class PositionManager {
    private static PositionManager INSTANCE = new PositionManager();

	/**
	 * 
	 * @uml.property name="mainWindowPosition" multiplicity="(0 1)"
	 */
	private Point mainWindowPosition;

    private Point observerWindowPosition;

    /**
     * Creates a new instance of <code>PositionManager</code>.
     */
    private PositionManager() {
        mainWindowPosition = new Point (100, 100);
        observerWindowPosition = new Point (280, 80);
    }
    
    /**
     * Returns the single instance of the <code>PositionManager</code> class.
     */
    public static PositionManager getUniqueInstance() {
        return INSTANCE;
    }

	/**
	 * Returns the position for the main window.
	 * 
	 * @uml.property name="mainWindowPosition"
	 */
	public Point getMainWindowPosition() {
		return mainWindowPosition;
	}

    
    /**
     * Returns the position for a new clock window.
     */
    public Point getClockWindowPosition() {
        observerWindowPosition.translate(20, 20);
        return observerWindowPosition;
    }
}
