package clock.observer;

import java.awt.Dimension;
import clock.observable.ClockTimer;
import clock.panel.ClockPanel;
import clock.panel.CustomAnalogClockPanel;

public class CustomAnalogClock extends ClockFrame {

	private static final long serialVersionUID = 6672895497326727502L;
	
	
	public CustomAnalogClock(ClockTimer timer, int width, int height) {
		super(timer, width, height);
	}


	@Override
	protected ClockPanel createClockPanel(int with, int height) {
		CustomAnalogClockPanel clkAnalogClockPanel = new CustomAnalogClockPanel();
		clkAnalogClockPanel.setPreferredSize(new Dimension(with, height));
		return clkAnalogClockPanel;
	}


	@Override
	protected String getFrameTitle() {
		return "Custom Analog Clock";
	}

}
