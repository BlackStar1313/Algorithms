package clock.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class CustomAnalogClockPanel extends ClockPanel {

	private static final long serialVersionUID = -6341244818508464007L;

	private static final double sa = Math.PI / 2;
	private static final double sda = Math.PI / 30;
	private static final double mda = sda / 60;
	private static final double hda = mda / 12;
	private static final double nda = Math.PI / 6;

	private int width, height;

	private int[] arcSecond = new int[61];
	private int[] arcMinute = new int[61];


	public CustomAnalogClockPanel() {
		for(int i = 0; i < 60; i++) {
			arcSecond[i+1] = arcSecond[i] - 6;
			arcMinute[i+1] = arcMinute[i] - 6;
		}
		arcSecond[0] = -360;
		arcMinute[0] = -360;
	}


	@Override
	protected void paintComponent(Graphics g) {

		int border = 50;

		width = getWidth();
		height = getHeight();

		Point center = new Point(width / 2, height / 2);
		int radius = (Math.min(width, height) / 2) - border;

		// setting the font
		final int FONT_SIZE = (int)((1.0 / 5.0) * radius);
		Font font = new Font("digital-7", Font.PLAIN, FONT_SIZE);
		setFont(font);

		// Clear background.
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		// Draw the clock components.
		drawSecondHand(g, center, radius, second);
		DesignClock(g, center, radius);
		drawClockNumbers(g, font, center, radius);
		drawHourHand(g, center, radius, hour, minute, second);
		drawMinuteHand(g, center, radius, minute, second);
		return;
	}

	/**
	 * Draw the design of the clock.
	 * @param g			the graphic element.
	 * @param c			the center of this clock.
	 * @param radius	the radius of this clock.
	 */
	private void DesignClock(Graphics g, Point c, int radius) {
		
		Graphics2D g2d = (Graphics2D)g;// Tell the compiler "g is actually a graphics2D"

		// drawing the tick marks
		int widthLitteMinutes = (int)(radius * (0.1 / 4.5));
		int widthBigMinutes = (int)(radius * (0.2 / 4.5));

		int lengthLitteMinutes = (int)(radius * (0.4 / 4.5));
		int lengthBigMinutes = (int)(radius * (0.8 / 4.5));

		int minRadius, maxRadius;
		for (int sec = 0; sec < 60; sec++) {
			if (sec % 5 == 0) {
				g2d.setStroke(new BasicStroke(widthBigMinutes));
				g.setColor(new Color(255, 22, 10));
				minRadius = radius;
				maxRadius = radius - lengthBigMinutes;
			}else{
				g2d.setStroke(new BasicStroke(widthLitteMinutes));
				g.setColor(Color.DARK_GRAY);
				minRadius = radius;
				maxRadius = radius - lengthLitteMinutes;
			}
			drawTickMarks(g, c.x, c.y, sda*sec, minRadius, maxRadius);
		}

		// drawing the outer black circle
		int blackCircleWidth = (int)((0.5 / 5.0) * radius);
		g2d.setStroke(new BasicStroke(blackCircleWidth));
		g.setColor(Color.BLACK);
		g.drawOval(c.x - radius, c.y - radius, 2 * radius, 2 * radius);
	}

	/**
	 * Draw the clock number using a given font.
	 * @param g		The graphic element.
	 * @param font	The font's number.
	 * @param c		The center of this clock.
	 * @param r		The radius of this clock.
	 */
	private void drawClockNumbers(Graphics g, Font font, Point c, int r) {

		FontMetrics fm = getFontMetrics(font);
		int fa = fm.getMaxAscent();
		int fh = (int) ((fm.getMaxAscent() + fm.getMaxDescent()) / 1.5);
		int nr = (70 * r) / 100;
		for (int i = 0; i < 12; i++) {
			String ns = Integer.toString((i == 0) ? 12 : i);
			int nx = (int) ((Math.cos((i * nda) - sa) * nr) + c.x);
			int ny = (int) ((Math.sin((i * nda) - sa) * nr) + c.y);
			int w = fm.stringWidth(ns) / 2;

			g.drawString(ns, nx - w, ny + fa - fh);
		}
	}

	/**
	 * Draws the clock's second hand.
	 * @param g The graphic element.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 * @param s seconds affected to the clock.
	 */
	private void drawSecondHand(Graphics g, Point c, int radius, int s) {
		int sr = (int) (radius - (height * 0.12f));
		int sx = (int) ((Math.cos((s * sda) - sa) * sr) + c.x);
		int sy = (int) ((Math.sin((s * sda) - sa) * sr) + c.y);

		Graphics2D g2d = (Graphics2D)g;
		int redFillOval = (int)((0.1 / 5.0) * radius);
		g2d.setStroke(new BasicStroke(redFillOval));
		g.setColor(new Color(1f, 0f, 0f, .5f));
		g.fillArc(c.x - radius, c.y - radius, 2 * radius, 2 * radius, 90, arcSecond[second]);
		g.drawLine(c.x, c.y, sx, sy);
	}


	/**
	 * Draws the clock's minute hand.
	 * @param g The graphic element.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 * @param m minutes affected to the clock.
	 * @param s seconds affected to the clock.
	 */
	private void drawMinuteHand(Graphics g, Point c, int radius, int m, int s) {
		int ms = m * 60;
		int mr = (int) ((3.0 / 5.0) * radius);
		int mx = (int) ((Math.cos(((ms + s) * mda) - sa) * mr) + c.x);
		int my = (int) ((Math.sin(((ms + s) * mda) - sa) * mr) + c.y);
		Graphics2D g2d = (Graphics2D)g;
		
		int BlueCircleWidth = (int)((0.1 / 5.0) * radius);
		g2d.setStroke(new BasicStroke(BlueCircleWidth));
		g.setColor(Color.BLUE);
		g.drawArc(c.x - radius, c.y - radius, 2 * radius, 2 * radius, 90, arcMinute[minute]);
		g.drawLine(c.x, c.y, mx, my);
	}

	/**
	 * Draws the clock's hour hand.
	 * @param g The graphic element.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 * @param h hours affected to the clock
	 * @param m minutes affected to the clock.
	 * @param s seconds affected to the clock.
	 */
	private void drawHourHand(Graphics g, Point c, int radius, int h, int m, int s) {
		int ms = m * 60;
		int hs = h * 60 * 60;
		int hr = (int) (.5 * radius);
		int hx = (int) ((Math.cos(((hs + ms + s) * hda) - sa) * hr) + c.x);
		int hy = (int) ((Math.sin(((hs + ms + s) * hda) - sa) * hr) + c.y);

		Graphics2D g2d = (Graphics2D)g;

		int BlackCircleWidth = (int)((0.2 / 5.0) * radius);
		g2d.setStroke(new BasicStroke(BlackCircleWidth));
		g.setColor(Color.BLACK);
		g.drawLine(c.x, c.y, hx, hy);
	}
}
