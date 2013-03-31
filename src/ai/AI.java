/**
 * 
 */
package ai;

import entities.enemies.Enemy;

/**
 * @author Mullings
 *
 */
public abstract class AI {
	
	public abstract void update(Enemy enemy, int delta);
	
}
