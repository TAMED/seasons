/**
 * 
 */
package config;

import java.util.Queue;

import states.LevelState;

/**
 * @author Mullings
 *
 */
public enum Level {
	FOREST_1(Section.FOREST_CLIFF, Section.FOREST_VINES, Section.FOREST_HILLS, Section.FOREST_LONG);
	
	private Section[] sections;
	
	private Level(Section...sections) {
		this.sections = sections;
	}
	
	public void addToQueue() {
		addToQueue(LevelState.sectionQueue);
	}

	private void addToQueue(Queue<Section> sectionQueue) {
		for (Section s : sections) {
			sectionQueue.add(s);
		}
	}
}
