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

public class Salmon extends Sprite{

	/**
	 * If the difference between the width and height of the entity is less than
	 * this (in pixels), then the capsule shape is approximated with only two circles
	 */
	private static final float EPSILON = 5;
	
	private BodyDef physicsDef;
	private FixtureDef boxDef;
	private Body physicsBody;

	
	private static final float width = Config.TILE_WIDTH;
	private static final float height = Config.TILE_HEIGHT;
	
	private Animation anim;
	private boolean eaten = false;
	private Timer timer;

	public Salmon(float x, float y) throws SlickException {
		super(x, y, width, height);
		anim = new Animation(new SpriteSheet("assets/images/nonentities/salmon/spinning.png", 32, 32), 100);
		float hw = width / 2;  // half-width
		float hh = height / 2; // half-height
				
		physicsDef = new BodyDef();
		physicsDef.type = BodyType.STATIC;

		boxDef = new FixtureDef();
		boxDef.isSensor = true;
		boxDef.shape = Util.getBoxShape(hw / 2 / Config.PIXELS_PER_METER,
			                                hh / 2 / Config.PIXELS_PER_METER);
		boxDef.filter.categoryBits = Config.SALMON;
	}
	
	
	
	/* (non-Javadoc)
	 * @see entities.Sprite#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
	}
	
	@Override
	public void render(Graphics g) {
		if (!eaten){
			anim.draw(this.getX(), this.getY());
		}
	}
	
	public void addToWorld(World world, float x, float y, Timer timer) {
		physicsDef.position.set(x / Config.PIXELS_PER_METER, y / Config.PIXELS_PER_METER);
		physicsBody = world.createBody(physicsDef);
		physicsBody.setUserData(this);

		Fixture box = physicsBody.createFixture(boxDef);
		box.setUserData(this);
		this.timer = timer;
	}
	
	/**
	 * @return the entity's physics world
	 */
	public World getPhysicsWorld() {
		if (physicsBody != null) return physicsBody.getWorld();
		return null;
	}

	@Override
	public final Point getPosition() {
		if (physicsBody == null) return super.getPosition();
		Vec2 v = physicsBody.getPosition();
		return new Point(v.x * Config.PIXELS_PER_METER,
		                 v.y * Config.PIXELS_PER_METER);
	}
	
	@Override
	public final void setPosition(float x, float y) {
		if (physicsBody == null) {
			super.setPosition(x, y);
			return;
		}
		physicsBody.setTransform(new Vec2(x / Config.PIXELS_PER_METER,
		                                  y / Config.PIXELS_PER_METER), 0);
	}
	
	public final Body getPhysicsBody() {
		return physicsBody;
	}
	
	public final BodyDef getPhysicsBodyDef() {
		return physicsDef;
	}
	
	/**
	 * @return the entity's physics fixture
	 */
	public Fixture getPhysicsFixture() {
		if (physicsBody != null) return physicsBody.getFixtureList();
		return null;
	}
	
	/**
	 * @return the entity's physics fixture
	 */
	public FixtureDef getPhysicsFixtureDef() {
		return boxDef;
	}
	
	/**
	 * @param eat fish
	 */
	public void eat() {
		this.eaten = true;
		physicsBody.setActive(false);
		timer.update(Config.SALMON_TIME);
	}

	public boolean isEaten() {
		return this.eaten;
	}
}
