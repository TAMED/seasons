/**
 * 
 */
package entities;

import items.Hook;

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
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Point;

import states.LevelState;
import util.Direction;
import util.Util;
import anim.AnimationState;
import config.Config;
import entities.enemies.Bat;
import entities.enemies.Ent;

/**
 * Base class for in-game objects and agents. Handles physics updates
 * @author Mullings
 * 
 */
public abstract class Entity extends Sprite {
	/**
	 * If the difference between the width and height of the entity is less than
	 * this (in pixels), then the capsule shape is approximated with only two circles
	 */
	private static final float EPSILON = 5;
	/**
	 * How far down to offset the wheel, as a ratio.
	 */
	private static final float FOOT_OFFSET = 1.05f;
	/**
	 * How much more force to apply when trying to slow down
	 */
	protected static final float BRAKE_RATIO = 1.5f;
	
	private BodyDef physicsDef;
	private FixtureDef circleDef1;
	private FixtureDef circleDef2;
	private FixtureDef boxDef;
	private Body physicsBody;
	
	private boolean hasFeet;
	private BodyDef footDef;
	private FixtureDef footFixtureDef;
	private RevoluteJoint footJoint;
	
	private boolean hasSensors;
	private FixtureDef[] sensors = new FixtureDef[Direction.values().length];
	private PolygonShape[] sensorShapes;
	
	private float width;
	private float height;
	private float radius;
	
	public final int maxHp;
	private int hp;
	private boolean alive;
	private int jumpTimer = 500;
	
	private float runSpeed;
	private float acceleration;
	private float jmpSpeed;
	
	private Sound jmpSound; 

	public Entity(float width, float height, int maxHp, boolean hasSensors) {
		this(width, height, 0, maxHp, hasSensors);
	}

	public Entity(float width, float height, float ground, int maxHp, boolean hasSensors) {
		super(0, 0, width, height, ground);
		
		try {
			jmpSound = new Sound("assets/sounds/bear_jump.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.width = width;
		this.height = height;
		this.radius = Math.min(width, height) / 2;
		float hw = width / 2;  // half-width
		float hh = height / 2; // half-height
		
		this.hasSensors = hasSensors;
		
		physicsDef = new BodyDef();
		physicsDef.type = BodyType.DYNAMIC;
		physicsDef.fixedRotation = true;
		
		circleDef1 = new FixtureDef();
		circleDef1.density = Config.DEFAULT_DENSITY;
		circleDef1.friction = Config.DEFAULT_FRICTION;
		circleDef1.shape = Util.getCircleShape(radius / Config.PIXELS_PER_METER);
		((CircleShape) circleDef1.shape).m_p.set((-hw + radius) / Config.PIXELS_PER_METER,
		                                         (-hh + radius) / Config.PIXELS_PER_METER);
		
		circleDef2 = new FixtureDef();
		circleDef2.density = Config.DEFAULT_DENSITY;
		circleDef2.friction = Config.DEFAULT_FRICTION;
		circleDef2.shape = Util.getCircleShape(radius / Config.PIXELS_PER_METER);
		((CircleShape) circleDef2.shape).m_p.set((hw - radius) / Config.PIXELS_PER_METER,
		                                         (hh - radius) / Config.PIXELS_PER_METER);
		
		boxDef = new FixtureDef();
		boxDef.density = Config.DEFAULT_DENSITY;
		boxDef.friction = Config.DEFAULT_FRICTION;
		boxDef.filter.maskBits |= Config.WATER;
		boxDef.filter.maskBits |= Config.STEAM;
		if (height - width > EPSILON) {
			boxDef.shape = Util.getBoxShape(hw / Config.PIXELS_PER_METER,
		                             (hh - hw) / Config.PIXELS_PER_METER);
		} else if (width - height > EPSILON) {
			boxDef.shape = Util.getBoxShape(hh / Config.PIXELS_PER_METER,
			                         (hw - hh) / Config.PIXELS_PER_METER);
		} else { // just make an arbitrary box
			boxDef.shape = Util.getBoxShape(hw / 2 / Config.PIXELS_PER_METER,
			                                hh / 2 / Config.PIXELS_PER_METER);
		}
		
		if (hasSensors) {
			sensorShapes = new PolygonShape[Direction.values().length];
			// creates sensors on each side. Config has mapping of integers to TOP, BOTTOM, etc.
			sensorShapes[Direction.UP.ordinal()   ]  = Util.getBoxShape(width/2.2f/Config.PIXELS_PER_METER, .1f, new Vec2(0, -height/2/Config.PIXELS_PER_METER), 0);
			sensorShapes[Direction.DOWN.ordinal() ]  = Util.getBoxShape(width/2.2f/Config.PIXELS_PER_METER, .1f, new Vec2(0,  height/2/Config.PIXELS_PER_METER), 0);
			sensorShapes[Direction.LEFT.ordinal() ]  = Util.getBoxShape(.1f,height/2.2f/Config.PIXELS_PER_METER, new Vec2(-width/2/Config.PIXELS_PER_METER,  0), 0);
			sensorShapes[Direction.RIGHT.ordinal()]  = Util.getBoxShape(.1f,height/2.2f/Config.PIXELS_PER_METER, new Vec2( width/2/Config.PIXELS_PER_METER,  0), 0);
			sensorShapes[Direction.CENTER.ordinal()] = Util.getBoxShape(.1f, .1f, new Vec2(0,0), 0);
			for (int i = 0; i < sensors.length; i++) {
				sensors[i] = new FixtureDef();
				sensors[i].shape = sensorShapes[i];
				sensors[i].isSensor = true;
			}
		} else {
			sensorShapes = new PolygonShape[0];
		}
		
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.alive = true;
	}
	
	
	
	/* (non-Javadoc)
	 * @see entities.Sprite#update(org.newdawn.slick.GameContainer, int)
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		jumpTimer += delta;
		super.update(gc, delta);
		anim.update(this);
		this.waterUpdate(gc);
	}

	public void move(float xvel, float yvel) {
		if (xvel > 0) setFacing(Direction.RIGHT);
		if (xvel < 0) setFacing(Direction.LEFT);
		getPhysicsBody().applyLinearImpulse(new Vec2(xvel, yvel), getPhysicsBody().getWorldCenter());
	}
	
	public void moveForce(float xForce, float yForce) {
		getPhysicsBody().applyForce(new Vec2(xForce, yForce), getPhysicsBody().getWorldCenter());

	}
	
	public void addFeet(float runSpeed, float acceleration, float jmpSpeed) {
		this.hasFeet = true;
		this.runSpeed = runSpeed;
		this.acceleration = acceleration;
		this.jmpSpeed = jmpSpeed;
		
		footDef = new BodyDef();
		footDef.type = BodyType.DYNAMIC;
		footDef.fixedRotation = false;
		footFixtureDef = new FixtureDef();
		footFixtureDef.shape = Util.getCircleShape(radius / Config.PIXELS_PER_METER);
		footFixtureDef.density = Config.DEFAULT_DENSITY;
		footFixtureDef.friction = Config.DEFAULT_TRACTION;
	}
	
	public void setDensity(float density) {
		boxDef.density = density;
	}
	
	public void run(Direction dir) {
		if (hasFeet) {
			float torque = acceleration;
			float vel = 0;
			switch (dir) {
				case LEFT:
					if (getPhysicsBody().getLinearVelocity().x > 0)
						torque *= BRAKE_RATIO;
					vel = runSpeed;
					setFacing(Direction.LEFT);
					break;
				case RIGHT:
					if (getPhysicsBody().getLinearVelocity().x < 0)
						torque *= BRAKE_RATIO;
					vel = -runSpeed;
					setFacing(Direction.RIGHT);
					break;
			}
			footJoint.setMaxMotorTorque(torque);
			footJoint.setMotorSpeed(vel);
		}
	}
	
	public void fly(Direction dir) {
		float yVel = this.getPhysicsBody().getLinearVelocity().y;
		switch (dir) {
		case LEFT:
			this.getPhysicsBody().setLinearVelocity(new Vec2(-Config.FLY_VEL, yVel));
			setFacing(Direction.LEFT);
			break;
		case RIGHT:
			this.getPhysicsBody().setLinearVelocity(new Vec2(Config.FLY_VEL, yVel));
			setFacing(Direction.RIGHT);
			break;
		default:
			break;
		}
	}
	
	public void jump(GameContainer gc, int delta) {
		if (checkSteam(gc)) {
			return;
		}
		if (checkWater(gc) || (categoriesTouchingSensors()[Direction.DOWN.ordinal()] & Config.WATER) > 0) {
			if (jumpTimer >= 500 && (categoriesTouchingSensors()[Direction.UP.ordinal()] & Config.WATER) == 0){
				getPhysicsBody().applyLinearImpulse(new Vec2(0, -jmpSpeed), new Vec2(0, 0));
				jmpSound.play();
				anim.play(AnimationState.JUMP);
				jumpTimer = 0;
			}
		} else if (isTouching(Direction.DOWN) || LevelState.godMode) {
			getPhysicsBody().applyLinearImpulse(new Vec2(0, -jmpSpeed), getPhysicsBody().getWorldCenter());
			jmpSound.play();
			anim.play(AnimationState.JUMP);
		}
		
	}
	
	public void addToWorld(World world, float x, float y) {
		physicsDef.position.set(x / Config.PIXELS_PER_METER, y / Config.PIXELS_PER_METER);
		physicsBody = world.createBody(physicsDef);
		physicsBody.setUserData(this);

		Fixture circle1 = physicsBody.createFixture(circleDef1);
		circle1.setUserData(this);
		Fixture circle2 = physicsBody.createFixture(circleDef2);
		circle2.setUserData(this);
		Fixture box = physicsBody.createFixture(boxDef);
		box.setUserData(this);
		
		if (hasSensors) {
			for(int i = 0; i < sensors.length; i++){
				physicsBody.createFixture(sensors[i]).setUserData(Direction.values()[i]);
			}
		}

		if (hasFeet) {
			footDef.position.set(x / Config.PIXELS_PER_METER, (y + (height / 2) - (1 / FOOT_OFFSET) * radius) / Config.PIXELS_PER_METER);
			Body footBody = world.createBody(footDef);
			footBody.createFixture(footFixtureDef);
			RevoluteJointDef joint = new RevoluteJointDef();
			joint.initialize(footBody, physicsBody, footBody.getWorldCenter());
			joint.enableMotor = true;
			joint.maxMotorTorque = acceleration;
			footJoint = (RevoluteJoint) world.createJoint(joint);
		}
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
	public FixtureDef[] getPhysicsFixtureDefs() {
		return new FixtureDef[] {circleDef1, circleDef2, boxDef};
	}
	
	/**
	 * @return the width of the entity
	 */
	public float getBodyWidth() {
		return width;
	}
	
	/**
	 * @return the height of the entity
	 */
	public float getBodyHeight() {
		return height;
	}
	/**
	 * @return the entity's sensor defs
	 */
	public FixtureDef[] getSensorDefs() {
		return sensors;
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
		// TODO: this function will currently add the same body multiple times
		ArrayList<Body> list = new ArrayList<Body>();
		ContactEdge contactEdge = physicsBody.getContactList();
		
		while(contactEdge != null) {
			if(contactEdge.contact.isTouching()) {
				Body b = contactEdge.contact.getFixtureA().getBody();
				list.add(b);
			}
			contactEdge = contactEdge.next;
		}
		
		return list;
	}
	
	public int[] categoriesTouchingSensors() {
		int[] categories = new int[5];
		ContactEdge contactEdge = physicsBody.getContactList();
		
		while(contactEdge != null) {
			if(contactEdge.contact.isTouching()) {
				int category = contactEdge.contact.getFixtureA().m_filter.categoryBits;
				Object data = contactEdge.contact.getFixtureB().getUserData();
				if (data != null && data instanceof Direction) {
					categories[((Direction) data).ordinal()] |= category;
				}
			}
			contactEdge = contactEdge.next;
		}
		
		return categories;
	}
	
	/**
	 * Check if player is touching salmon
	 */
	public void obstacleCheck() {
		ContactEdge contactEdge = this.getPhysicsBody().getContactList();
		
		while(contactEdge != null) {
			if(contactEdge.contact.isTouching()) {
				int categoryA = contactEdge.contact.getFixtureA().m_filter.categoryBits;
				int categoryB = contactEdge.contact.getFixtureB().m_filter.categoryBits;
				Object fixB = contactEdge.contact.getFixtureB().getUserData();
				Object fixA = contactEdge.contact.getFixtureA().getUserData();
				if (categoryA == Config.SALMON && fixA instanceof Salmon) {
					salmonUpdate(fixA);
				}
				if (categoryB == Config.SALMON && fixB instanceof Salmon) {
					salmonUpdate(fixB);
				}
				if (categoryA == Config.MUSHROOM && fixA instanceof Mushroom && fixB instanceof Direction) {
					mushroomUpdate(fixA, fixB);
				}
				if (categoryB == Config.MUSHROOM && fixB instanceof Mushroom && fixA instanceof Direction) {
					mushroomUpdate(fixB, fixA);
				}
				
			}
			contactEdge = contactEdge.next;
		}
	}
	
	public boolean checkWater(GameContainer gc) {
		boolean center = (categoriesTouchingSensors()[Direction.CENTER.ordinal()] & Config.WATER) > 0;
		boolean top    = (categoriesTouchingSensors()[Direction.UP.ordinal()] & Config.WATER) > 0;
		return center || top;
	}
	
	public boolean checkSteam(GameContainer gc) {
		boolean center = (categoriesTouchingSensors()[Direction.CENTER.ordinal()] & Config.STEAM) > 0;
		boolean top    = (categoriesTouchingSensors()[Direction.UP.ordinal()] & Config.STEAM) > 0;
		return center || top;
	}
	
	public boolean checkHook(GameContainer gc, int delta, Player player) {
		ContactEdge contactEdge = physicsBody.getContactList();
		boolean hooked = false;
		while(contactEdge != null) {
			if(contactEdge.contact.isTouching()) {
				Object a = contactEdge.contact.getFixtureA().getUserData();
				Object b = contactEdge.contact.getFixtureB().getUserData();
				hooked = true;
				if (a instanceof Bat) {
					((Bat) a).hook(gc, delta, player);
					this.setPosition(((Bat) a).getPosition());
				}
				if (b instanceof Bat) {
					((Bat) b).hook(gc, delta, player);
					this.setPosition(((Bat) b).getPosition());
				}
			}
			contactEdge = contactEdge.next;
		}
		return hooked;
	}
	
	private void waterUpdate(GameContainer gc) {
		if (checkWater(gc)) {
			this.getPhysicsBody().setGravityScale(Config.WATER_GRAVITY_SCALE);
			this.getPhysicsBody().setLinearDamping(Config.WATER_DRAG);
		}
		
		else if (checkSteam(gc)) {
			this.getPhysicsBody().setGravityScale(Config.STEAM_GRAVITY_SCALE);
			this.getPhysicsBody().setLinearDamping(Config.STEAM_DRAG);
		}
		
		else {
			this.getPhysicsBody().setGravityScale(1);
			this.getPhysicsBody().setLinearDamping(0f);
		}
	}
	
	private void mushroomUpdate(Object preEntity, Object dir) {
		Mushroom entity = (Mushroom) preEntity;
		if(!entity.isDead()){
			if ((dir != null) && (dir instanceof Direction)) {
				entity.activate(this, (Direction) dir);
			}
		}
	}
	
	private void salmonUpdate(Object preEntity) {
		Salmon entity = (Salmon) preEntity;
		if(!entity.isDead()){
			entity.activate(this, null);
		}
	}
	
	private void entUpdate(Object preEntity) {
		Ent entity = (Ent) preEntity;
		entity.activate(this, null);
	}
	abstract public void reset();
	
	public boolean isStill() {
		if (Math.abs(this.getPhysicsBody().getLinearVelocity().x) < Config.VEL_EPSILON) return true;
		return false;
	}

}
