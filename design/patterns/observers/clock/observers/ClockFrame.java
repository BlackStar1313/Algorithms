package design.patterns.observers.clock.observers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import clock.panel.ClockPanel;
import clock.util.PositionManager;

public abstract class ClockFrame extends JFrame implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7255610670778737562L;
	
	
	public ClockFrame(String frameName, ClockPanel clkPanel, int width, int height) {
		setTitle(frameName);
		addWindowFocusListener(new DetachOnClosingWindowListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create and set up the clock panel.
		clkPanel.setPreferredSize(new Dimension(width, height));
		
		// Add the panel to the window.
        getContentPane().add(clkPanel, BorderLayout.CENTER);
		
		// Set screen position.
        setLocation(PositionManager.getUniqueInstance().getClockWindowPosition());
        
        // Display the window.
        pack();
        setVisible(true);
	}
	
	 /**
     * A window listener that detaches the clock from the timer when the window 
     * is being closed.
     */
    private class DetachOnClosingWindowListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            // Do some cleanup here...
        }
    }

}
