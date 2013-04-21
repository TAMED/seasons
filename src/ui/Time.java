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
		StringBuilder sb = new StringBuilder();
		sb.append("Time: ");
		sb.append(((millis - extra)/1000));
		sb.append(":");
		sb.append(((extra - (extra % 10))/10));
		return sb.toString();
	}
	
	public void set(int millis) {
		this.millis = millis;
	}
}
