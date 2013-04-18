package ui;

public class Time {

	private int millis;
	
	public Time() {
		millis = 0;
	}

	public void update(int delta) {
		millis += delta;
	}
	
	public void reset() {
		millis = 0;
	}
	
	public int getMillis() {
		return millis;
	}
	
	public String getTimeString() {
		int extra = millis % 1000;
		return "Time: " + ((millis - extra)/1000) + ":" + ((extra - (extra % 10))/10);
	}
	
	public void set(int millis) {
		this.millis = millis;
	}
}
