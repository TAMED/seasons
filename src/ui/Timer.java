package ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Timer {

	private int millis;
	
	public Timer() {
		millis = 0;
	}

	public void update(int delta) {
		millis += delta;
	}
	
	public void reset() {
		millis = 0;
	}
	
	public String getTime() {
		int extra = millis % 1000;
		return "Time: " + ((millis - extra)/1000) + ":" + ((extra - (extra % 10))/10);
	}
}
