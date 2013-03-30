/**
 * 
 */
package entities;

import items.*;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import config.Config;

/**
 * @author Mullings
 *
 */
public class Player extends Entity {
	public World world;
	private int jumpTimeout = 0;
	private ItemBase[] items = new ItemBase[2];

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param game 
	 */
	public Player(float x, float y, float width, float height, World gameWorld) {
		super(x, y, width, height);
		setImage(Color.white);
		items[0] = new Hookshot(this);
		world = gameWorld;
	}

	/* (non-Javadoc)
	 * @see entities.Entity#render(org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		draw(graphics);
		items[0].render(graphics);
	}

	/* (non-Javadoc)
	 * @see entities.Entity#update()
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		movePlayer(gc,delta);
		items[0].update(gc, delta);
	}
	
	private void movePlayer(GameContainer gc, int delta) {
		Input input = gc.getInput();
		float xvel = 0;
		float yvel = this.getPhysicsBody().getLinearVelocity().y;
		if(input.isKeyDown(Input.KEY_D)) {
			xvel += Config.MOVE_VEL;
			isRight = true;
		}
		if(input.isKeyDown(Input.KEY_A)) {
			xvel -= Config.MOVE_VEL;
			isRight = false;
		}
		if(this.sensorsTouching()[Config.BOTTOM] && jumpTimeout <= 0) {
			if(input.isKeyDown(Input.KEY_SPACE)) {
				yvel = -Config.JUMP_VEL;
				jumpTimeout = Config.JUMP_TIMER;
			}
		}
		if(jumpTimeout > 0) jumpTimeout--;
		this.getPhysicsBody().setLinearVelocity(new Vec2(xvel,yvel));
	}
}
