/**
 * 
 */
package ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import entities.Sprite;

/**
 * @author Mullings
 *
 */
public class Timer extends Sprite {
	private String current;
	private String last;
	private String best;

	public Timer(float x, float y) {
		super(x, y, 0, 0);
	}

	/* (non-Javadoc)
	 * @see entities.Sprite#render(org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.white);
		graphics.drawString(current, getX(), getY());
		graphics.drawString(last,    getX(), getY() + 25);
		graphics.drawString(best,    getX(), getY() + 50);
	}
	
	public void updateTime(Time currentTime, Time lastTime, Time bestTime) {
		current = "Time: " + getTimeString(currentTime);
		last    = "Last: " + getTimeString(lastTime);
		best    = "Best: " + getTimeString(bestTime);
	}
	
	private String getTimeString(Time t) {
		if (t == null) return "N/A";
		return t.getTimeString();
	}

}
