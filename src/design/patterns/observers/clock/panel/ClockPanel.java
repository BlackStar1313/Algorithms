package clock.panel;

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
     * Creates a new instance of <code>ClockPanel</code>.
     */
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

	/**
	 * Sets the current time.
	 * @param hour		the current hour.
	 * @param minute	the current minute.
	 * @param second	the current second.
	 */
	public void changeTime(int hour, int minute, int second) {

		if(hour < 0 || hour > 24 || minute < 0 || minute > 59 || second < 0 || second
				> 59) { throw new IllegalArgumentException("You have entered [" + hour + ", "
						+ minute + ", " + second); }

		this.hour = hour; 
		this.minute = minute; 
		this.second = second;
		repaint();
	}
	
	/**
	 * Draw the clock's tick marks
	 * @param g				the graphic element
	 * @param posX			the position x.	
	 * @param posY			the position y
	 * @param angle			the angle
	 * @param minRadius		the minimum radius
	 * @param maxRadius		the maximum radius
	 */
	protected void drawTickMarks(Graphics g, int posX, int posY, double angle, int minRadius, int maxRadius) {
		float sine = (float)Math.sin(angle);
		float cosine = (float)Math.cos(angle);
		int dxmin = (int)(minRadius * sine);
		int dymin = (int)(minRadius * cosine);
		int dxmax = (int)(maxRadius * sine);
		int dymax = (int)(maxRadius * cosine);
		g.drawLine(posX+dxmin, posY+dymin, posX+dxmax, posY+dymax);
	}

}
