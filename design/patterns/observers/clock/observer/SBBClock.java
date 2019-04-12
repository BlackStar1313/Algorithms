package clock.observer;

import java.awt.Dimension;

import clock.observable.ClockTimer;
import clock.panel.ClockPanel;
import clock.panel.SBBClockPanel;

public class SBBClock extends ClockFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6711692528524274836L;

	public SBBClock(ClockTimer timer, int width, int height) {
		super(timer, width, height);
	}

	@Override
	protected ClockPanel createClockPanel(int width, int height) {
		SBBClockPanel clockPanel = new SBBClockPanel();
        clockPanel.setPreferredSize(new Dimension(width, height));
        return clockPanel;
	}

	@Override
	protected String getFrameTitle() {
		return "SBB Clock";
	}

}
