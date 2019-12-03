package clock;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clock.observable.ClockTimer;
import clock.observer.AnalogClock;
import clock.observer.CustomAnalogClock;
import clock.observer.DigitalClock;
import clock.observer.SBBClock;
import clock.util.PositionManager;

/**
 * The <code>ClockApp</code> class represents the application's main window.
 * 
 * @author Peguy Njoyim
 */
public class ClockApp extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4551831349802075686L;

	private ClockTimer timer;

	private JPanel panel;

	private JButton startButton;
	private JButton stopButton;

	private JButton analogClockButton;
	private JButton digitalClockButton;
	private JButton customAnalogClockButton;
	private JButton SBBClockButton;
	

	/**
	 * Creates a new instance of <code>ClockApp</code>.
	 */
	public ClockApp() {

		// Create the timer;
		timer = new ClockTimer();

		// Set up the window.
		setTitle("Clock Timer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create and set up the panel.
		panel = new JPanel(new GridLayout(0, 1, 5, 5));
		panel.setPreferredSize(new Dimension(300, 300));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		addWidgets();
		
		// Add the panel to the window.
        getContentPane().add(panel, BorderLayout.CENTER);
		
		// Set screen position.
		setLocation(PositionManager.getUniqueInstance().getMainWindowPosition());

		// Display the window.
		pack();
		setVisible(true);
	}

	/**
	 * The application's main method.
	 */
	public static void main(String[] args) {
		new ClockApp();
	}

	/**
	 * Create and add the widgets.
	 */
	private void addWidgets() {
		// Create and set up the start button.
		startButton = new JButton("Start timer");
		startButton.addActionListener(e ->{
			timer.start();
		});

		// Create and set up the stop button.
		stopButton = new JButton("Stop timer");
		stopButton.addActionListener( e ->{
			timer.stop();
		});

		// Create and set up the analog clock button.
		analogClockButton = new JButton("Analog clock");
		analogClockButton.addActionListener(e ->{
			new AnalogClock(timer, 480, 640);
		});

		// Create and set up the digital clock button.
		digitalClockButton = new JButton("Digital clock");
		digitalClockButton.addActionListener(e ->{
			new DigitalClock(timer, 640, 480);
		});
		
		customAnalogClockButton = new JButton("Custom Analog Clock");
		customAnalogClockButton.addActionListener(e ->{
			new CustomAnalogClock(timer, 480, 640);
		});
		
		SBBClockButton = new JButton("SBB Clock");
		SBBClockButton.addActionListener(e ->{
			new SBBClock(timer, 480, 640);
		});
		
		panel.add(startButton);
		panel.add(stopButton);
		panel.add(analogClockButton);
		panel.add(digitalClockButton);
		panel.add(customAnalogClockButton);
		panel.add(SBBClockButton);
		return;
	}
}
