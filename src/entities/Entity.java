/**
 * 
 */
package entities;

import java.util.ArrayList;
import java.util.EnumSet;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Point;

import util.Direction;
import util.Util;
import anim.AnimationState;
import config.Config;

/**
 * Base class for in-game objects and agents. Handles physics updates
 * @author Mullings
 * 
 */
public abstract class Entity extends Sprite {
	private PolygonShape physicsShape;
	private BodyDef physicsDef;
	private FixtureDef physicsFixtureDef;
	private Fixture physicsFixture;
	private Body physicsBody;
	private World physicsWorld;
	private FixtureDef[] sensors = new FixtureDef[Direction.values().length];
	private PolygonShape[] sensorShapes;
	private boolean hasSensors;
	
	private float runSpeed;
	private float jmpSpeed;
	
	public final int maxHp;
	private int hp;
	private boolean alive;

	public Entity(float x, float y, float width, float height, float runSpeed, float jmpSpeed, int maxHp, boolean hasSensors) {
		this(x, y, width, height, 0, runSpeed, jmpSpeed, maxHp, hasSensors);
	}

	public Entity(float x, float y, float width, float height, float ground, float runSpeed, float jmpSpeed, int maxHp, boolean hasSensors) {
		super(x, y, width, height, ground);
		this.hasSensors = hasSensors;
		physicsDef = new BodyDef();
		physicsDef.type = BodyType.DYNAMIC;
		physicsDef.fixedRotation = true;
		physicsDef.position.set(x / Config.PIXELS_PER_METER,
		                        y / Config.PIXELS_PER_METER);
		
		physicsShape = new PolygonShape();
		physicsShape.setAsBox(width / 2 / Config.PIXELS_PER_METER,
		                     height / 2 / Config.PIXELS_PER_METER);
		
		physicsFixtureDef = new FixtureDef();
		physicsFixtureDef.shape = physicsShape;
		physicsFixtureDef.density = Config.DEFAULT_DENSITY;
		physicsFixtureDef.friction = Config.DEFAULT_FRICTION;
		
		if (hasSensors) {
			sensorShapes = new PolygonShape[Direction.values().length];
			// creates sensors on each side. Config has mapping of integers to TOP, BOTTOM, etc.
			for (int i = 0; i < sensorShapes.length; i++) {
				sensorShapes[i] = new PolygonShape();
			}
			sensorShapes[Direction.UP.ordinal()   ].setAsBox(width/2.2f/Config.PIXELS_PER_METER,.1f, new Vec2(0, -height/2/Config.PIXELS_PER_METER), 0);
			sensorShapes[Direction.DOWN.ordinal() ].setAsBox(width/2.2f/Config.PIXELS_PER_METER,.1f, new Vec2(0, height/2/Config.PIXELS_PER_METER), 0);
			sensorShapes[Direction.LEFT.ordinal() ].setAsBox(.1f,height/2.2f/Config.PIXELS_PER_METER, new Vec2(-width/2/Config.PIXELS_PER_METER, 0), 0);
			sensorShapes[Direction.RIGHT.ordinal()].setAsBox(.1f,height/2.2f/Config.PIXELS_PER_METER, new Vec2(width/2/Config.PIXELS_PER_METER, 0), 0);
			for (int i = 0; i < sensors.length; i++) {
				sensors[i] = new FixtureDef();
				sensors[i].shape = sensorShapes[i];
				sensors[i].isSensor = true;
			}
		} else {
			sensorShapes = new PolygonShape[0];
		}
		this.runSpeed = runSpeed;
		this.jmpSpeed = jmpSpeed;
		
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.alive = true;
	}
	
	/* (non-Javadoc)
	 * @see entities.Sprite#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		anim.update(this);
	}

	public void moveLeft() {
		if (this.isTouching(Direction.DOWN)) {
			if (this.getFacing() == Direction.LEFT) {
				this.getPhysicsBody().applyForce(new Vec2(-runSpeed, 0), Util.PointToVec2(this.getPosition()));
			} else {
				this.getPhysicsBody().applyForce(new Vec2(-3*runSpeed, 0), Util.PointToVec2(this.getPosition()));
			}
		} else {
			this.getPhysicsBody().applyForce(new Vec2(-runSpeed, 0), Util.PointToVec2(this.getPosition()));
		}
		setFacing(Direction.LEFT);
		anim.play(AnimationState.RUN);
	}
	
	public void moveRight() {
		if (this.isTouching(Direction.DOWN)) {
			if (this.getFacing() == Direction.RIGHT) {
				this.getPhysicsBody().applyForce(new Vec2(runSpeed, 0), Util.PointToVec2(this.getPosition()));
			} else {
				this.getPhysicsBody().applyForce(new Vec2(3*runSpeed, 0), Util.PointToVec2(this.getPosition()));
			}
		} else {
			this.getPhysicsBody().applyForce(new Vec2(runSpeed, 0), Util.PointToVec2(this.getPosition()));
		}
		setFacing(Direction.RIGHT);
		anim.play(AnimationState.RUN);
	}
	
	public void jump() {
		if(this.isTouching(Direction.DOWN)) {
//			float xvel = this.getPhysicsBody().getLinearVelocity().x;
			this.getPhysicsBody().applyLinearImpulse(new Vec2(0, -jmpSpeed), new Vec2(0, 0));
		}
		anim.play(AnimationState.JUMP);
	}
	
	public void dampenVelocity(int delta) {
		Vec2 vel = this.getPhysicsBody().getLinearVelocity();
		this.getPhysicsBody().setLinearVelocity(new Vec2((float) (vel.x * Math.pow(Config.DRAG, delta/100f)), vel.y));
		if (this.isTouching(Direction.DOWN)) {
			anim.play(AnimationState.IDLE);
		}
	}
	
	public void addToWorld(World world) {
		physicsBody = world.createBody(physicsDef);
		physicsFixture = physicsBody.createFixture(physicsFixtureDef);
		physicsFixture.setUserData(this);
		if (hasSensors) {
			for(int i = 0; i < sensors.length; i++){
				physicsBody.createFixture(sensors[i]).setUserData(Direction.values()[i]);
			}
			physicsWorld = world;
		}
	}
	
	/**
	 * @return the entity's physics world
	 */
	public World getPhysicsWorld() {
		return physicsWorld;
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
		return physicsFixture;
	}
	
	/**
	 * @return the entity's physics fixture
	 */
	public FixtureDef getPhysicsFixtureDef() {
		return physicsFixtureDef;
	}
	
	/**
	 * @return the entity's linear velocity
	 */
	public float getVelocity() {
		return physicsBody.getLinearVelocity().length();
	}
	
	/**
	 * @return the entity's current hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param deal damage to the entity
	 */
	public void damage(int points) {
		this.hp = Math.max(0, hp - points);
	}

	/**
	 * @param deal 1 point of damage to the entity
	 */
	public void damage() {
		damage(1);
	}

	/**
	 * @param heal the entity
	 */
	public void heal(int points) {
		this.hp = Math.min(maxHp, hp + points);
	}

	/**
	 * @param heal the entity to full health
	 */
	public void heal() {
		heal(maxHp);
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void kill() {
		alive = false;
		physicsBody.setActive(false);
	}

	/**
	 * @return whether or not the given side of the entity is touching another body
	 */
	public final boolean isTouching(Direction side) {
		ContactEdge contactEdge = physicsBody.getContactList();
		
		while(contactEdge != null) {
			if(contactEdge.contact.isTouching()) {
				Object data = contactEdge.contact.getFixtureB().getUserData();
				if (data != null && side.equals(data)) {
					return true;					
				}
			}
			contactEdge = contactEdge.next;
		}
		
		return false;
	}
	
	/**
	 * @return a set containing the sides of the entity that are touching another both
	 */
	public final EnumSet<Direction> sidesTouching() {
		EnumSet<Direction> touching = EnumSet.noneOf(Direction.class);
		ContactEdge contactEdge = physicsBody.getContactList();
		
		while(contactEdge != null) {
			if(contactEdge.contact.isTouching()) {
				Object data = contactEdge.contact.getFixtureB().getUserData();
				if (data != null && data instanceof Direction) {
					touching.add((Direction) data);
				}
			}
			contactEdge = contactEdge.next;
		}
		
		return touching;
	}
	
	/**
	 * @return a list of bodies touching the object
	 */
	public final ArrayList<Body> bodiesTouching() {
		ArrayList<Body> list = new ArrayList<Body>();
		ContactEdge contactEdge = physicsBody.getContactList();
		
		while(contactEdge != null) {
//			Fixture fixtureA = contactEdge.contact.getFixtureA();
//			Fixture fixtureB = contactEdge.contact.getFixtureB();
			if(contactEdge.contact.isTouching()) {
				list.add(contactEdge.contact.getFixtureA().getBody());
			}
			contactEdge = contactEdge.next;
		}
		
		return list;
	}
	
	abstract public void reset();

}
