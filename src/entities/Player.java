/**
 * 
 */
package entities;

import items.Hookshot;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SpriteSheet;

import anim.AnimationState;
import config.Config;

/**
 * @author Mullings
 *
 */
public class Player extends Entity {
	Hookshot hookshot;
	public Player(float width, float height) {
		this(width, height, 0);
	}
	
	public Player(float width, float height, float ground) {
		super(0, 0, width, height, ground, Config.PLAYER_MOVE_SPEED, Config.PLAYER_JUMP_SPEED, Config.PLAYER_MAX_HP, true);
		
		try {
			setImage(new Image("assets/images/player/sprite.png"));
			anim.addAnimation(AnimationState.IDLE, new Animation(new SpriteSheet("assets/images/player/idle.png", 152, 152), 100));
			Animation running = new Animation(new SpriteSheet("assets/images/player/running.png", 152, 152), 1);
			setFrames(running, 14, 80);
			anim.addAnimation(AnimationState.RUN, running);
		} catch (Exception e) {
			e.printStackTrace();
			setColor(Color.white);
		}
		
		getPhysicsBodyDef().allowSleep = false;
		
		hookshot = new Hookshot(this);
	}
	
	@Override
	public void reset() {
		this.heal();
		hookshot.reset();
	}

	public void render(Graphics graphics) {
		draw(graphics);
		hookshot.render(graphics);
		hookshot.drawRange(graphics);
	}

	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		movePlayer(gc,delta);
		hookshot.update(gc, delta);
	}
	
	private void movePlayer(GameContainer gc, int delta) {
		Input input = gc.getInput();
		
		if(input.isKeyDown(Input.KEY_D)) {
			moveRight();
		} else if(input.isKeyDown(Input.KEY_A)) {
			moveLeft();
		} else {
			if (!hookshot.isPulling()) {
				dampenVelocity(delta);
			}
		}
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			jump(gc, delta);
		}
	}
	
	/**
	 * @return whether the player will damage enemies when coming into contact with them
	 */
	public boolean isAttacking() {
		return hookshot.isAttacking();
	}
	
	private void setFrames(Animation anim, int numFrames, int durPerFrame) {
		for (int i = 0; i < numFrames; i++) {
			anim.setDuration(i, durPerFrame);
		}
		for (int i = numFrames; i < anim.getFrameCount(); i++) {
			anim.setDuration(i, 0);
		}
	}
}
