package clock.observer;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import clock.observable.ClockTimer;
import clock.panel.ClockPanel;


/**
 * An abstract superclass for all clock frames. The creation of the clock panel is deferred to the subclasses.
 * 
 * @author Peguy Njoyim
 *
 */
public abstract class ClockFrame extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7255610670778737562L;
	
	/**
	 * The clock timer
	 */
	private ClockTimer timer;

	/**
	 * The clock Panel
	 */
	private ClockPanel clockPanel;


	/**
	 * 
     * Creates a new instance of <code>ClockFrame</code> that observes the given clock timer.
	 * @param width		the width of the window
	 * @param height	the height of the window
	 */
	public ClockFrame(ClockTimer timer, int width, int height) {
		this.timer = timer;
		
		// Set up the window.
		setTitle(getFrameTitle());
        addWindowListener(new DetachOnClosingWindowListener());
		
		// Create and set up the analog clock panel.
        clockPanel = createClockPanel(width, height);

		// Add the panel to the window.
		getContentPane().add(clockPanel, BorderLayout.CENTER);
		
		timer.addObserver(this);
		update();

		// Display the window.
		pack();
		setVisible(true);
	}
	
	 /**
     * Updates the clock.
     */
    public void update() {
        int hour = timer.getHour();
        int minute = timer.getMinute();
        int second = timer.getSecond();
        clockPanel.changeTime(hour, minute, second);
    }

	/**
	 * Defines an abstract factory method which creates and returns a clock
	 * panel. Must be implemented by the subclasses.
	 */
	protected abstract ClockPanel createClockPanel(int width, int height);

	/**
	 * Defines an abstract primitive operation for setting the frame's title.
	 * Must be implemented by the subclasses.
	 */
	protected abstract String getFrameTitle();

	/**
	 * A window listener that detaches the clock from the timer when the window 
	 * is being closed.
	 */
	private class DetachOnClosingWindowListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			//Doing some clean-ups
			timer.removeObserver(ClockFrame.this);
		}
	}

}
