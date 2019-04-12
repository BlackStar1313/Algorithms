package design.patterns.observers.clock.observers;

import clock.panel.DigitalClockPanel;

public class DigitalClock extends ClockFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6863315938156821185L;
	
	
	private static String title;
	private static DigitalClockPanel digitalClockPanel;
	
	// a static block initializing static variables
	static {
		title = "Digital Clock";
		digitalClockPanel = new DigitalClockPanel();
	}

	public DigitalClock(int width, int height) {
		super(title, digitalClockPanel, width, height);
	}

	@Override
	public void update(int h, int m, int s) {
		digitalClockPanel.changeTime(h, m, s);
		digitalClockPanel.repaint();
	}

}
