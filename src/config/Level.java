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
	FOREST(Section.FOREST_1,
			Section.FOREST_2,
			Section.FOREST_2A,
			Section.FOREST_3,
			Section.FOREST_4,
			Section.FOREST_5,
			Section.FOREST_6,
			Section.FOREST_7,
			Section.FOREST_8),
	LAKE(Section.LAKE_1);
//	FOREST_PLAINS(Section.FOREST_PLAINS);
	
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
