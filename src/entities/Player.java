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

import util.Direction;

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
		super(width, height, ground, Config.PLAYER_MAX_HP, true);
		addFeet(Config.PLAYER_MOVE_SPEED, Config.PLAYER_ACCELERATION, Config.PLAYER_JUMP_SPEED);
		
		try {
			setImage(new Image("assets/images/player/sprite.png"));
			Animation idle = new Animation(new SpriteSheet("assets/images/player/idle.png", 152, 152), 100);
			Animation running = new Animation(new SpriteSheet("assets/images/player/running.png", 152, 152), 1);
			setFrames(running, 14, 80);
			Animation jumping = new Animation(new SpriteSheet("assets/images/player/jumping.png", 152, 152), 10);
			Animation falling = new Animation(new SpriteSheet("assets/images/player/falling.png", 152, 152), 10);
			
			anim.addAnimation(AnimationState.IDLE, idle);
			anim.addAnimation(AnimationState.RUN, running);
			anim.addAnimation(AnimationState.JUMP, jumping);
			anim.addAnimation(AnimationState.FALL, falling);
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
		
		boolean floating = !isTouching(Direction.DOWN) || checkWater(gc);
		
		if(input.isKeyDown(Input.KEY_D)) {
			run(Direction.RIGHT);
			if (floating) move(Config.PLAYER_AIR_MOVE_SPEED, 0);
		} else if(input.isKeyDown(Input.KEY_A)) {
			run(Direction.LEFT);
			if (floating) move(-Config.PLAYER_AIR_MOVE_SPEED, 0);
		} else {
			run(Direction.DOWN);
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
