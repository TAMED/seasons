package ai;

import org.jbox2d.common.Vec2;

import entities.enemies.Enemy;

public class Still extends AI{
	
	public Still() {
	}

	/* (non-Javadoc)
	 * @see ai.AI#update(Enemy, int)
	 */
	@Override
	public void update(Enemy enemy, int delta) {
		enemy.getPhysicsBody().setLinearVelocity(new Vec2(0,0));
	}

}
