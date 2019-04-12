package clock.observer;

import java.awt.Dimension;

import clock.observable.ClockTimer;
import clock.panel.ClockPanel;
import clock.panel.DigitalClockPanel;

public class DigitalClock extends ClockFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6863315938156821185L;
	
	
	public DigitalClock(ClockTimer timer, int width, int height) {
		super(timer, width, height);
	}


	@Override
	protected ClockPanel createClockPanel(int with, int height) {
		DigitalClockPanel clockPanel = new DigitalClockPanel();
		clockPanel.setPreferredSize(new Dimension(with, height));
		return clockPanel;
	}


	@Override
	protected String getFrameTitle() {
		return "Digital Clock";
	}

}
