package analogeUhrPackage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ClockPanel extends JPanel implements ActionListener {
	int radiusOuter = 225;
	int indentClock = 3;
	int centralPoint = radiusOuter + indentClock;
	int radiusInner = 7;
	
	void drawClock(Graphics2D g2d) {  //draws the clock
		g2d.setColor(Color.BLACK);
		g2d.drawOval(indentClock, indentClock, radiusOuter * 2, radiusOuter * 2);
		g2d.fillOval(centralPoint - radiusInner, centralPoint - radiusInner, radiusInner * 2, radiusInner * 2);
		int j = 0;
		int h = 1;
		for(double i = 0; i < (2 * Math.PI) - (2 * Math.PI) / 60; i = i + ((2 * Math.PI) / 60)) {
			int x = centralPoint + (int) (long) Math.round(Math.sin(i) * 210);
			int y = centralPoint + (int) (long) Math.round(-Math.cos(i) * 210);
			if(j == 0) {
				g2d.drawString("12", x - 5, y + 4);
			} else if(j % 5 == 0) {
				g2d.drawString(Integer.toString(h), x - 2, y + 5);
				h++;
			} else {
				g2d.fillOval(x, y, 3, 3);
			}
			j++;
		}	
	}
	
	void drawHand(Graphics2D g2d, int width, double position, int length) {  //draws the clock hands
		g2d.setStroke(new BasicStroke(width));  //this needs Graphics2D
		g2d.drawLine(centralPoint, centralPoint, 
		centralPoint + (int) (long) Math.round(Math.sin(((2 * Math.PI) / 60) * position) * length), centralPoint + (int) (long) Math.round(-Math.cos(((2 * Math.PI) / 60) * position) * length));
	}
	
	@Override
	public void paintComponent(Graphics g) {  //draws everything, gets called when the panel is created, "when it is needed", repaint() clears the screen and calls this
		super.paintComponent(g);
		this.setBackground(new Color(255, 150, 32));
		Graphics2D g2d = (Graphics2D) g;
		time = ZonedDateTime.now(setTimeZone(zoneI));
		setTime();		
		drawClock(g2d);	
		drawHand(g2d, 5, hours, 100);
		drawHand(g2d, 3, minutes, 170);
		drawHand(g2d, 1, seconds, 195);
		drawExtras(g2d);		
	}
	
	void drawExtras(Graphics g2d) {  //draws the date, a digital clock and the displayed time-zone into the clock
		//date
		g2d.drawRect(centralPoint + (int) (long) Math.round(Math.sin(((2 * Math.PI) / 60) * 22.5) * 140) - 2, centralPoint + (int) (long) Math.round(-Math.cos(((2 * Math.PI) / 60) * 22.5) * 140) - 13, 17, 17);
		g2d.drawString(ZonedDateTime.now(setTimeZone(zoneI)).getDayOfMonth() + "",
				centralPoint + (int) (long) Math.round(Math.sin(((2 * Math.PI) / 60) * 22.5) * 140), centralPoint + (int) (long) Math.round(-Math.cos(((2 * Math.PI) / 60) * 22.5) * 140));
		//digital clock
		String sec = Integer.toString(ZonedDateTime.now(setTimeZone(zoneI)).getSecond());
		String min = Integer.toString(ZonedDateTime.now(setTimeZone(zoneI)).getMinute());
		String h = Integer.toString(ZonedDateTime.now(setTimeZone(zoneI)).getHour());
		if(ZonedDateTime.now(setTimeZone(zoneI)).getSecond() < 10) {
			sec = "0" + ZonedDateTime.now(setTimeZone(zoneI)).getSecond();
		}
		if(ZonedDateTime.now(setTimeZone(zoneI)).getMinute() < 10){
			min = "0" + ZonedDateTime.now(setTimeZone(zoneI)).getMinute();
		}
		if(ZonedDateTime.now(setTimeZone(zoneI)).getHour() < 10){
			h = "0" + ZonedDateTime.now(setTimeZone(zoneI)).getHour();
		}
		g2d.drawString(h + ":" + min + ":" + sec, centralPoint + (int) (long) Math.round(Math.sin(((2 * Math.PI) / 60) * 0) * 140) - 25, centralPoint + (int) (long) Math.round(-Math.cos(((2 * Math.PI) / 60) * 0) * 140));
		//timezone
		g2d.drawString(zoneS, centralPoint + (int) (long) Math.round(Math.sin(((2 * Math.PI) / 60) * 0) * 140) - indentZone, centralPoint + (int) (long) Math.round(-Math.cos(((2 * Math.PI) / 60) * 0) * 140) - 15);
	}
	
	
	
	//static so that it can be accessed by the Assembly class without creating a ClockPanel object
	static int zoneI = 1;
	static String zoneS;
	static int indentZone;
	
	static ZoneId setTimeZone(int zone) {  //sets the time-zone using zoneI, returns the ZoneId of the zone that is set
		switch(zone) {
			case 1:
				indentZone = 39;
				zoneS = "Europe/Berlin";
				return ZoneId.of(zoneS);
			case 2:
				indentZone = 43;
				zoneS = "Europe/Moscow";
				return ZoneId.of(zoneS);
			case 3:
				indentZone = 40;
				zoneS = "Asia/Shanghai";
				return ZoneId.of(zoneS);
			case 4:
				indentZone = 50;
				zoneS = "America/Phoenix";
				return ZoneId.of(zoneS);
			default:
				indentZone = 39;
				return ZoneId.of("Europe/Berlin");
		}
	}
	
	ZonedDateTime time;



	double hour;
	double minute;
	double second;
	
	//how many "steps" per second
	final private double HOURS_PASS = 5.0 / 3600.0;  //== 1 / 720
	double hours;
	
	final private double MINUTES_PASS = 1.0 / 60.0;		 
	double minutes;
	
	final private double SECONDS_PASS = 1;
	double seconds;
	
	void setTime() {  //sets the time according to the demanded time-zone, converts it so that it is usable as a value for the positioning of the clock hands
		hour = time.getHour() * 5;
		minute = time.getMinute() * 60;
		second = time.getSecond();
		hours = hour + (minute + second) * HOURS_PASS;
		minutes = minute * MINUTES_PASS + (second * MINUTES_PASS);
		seconds = second; // + 1
	}
	
	Timer timer = new Timer(999, this);  //sets a timer with an action listener that performs the action below every 999 milliseconds
	
	ClockPanel() {  //the constructor starts the timer and sets the size of the panel, paintComponents, which does basically everything in this program gets called automatically as described above
		timer.start();
		this.setPreferredSize(new Dimension(456, 456));  //?
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {  //modifies the position values of the clock hands, then repaints, calling paintComponent
		hours += HOURS_PASS;
		minutes += MINUTES_PASS;
		seconds += SECONDS_PASS;		
		repaint();
	}
}