package design.patterns.observers.clock.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;

public class DigitalClockPanel extends ClockPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4491752044278062022L;



	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		int posX = width / 2 - (int) (width / 2.6f);
		int posY = height / 2 + (height / 10);

		setBackground(Color.BLACK);
		//using the font.
		final int currFontSize = (int) (width / 5.2f);
		setFont(new Font("digital-7", Font.PLAIN, currFontSize));
		Color foreGroundColor = new Color(0, 255, 255);
		setForeground(foreGroundColor);
		g.drawString(getTime(), posX, posY);
	}

	private String getTime() {
		Calendar cal = Calendar.getInstance();
		int AM_PM= cal.get(Calendar.AM_PM);
		String Am_Pm="";
		if(AM_PM==1){

			Am_Pm="PM";
		}
		else{
			Am_Pm="AM";
		}
		String time = "";
		time += ((hour < 10) ? "0" : "") + hour + ":";
		time += ((minute < 10) ? "0" : "") + minute + ":";
		time += ((second < 10) ? "0" : "") + second;
		time += " " + Am_Pm;

		return time;
	}
}
