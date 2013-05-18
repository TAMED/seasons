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
	private String goal;

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
	
	public void updateTime(Time currentTime, Time lastTime, Time bestTime, Time goalTime) {
		current = "Time: " + getTimeString(currentTime);
		last    = "Last: " + getTimeString(lastTime);
		best    = "Best: " + getTimeString(bestTime);
		goal    = "Goal: " + getTimeString(goalTime);
	}
	
	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	private String getTimeString(Time t) {
		if (t == null) return "N/A";
		return t.getTimeString();
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getBest() {
		return best;
	}

	public void setBest(String best) {
		this.best = best;
	}
}
