/**
 * 
 */
package time;


/**
 * @author Mullings
 *
 */
public class Timer {
	private Time current;
	private Time last;
	private Time best;

	public Timer() {
		current = new Time();
		last = new Time();
		best = new Time();
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

	public Time getCurrentTime() {
		return current;
	}

	public Time getLastTime() {
		return last;
	}

	public Time getBestTime() {
		return best;
	}
}
