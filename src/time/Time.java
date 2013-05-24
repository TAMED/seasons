package time;

public class Time {

	private int millis;
	
	public Time() {
		millis = 0;
	}

	public Time(int millis) {
		this.millis = millis;
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
	
	public float getSeconds() {
		return millis/1000f;
	}
	
	public void set(Time t) {
		this.millis = t.getMillis();
	}

	public boolean fasterThan(Time t) {
		return millis < t.getMillis();
	}
	
	public String getTimeString() {
		if (millis == Integer.MAX_VALUE) return "N/A";
		return String.format("%.2f", millis / 1000f);
	}
}
