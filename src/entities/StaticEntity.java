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
import ui.Time;
import ui.Timer;
import util.Direction;
import util.Util;
import anim.AnimationState;
import config.Config;

public abstract class StaticEntity extends Sprite{

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
	

	private boolean dead = false;
	private Time timer;

	public StaticEntity(float x, float y, boolean isSensor, int category) throws SlickException {
		super(x, y, width, height);
		/*
		anima = new Animation(new SpriteSheet("assets/images/nonentities/salmon/spinning.png", 32, 32), 100);
		anim.addAnimation(AnimationState.BASIC, anima);
		anim.setDefaultAnimation(AnimationState.BASIC);
		*/
		float hw = width / 2;  // half-width
		float hh = height / 2; // half-height
				
		physicsDef = new BodyDef();
		physicsDef.type = BodyType.STATIC;

		boxDef = new FixtureDef();
		boxDef.isSensor = isSensor;
		boxDef.shape = Util.getBoxShape(hw / Config.PIXELS_PER_METER,
			                                hh / Config.PIXELS_PER_METER);
		boxDef.density = Config.DEFAULT_DENSITY;
		boxDef.friction = Config.DEFAULT_FRICTION;
		boxDef.filter.categoryBits = category;
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
		if (!isDead()){
			draw(g);
		}
	}
	
	public void addToWorld(World world, float x, float y) {
		physicsDef.position.set(x / Config.PIXELS_PER_METER, y / Config.PIXELS_PER_METER);
		physicsBody = world.createBody(physicsDef);
		physicsBody.setUserData(this);

		Fixture box = physicsBody.createFixture(boxDef);
		box.setUserData(this);
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
	
	public boolean isDead() {
		return this.dead;
	}



	public void setDead(boolean dead) {
		this.dead = dead;
	}



	public Time getTimer() {
		return timer;
	}



	public void setTimer(Time timer) {
		this.timer = timer;
	}




	public abstract void activate(Entity entity, Direction dir);
}
