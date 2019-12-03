package clock.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class SBBClockPanel extends ClockPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8773216136260877307L;

	private final Color redFlange = new Color(184,24,15);
	private final Color redNeedle = new Color(178,26,23);
	private final Color grayFlange = new Color(166,166,174);
	private final Color minutesColor = Color.black;
	private final Color backGround = Color.white;

	private final double minuteStep = 2*Math.PI/60;
	private final double tenMinuteStep = 2*Math.PI/12;

	/**
	 * Overrides the superclass method by drawing an analog clock in the panel.
	 * @param g GUI in which the clock is drawn.
	 */
	protected void paintComponent(Graphics g){
		int width = getWidth();
		int height = getHeight();
		int border = 10;

		Point c = new Point(width / 2, height / 2);
		int r = (Math.min(width, height) / 2) - border;

		// Clear background.
		g.setColor(backGround);
		g.fillRect(0, 0, width, height);

		//Draw the clock components
		drawClockDesign(g,c,r);
		drawHourHand(g,c,r,this.hour,0,0);
		drawMinuteHand(g,c,r,this.minute,0);
		drawSecondHand(g,c,r,this.second);
	}

	/**
	 * Draws the design of the clock.
	 * @param g the graphic element.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 */
	private void drawClockDesign(Graphics g, Point c, int r){
		//draw the outer red circle
		g.setColor(redFlange);
		g.fillOval(c.x-r,c.y-r,2*r,2*r);

		//draw the inner gray circle
		g.setColor(grayFlange);
		double ratioGrayFlange = 0.4/4.6;
		int grayFlangeRadius = (int)(r-ratioGrayFlange*r);
		g.fillOval(c.x-grayFlangeRadius,c.y-grayFlangeRadius,2*grayFlangeRadius,2*grayFlangeRadius);

		//draw the inner white circle
		g.setColor(backGround);
		double ratioWhiteFlange = 0.6/4.6;
		int whiteFlangeRadius = (int)(r-ratioWhiteFlange*r);
		g.fillOval(c.x-whiteFlangeRadius,c.y-whiteFlangeRadius,2*whiteFlangeRadius,2*whiteFlangeRadius);

		//Draw the minute's rectangles
		g.setColor(minutesColor);
		double ratioBeginningMinutes = 0.9/4.6;
		double ratioLengthBigMinutes = 1/4.6;
		double ratioWidthBigMinutes = 0.2/4.6;
		double ratioLengthLittleMinutes = 0.4/4.6;
		double ratioWidthLittleMinutes = 0.1/4.6;
		int positionRadius = (int)(r-ratioBeginningMinutes*r);
		int lengthBigMinute = (int)(r*ratioLengthBigMinutes);
		int widthBigMinute = (int)(r*ratioWidthBigMinutes);
		int lengthLittleMinute = (int)(r*ratioLengthLittleMinutes);
		int widthLittleMinute = (int)(r*ratioWidthLittleMinutes);
		Graphics2D g2d = (Graphics2D)g;

		int minRadius, maxRadius;
		for(int sec = 0; sec < 60; sec++) {

			if(sec % 5 == 0) {
				g2d.setStroke(new BasicStroke(widthBigMinute));
				minRadius = positionRadius;
				maxRadius = positionRadius-lengthBigMinute;
			}else {
				g2d.setStroke(new BasicStroke(widthLittleMinute));
				minRadius = positionRadius;
				maxRadius = positionRadius - lengthLittleMinute;
			}
			drawTickMarks(g2d, c.x, c.y, minuteStep*sec, minRadius, maxRadius);
		}

	}

	/**
	 * Draws the clock's second hand.
	 * @param g GUI which contains the representation of the analog clock.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 * @param s seconds affected to the clock.
	 */
	protected void drawSecondHand(Graphics g,Point c,int r, int s){
		//draw the inner little red circle,
		g.setColor(redNeedle);
		double ratioLittleCircle = 0.15/4.6;
		int littleCircleRadius = (int)(ratioLittleCircle*r);
		g.fillOval(c.x-littleCircleRadius,c.y-littleCircleRadius,2*littleCircleRadius,2*littleCircleRadius);


		double ratioWidthSeconds = 0.2/4.6;
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke((int)(r*ratioWidthSeconds)));
		int d = (int)(2.9/4.6*r);
		int d2 = (int)(1.2/4.6*r);
		int radiusCircle = (int)(0.55/4.6*r);
		double angle = s*minuteStep;
		int xEnd = (int)(c.x+Math.cos(angle-Math.PI/2)*d);
		int yEnd = (int)(c.y+Math.sin(angle-Math.PI/2)*d);
		int xEndPosition = (int)(c.x+Math.cos(angle-Math.PI/2)*d-radiusCircle/2);
		int yEndPosition = (int)(c.y+Math.sin(angle-Math.PI/2)*d-radiusCircle/2);

		g.drawLine((int)(c.x+Math.cos(angle+Math.PI/2)*d2),(int)(c.y+Math.sin(angle+Math.PI/2)*d2),xEnd,yEnd);
		g.fillOval(xEndPosition,yEndPosition,radiusCircle,radiusCircle);
	}

	/**
	 * Draws the clock's minute hand.
	 * @param g GUI which contains the representation of the analog clock.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 * @param m minutes affected to the clock.
	 * @param s seconds affected to the clock.
	 */
	protected void drawMinuteHand(Graphics g, Point c, int r, int m, int s){

		g.setColor(minutesColor);
		double ratioWidthMinutes = 0.4/4.6;
		int widthMinute = (int)(r*ratioWidthMinutes);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(widthMinute));
		int d2 = (int)(0.9/4.6*r);
		int d1 = (int)(3.5/4.6*r);
		double angle = m*minuteStep;
		int xEndBeginn = (int)(c.x+Math.cos(angle-Math.PI/2)*d1);
		int yEndBeginn = (int)(c.y+Math.sin(angle-Math.PI/2)*d1);
		int xEnd = (int)(c.x+Math.cos(angle+Math.PI/2)*d2);
		int yEnd = (int)(c.y+Math.sin(angle+Math.PI/2)*d2);
		g.drawLine(xEnd,yEnd,xEndBeginn,yEndBeginn);
	}

	/**
	 * Draws the clock's hour hand.
	 * @param g GUI which contains the representation of the analog clock.
	 * @param c center of the analog clock.
	 * @param r radius of the analog clock.
	 * @param h hours affected to the clock
	 * @param m minutes affected to the clock.
	 * @param s seconds affected to the clock.
	 */
	protected void drawHourHand(Graphics g, Point c, int r, int h, int m, int s){
		g.setColor(minutesColor);
		double ratioWidthHour = 0.6/4.6;
		int widthHour = (int)(r*ratioWidthHour);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(widthHour));

		int d2 = (int)(2.1/4.6*r);
		int d1 = (int)(0.8/4.6*r);
		double angle = h*tenMinuteStep;
		int xEndBeginn = (int)(c.x+Math.cos(angle-Math.PI/2)*d2);
		int yEndBeginn = (int)(c.y+Math.sin(angle-Math.PI/2)*d2);
		int xEnd = (int)(c.x+Math.cos(angle+Math.PI/2)*d1);
		int yEnd = (int)(c.y+Math.sin(angle+Math.PI/2)*d1);
		g.drawLine(xEnd,yEnd,xEndBeginn,yEndBeginn);
	}

}
