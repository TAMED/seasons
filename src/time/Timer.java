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
	private Time goal;

	public Timer() {
		current = new Time();
		last = new Time(Integer.MAX_VALUE);
		best = new Time(Integer.MAX_VALUE);
		goal = new Time();
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
	
	public Time getGoal() {
		return goal;
	}

	public void setGoal(Time goal) {
		this.goal = goal;
	}
}
