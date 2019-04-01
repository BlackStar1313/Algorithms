package design.patterns.observers.clock.panel;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

public abstract class ClockPanel extends JPanel {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 6717678528829482311L;

	private static final String FONT_FILE_PATH = "fonts\\digital-7.ttf";
	
	/**
	 * hour's variable for a clock
	 */
	protected int hour;

	/**
	 * minute's variable for a clock
	 */
	protected int minute;

	/**
	 * second's variable for a clock
	 */
	protected int second;

	/**
	 * the width of the panel
	 */
	protected int width;

	/**
	 * The height of the panel
	 */
	protected int height;

	public ClockPanel() {
		try {
			//create the font to use.
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(FONT_FILE_PATH)));
		} catch (IOException | FontFormatException ioe) {
			ioe.printStackTrace();
			System.out.println("font not loaded!!");
		}
	}


	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		width = getWidth();
		height = getHeight();
	}

	/**
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 */

	public void changeTime(int hour, int minute, int second){

		if(hour < 0 || hour > 24 || minute < 0 || minute > 59 || second < 0 || second
				> 59) { throw new IllegalArgumentException("You have entered [" + hour + ", "
						+ minute + ", " + second); }

		this.hour = hour; 
		this.minute = minute; 
		this.second = second;
	}

}
