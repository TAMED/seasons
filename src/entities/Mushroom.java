package entities;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import util.Direction;
import config.Config;

public class Mushroom extends StaticObstacle{
	int mushTimer = 0;
	float mushX = 30;
	float mushY = 30;
	
	
	public Mushroom(float x, float y) throws SlickException {
		super(x, y, false, Config.MUSHROOM);
	}
	
	/**
	 * @param bounce
	 */
	@Override
	public void activate(Entity entity, Direction dir) {
		if (mushTimer <= 0) {
			Vec2 vel = entity.getPhysicsBody().getLinearVelocity();
			switch(dir) {
			case LEFT:
				vel.x = mushX;
				vel.y = -mushY;
				break;
			case RIGHT:
				vel.x = -mushX;
				vel.y = -mushY;
				break;
			case UP:
				vel.y = mushY;
				break;
			case DOWN:
				vel.y = -mushY;
				break;
			}
			mushTimer = 100;
			entity.getPhysicsBody().setLinearVelocity(vel);
		}
	}
	
	@Override
	public void render(Graphics g) {
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		mushTimer -= delta;
		
	}
}
