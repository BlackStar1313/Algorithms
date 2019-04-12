package clock.observer;

import java.awt.Dimension;

import clock.observable.ClockTimer;
import clock.panel.AnalogClockPanel;
import clock.panel.ClockPanel;

/**
 * An analog clock observing a clock timer.
 * 
 * @author Peguy Njoyim
 */
public class AnalogClock extends ClockFrame {
	
    private static final long serialVersionUID = 3258408447900069937L;


    /**
     * Creates a new instance of <code>AnalogClock</code> that observes the 
     * given clock timer.
     */
    public AnalogClock(ClockTimer timer, int width, int height) {
    	super(timer, width, height);
    }

	@Override
	protected ClockPanel createClockPanel(int width, int height) {
		AnalogClockPanel clockPanel = new AnalogClockPanel();
        clockPanel.setPreferredSize(new Dimension(width, height));
        return clockPanel;
	}

	@Override
	protected String getFrameTitle() {
		return "Analog Clock";
	}
}