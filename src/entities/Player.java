/**
 * 
 */
package entities;

import items.Hookshot;
import items.ItemBase;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import config.Config;

/**
 * @author Mullings
 *
 */
public class Player extends Entity {
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
		
		try {
			setImage(new Image("assets/images/shaman1.png"));
		} catch (Exception e) {
			e.printStackTrace();
			setImage(Color.white);
		}
		
		items[0] = new Hookshot(this);
	}

	public void render(Graphics graphics) {
		draw(graphics);
		items[0].render(graphics);
	}

	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		movePlayer(gc,delta);
		items[0].update(gc, delta);
	}
	
	private void movePlayer(GameContainer gc, int delta) {
		Input input = gc.getInput();
		float xvel = 0;
		float yvel = this.getPhysicsBody().getLinearVelocity().y;
		if(input.isKeyDown(Input.KEY_A)) {
			xvel -= Config.MOVE_VEL;
			setFacing(Direction.LEFT);
		}
		if(input.isKeyDown(Input.KEY_D)) {
			xvel += Config.MOVE_VEL;
			setFacing(Direction.RIGHT);
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
