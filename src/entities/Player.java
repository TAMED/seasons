/**
 * 
 */
package entities;

import input.Controls;
import input.Controls.Action;
import items.Hookshot;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import util.Direction;
import anim.AnimationState;
import config.Config;

/**
 * @author Mullings
 *
 */
public class Player extends Entity {
	protected Hookshot hookshot;
	
	public Player(float width, float height) {
		this(width, height, 0);
	}
	
	public Player(float width, float height, float ground) {
		super(width, height, ground, Config.PLAYER_MAX_HP, true);
		addFeet(Config.PLAYER_MOVE_SPEED, Config.PLAYER_ACCELERATION, Config.PLAYER_JUMP_SPEED);
		setDensity(Config.PLAYER_DENSITY);
		this.getPhysicsFixtureDefs()[2].filter.maskBits |= Config.SALMON;
		anim.setDefaultAnimation(AnimationState.IDLE);
		
		try {
			setImage(new Image("assets/images/player/sprite.png"));
			Animation idle = new Animation(new SpriteSheet("assets/images/player/idle.png", 152, 152), 100);
			Animation running = new Animation(new SpriteSheet("assets/images/player/running.png", 152, 152), 1);
			Animation jumping = new Animation(new SpriteSheet("assets/images/player/jumping.png", 152, 152), 10);
			Animation falling = new Animation(new SpriteSheet("assets/images/player/falling.png", 152, 152), 10);
			Animation hooking = new Animation(new SpriteSheet("assets/images/player/hooking.png", 152, 152), 10);		
			Animation jumpTransition = new Animation(new SpriteSheet("assets/images/player/jump_transition.png", 152, 152), 50);
			Animation somersault = new Animation(new SpriteSheet("assets/images/player/somersault.png", 152, 152), 50);
			
//			// set transitions to no looping
//			jumpTransition.setLooping(false);
//			somersault.setLooping(false);
			
			anim.addAnimation(AnimationState.IDLE, idle);
			anim.addAnimation(AnimationState.RUN, running);
			anim.setFrames(AnimationState.RUN, 14, 80);
			anim.addAnimation(AnimationState.JUMP, jumpTransition);
			anim.addAnimation(AnimationState.RISE, jumping);
			anim.addAnimation(AnimationState.FALL, falling);
			anim.addAnimation(AnimationState.HOOKING, hooking);
			anim.addAnimation(AnimationState.SOMERSAULT, somersault);
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
		anim.reset();
	}

	public void render(Graphics graphics) {
		hookshot.render(graphics);
		hookshot.drawRange(graphics);
		draw(graphics);
	}

	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		movePlayer(gc,delta);
		hookshot.update(gc, delta, anim);
		obstacleCheck();
	}
	
	private void movePlayer(GameContainer gc, int delta) {
		boolean floating = checkWater(gc);
		boolean inAir = !isTouching(Direction.DOWN) && !checkWater(gc);
		float xVel = this.getPhysicsBody().getLinearVelocity().x;
		
		if (floating)
			anim.play(AnimationState.RUN);
		
		if(Controls.isKeyPressed(Action.RIGHT)) {
			run(Direction.RIGHT);
			if (floating) move(Config.PLAYER_WATER_MOVE_SPEED, 0);
			
			if (inAir && xVel < Config.PLAYER_MAX_AIR_SPEED) {
				if (xVel < 0) move(BRAKE_RATIO * Config.PLAYER_AIR_ACCELERATION, 0);
				else          move(Config.PLAYER_AIR_ACCELERATION, 0);
			}
		} else if(Controls.isKeyPressed(Action.LEFT)) {
			run(Direction.LEFT);
			if (floating) move(-Config.PLAYER_WATER_MOVE_SPEED, 0);
			
			if (inAir && xVel > -Config.PLAYER_MAX_AIR_SPEED) {
				if (xVel > 0) move(BRAKE_RATIO * -Config.PLAYER_AIR_ACCELERATION, 0);
				else          move(-Config.PLAYER_AIR_ACCELERATION, 0);
			}
		} else {
			run(Direction.DOWN);
		}
		
		if(Controls.isKeyPressed(Action.JUMP)) {
			jump(gc, delta);
		}
	}
	
	/**
	 * @return whether the player will damage enemies when coming into contact with them
	 */
	public boolean isAttacking() {
		return hookshot.isAttacking();
	}

	public Hookshot getHookshot() {
		return hookshot;
	}

}
