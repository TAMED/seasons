package entities;

import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import sounds.SoundEffect;
import time.Time;
import ui.TimeBar;
import util.Direction;
import anim.AnimationState;
import config.Config;


public class Salmon extends StaticObstacle{
	
	public SoundEffect salmonSound;
	public static TimeBar timerBar;
	
	public Salmon(float x, float y) throws SlickException {
		super(x, y, true, Config.SALMON);
		Animation a = (new Animation(new SpriteSheet("assets/images/nonentities/salmon/spinning.png", 32, 32), 100));
		anim.addAnimation(AnimationState.BASIC, a);
		anim.setDefaultAnimation(AnimationState.BASIC);
		salmonSound = new SoundEffect("assets/sounds/Salmon_Sound.wav");
	}
	
	/**
	 * @param eat fish
	 */
	@Override
	public void activate(Entity entity, Direction dir) {
		this.setDead(true);
		getPhysicsBody().setActive(false);
		getTimer().update(Config.SALMON_TIME);
		salmonSound.play();
		timerBar.gotSalmon();
	}
	
	public void addToWorld(World world, float x, float y, Time timer) {
		super.addToWorld(world, x, y, timer);
		this.setTimer(timer);
	}

}
