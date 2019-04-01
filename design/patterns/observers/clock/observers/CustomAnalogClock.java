package design.patterns.observers.clock.observers;

import clock.panel.CustomAnalogClockPanel;

public class CustomAnalogClock extends ClockFrame {

	private static final long serialVersionUID = 6672895497326727502L;
	
	private static String title;
	private static CustomAnalogClockPanel cstAnalogClock;
	
	static {
		title = "Custom Analog Clock";
		cstAnalogClock = new CustomAnalogClockPanel();
	}
	
	public CustomAnalogClock(int width, int height) {
		super(title, cstAnalogClock, width, height);
	}

	@Override
	public void update(int h, int m, int s) {
		cstAnalogClock.changeTime(h, m, s);
		cstAnalogClock.repaint();
	}

}
