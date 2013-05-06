package entities;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import ui.Time;
import util.Direction;
import util.Util;
import anim.AnimationState;
import config.Config;


public class Salmon extends StaticEntity{
	
	public Salmon(float x, float y) throws SlickException {
		super(x, y, true, Config.SALMON);
		Animation a = (new Animation(new SpriteSheet("assets/images/nonentities/salmon/spinning.png", 32, 32), 100));
		anim.addAnimation(AnimationState.BASIC, a);
		anim.setDefaultAnimation(AnimationState.BASIC);
	}
	
	/**
	 * @param eat fish
	 */
	@Override
	public void activate(Entity entity, Direction dir) {
		this.setDead(true);
		getPhysicsBody().setActive(false);
		getTimer().update(Config.SALMON_TIME);
	}
	
	public void addToWorld(World world, float x, float y, Time timer) {
		super.addToWorld(world, x, y);
		this.setTimer(timer);
	}

}
