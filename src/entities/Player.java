/**
 * 
 */
package entities;

import items.Hookshot;
import items.ItemBase;

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
	private ItemBase[] items = new ItemBase[2];

	public Player(float width, float height) {
		super(0, 0, width, height, Config.PLAYER_MOVE_SPEED, Config.PLAYER_JUMP_SPEED, Config.PLAYER_MAX_HP);
		
		try {
			setImage(new Image("assets/images/player.png"));
		} catch (Exception e) {
			e.printStackTrace();
			setImage(Color.white);
		}
		
		getPhysicsBodyDef().allowSleep = false;
		
		items[0] = new Hookshot(this);
	}
	
	@Override
	public void reset() {
		this.heal();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				this.items[i].reset();
			}
		}
	}

	public void render(Graphics graphics) {
		draw(graphics);
		items[0].render(graphics);
		items[0].drawRange(graphics);
	}

	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		movePlayer(gc,delta);
		items[0].update(gc, delta);
	}
	
	private void movePlayer(GameContainer gc, int delta) {
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_D)) {
			moveRight();
		} else if(input.isKeyDown(Input.KEY_A)) {
			moveLeft();
		} else {
			dampenVelocity(delta);
		}
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			jump();
		}
	}
	
	/**
	 * @return whether the player will damage enemies when coming into contact with them
	 */
	public boolean isAttacking() {
		for (ItemBase i : items) {
			if (i != null && i.isAttacking()) return true;
		}
		return false;
	}
}
