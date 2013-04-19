package ui;

public class Timer {

	private Time time;
	
	public Timer() {
		time = new Time();
	}

	public void update(int delta) {
		time.update(delta);
	}
	
	public void reset() {
		time.reset();
	}
	
	public String getTimeString() {
		return time.getTimeString();
	}
	
	public int getMillis() {
		return time.getMillis();
	}
	
	public Time getTime() {
		return time;
	}
	
	public void set(int millis) {
		time.set(millis);
	}
}
