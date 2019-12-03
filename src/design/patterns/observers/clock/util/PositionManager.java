package clock.util;

import java.awt.Point;

/**
 * A singelton for managing the positions of application windows.
 * 
 * @author Andreas Ruppen
 */
public class PositionManager {
    private static PositionManager INSTANCE = new PositionManager();

	/**
	 * 
	 * @uml.property name="mainWindowPosition" multiplicity="(0 1)"
	 */
	private Point mainWindowPosition;

    /**
     * Creates a new instance of <code>PositionManager</code>.
     */
    private PositionManager() {
        mainWindowPosition = new Point (480, 200);
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
}
