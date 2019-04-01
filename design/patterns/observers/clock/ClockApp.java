package design.patterns.observers.clock;

import clock.observable.ClockTimer;
import clock.observers.AnalogClock;
import clock.observers.CustomAnalogClock;
import clock.observers.DigitalClock;

/**
 * The <code>ClockApp</code> class represents the application's main window.
 * 
 * @author Peguy Njoyim
 */
public class ClockApp {

	/**
	 * Creates a new instance of <code>ClockApp</code>.
	 */
	public ClockApp() {
		ClockTimer timer = new ClockTimer(new AnalogClock(340, 640), new DigitalClock(640, 360), new CustomAnalogClock(480, 640));
		timer.start();
	}

	/**
	 * The application's main method.
	 */
	public static void main(String[] args) {
		new ClockApp();
	}
}
