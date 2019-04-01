package design.patterns.observers.clock.panel;

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

	private static final int FONT_SIZE = 30;

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
		super.paintComponent(g);

		int border = 10;
		int r = (Math.min(width, height) / 2) - border;


		// Clear background.
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		Point center = new Point(width / 2, height / 2);

		// drawing the clock circle
		g.setColor(Color.WHITE);
		g.fillOval(center.x - r, center.y - r, 2 * r,  2 * r);
		Graphics2D g2d = (Graphics2D)g;//Tell the compiler "g is actually a graphics2D"

		Font font = new Font("digital-7", Font.PLAIN, FONT_SIZE);
		setFont(font);
		
		// Draw the clock components.
		
		drawSecondHand(g, center, r, second);
		g2d.setStroke(new BasicStroke(20f));
		g.setColor(Color.BLACK);
		g.drawOval(center.x - r, center.y - r, 2 * r,  2 * r);
		drawClockNumbers(g, font, center, r);
		drawTickMarks(g, center, r);
		drawMinuteHand(g, center, r, minute, second);
		drawHourHand(g, center, r, hour, minute, second);
		
		return;
	}

	
	private void drawClockNumbers(Graphics g, Font font, Point c, int r) {

		FontMetrics fm = getFontMetrics(font);
		int fa = fm.getMaxAscent();
		int fh = (fm.getMaxAscent() + fm.getMaxDescent()) / 2;
		int nr = (80 * r) / 100;
		for (int i = 0; i < 12; i++) {
			String ns = Integer.toString((i == 0) ? 12 : i);
			int nx = (int) ((Math.cos((i * nda) - sa) * nr) + c.x);
			int ny = (int) ((Math.sin((i * nda) - sa) * nr) + c.y);
			int w = fm.stringWidth(ns) / 2;

			g.drawString(ns, nx - w, ny + fa - fh);
		}
	}



	private void drawRadius(Graphics g, int x, int y, double angle, int minRadius, int maxRadius) {
		float sine = (float)Math.sin(angle);
		float cosine = (float)Math.cos(angle);
		int dxmin = (int)(minRadius * sine);
		int dymin = (int)(minRadius * cosine);
		int dxmax = (int)(maxRadius * sine);
		int dymax = (int)(maxRadius * cosine);

		Graphics2D g2d = (Graphics2D)g;

		g2d.setStroke(new BasicStroke(3f));
		g.setColor(Color.BLACK);
		g.drawLine(x+dxmin, y+dymin, x+dxmax, y+dymax);
	}

	private void drawTickMarks(Graphics g, Point c, int r) {
		int tr = (int) (height/34f);
		for (int sec = 0; sec < 60; sec++) {
			int ticStart;
			if (sec % 5 == 0) {
				ticStart = width/2-15;
			}else{
				ticStart = width/2-5;
			}
			drawRadius(g, c.x, c.y, sda*sec, ticStart-tr, width/2-tr);

		}
	}

	private void drawSecondHand(Graphics g, Point c, int r, int s) {
		int sr = (int) (r - (height * 0.12f));
		int sx = (int) ((Math.cos((s * sda) - sa) * sr) + c.x);
		int sy = (int) ((Math.sin((s * sda) - sa) * sr) + c.y);

		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(5f));
		g.setColor(new Color(1f, 0f, 0f, .5f));
		g.fillArc(c.x - r, c.y - r, 2 * r, 2 * r, 90, arcSecond[second]);
		g.drawLine(c.x, c.y, sx, sy);
	}


	private void drawMinuteHand(Graphics g, Point c, int r, int m, int s) {
		int ms = m * 60;
		int mr = (int) (.7 * r);
		int mx = (int) ((Math.cos(((ms + s) * mda) - sa) * mr) + c.x);
		int my = (int) ((Math.sin(((ms + s) * mda) - sa) * mr) + c.y);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(6f));
		g.setColor(Color.BLUE);
		g.drawArc(c.x - r, c.y - r, 2 * r, 2 * r, 90, arcMinute[minute]);
		g.drawLine(c.x, c.y - 1, mx, my);
		g.drawLine(c.x - 1, c.y, mx, my);
	}

	private void drawHourHand(Graphics g, Point c, int r, int h, int m, int s) {
		int ms = m * 60;
		int hs = h * 60 * 60;
		int hr = (int) (.5 * r);
		int hx = (int) ((Math.cos(((hs + ms + s) * hda) - sa) * hr) + c.x);
		int hy = (int) ((Math.sin(((hs + ms + s) * hda) - sa) * hr) + c.y);

		Graphics2D g2d = (Graphics2D)g;

		g2d.setStroke(new BasicStroke(6f));
		g.setColor(Color.BLACK);
		g.drawLine(c.x, c.y - 1, hx, hy);
		g.drawLine(c.x - 1, c.y, hx, hy);
	}


}
