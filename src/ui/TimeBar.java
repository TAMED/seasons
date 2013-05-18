package ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import time.Time;
import time.Timer;

public class TimeBar {
	private static final int X = 100;
	private static final int Y = 100;

	public TimeBar() {

	}

	public void render(Graphics graphics, Timer timer) {
		graphics.setColor(Color.white);
		graphics.drawString("Time: " + getTimeString(timer.getCurrentTime()), X, Y);
		graphics.drawString("Last: " + getTimeString(timer.getLastTime()),    X, Y + 25);
		graphics.drawString("Best: " + getTimeString(timer.getBestTime()),    X, Y + 50);
	}
	
	private String getTimeString(Time t) {
		if (t == null) return "N/A";
		return t.getTimeString();
	}

}
