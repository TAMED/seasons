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
	FOREST_OLD(Section.FOREST_CLIFF,
			   Section.FOREST_VINES,
			   Section.FOREST_HILLS,
			   Section.FOREST_LONG),
	FOREST_NEW(Section.FOREST_SEC1,
			   Section.FOREST_SEC2,
			   Section.FOREST_SEC3,
			   Section.FOREST_SEC4,
			   Section.FOREST_SEC5,
			   Section.FOREST_SEC6),
	LAKE(Section.LAKE_UNDERWATER);
	
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
