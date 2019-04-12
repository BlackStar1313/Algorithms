package design.patterns.observers.clock.observers;

import clock.panel.AnalogClockPanel;

/**
 * An analog clock observing a clock timer.
 * 
 * @author Peguy Njoyim
 */
public class AnalogClock extends ClockFrame {
    private static final long serialVersionUID = 3258408447900069937L;

	/**
	 * 
	 * @uml.property name="analogClockPanel"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private static AnalogClockPanel analogClockPanel;
	private static String title;
	
	static {
		title = "Analog Clock";
		analogClockPanel = new AnalogClockPanel();
	}

    /**
     * Creates a new instance of <code>AnalogClock</code> that observes the 
     * given clock timer.
     */
    public AnalogClock(int width, int height) {
        super(title, analogClockPanel, width, height);
    }

	@Override
	public void update(int h, int m, int s) {
		analogClockPanel.changeTime(h, m, s);
        analogClockPanel.repaint();
	}
}