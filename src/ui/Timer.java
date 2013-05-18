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
	private static final int X = 100;
	private static final int Y = 100;
	
	private Time current;
	private Time last;
	private Time best;

	public Timer() {
		super(X, Y, 0, 0);
		current = new Time();
		last = new Time();
		best = new Time();
	}

	/* (non-Javadoc)
	 * @see entities.Sprite#render(org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.white);
		graphics.drawString("Time: " + getTimeString(current), getX(), getY());
		graphics.drawString("Last: " + getTimeString(last),    getX(), getY() + 25);
		graphics.drawString("Best: " + getTimeString(best),    getX(), getY() + 50);
	}
	
	public void update(int delta) {
		current.update(delta);
	}
	
	public void reset() {
		current.reset();
	}
	
	public void updateRecords() {
		last.set(current);
		if (current.fasterThan(best)) {
			best.set(current);
		}
	}
	
	private String getTimeString(Time t) {
		if (t == null) return "N/A";
		return t.getTimeString();
	}

	public Time getCurrentTime() {
		return current;
	}
}
