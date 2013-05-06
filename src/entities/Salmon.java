package entities;

import java.util.ArrayList;
import java.util.EnumSet;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import states.LevelState;
import ui.Timer;
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
	
	public void addToWorld(World world, float x, float y, Timer timer) {
		super.addToWorld(world, x, y);
		this.setTimer(timer);
	}

}
