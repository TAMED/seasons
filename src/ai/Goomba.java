/**
 * 
 */
package ai;

import util.Direction;
import entities.enemies.Enemy;

/**
 * @author Mullings
 *
 */
public class Goomba extends AI {
	private Direction walkDir;
	
	public Goomba() {
		walkDir = Direction.LEFT;
	}

	/* (non-Javadoc)
	 * @see ai.AI#update(Enemy, int)
	 */
	@Override
	public void update(Enemy enemy, int delta) {
		if (enemy.isTouching(walkDir)) {
			walkDir = walkDir.opposite();
		}
		if (walkDir == Direction.LEFT) enemy.moveLeft();
		if (walkDir == Direction.RIGHT) enemy.moveRight();
	}

}
