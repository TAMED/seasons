/**
 * 
 */
package entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.newdawn.slick.geom.Point;

import config.Config;

/**
 * Base class for in-game objects and agents. Handles physics updates
 * @author Mullings
 * 
 */
public abstract class Entity extends Sprite {
	private PolygonShape physicsShape;
	private BodyDef physicsDef;
	private FixtureDef physicsFixture;
	private Body physicsBody;
	private World physicsWorld;
	private FixtureDef[] sensors = new FixtureDef[4];
	private PolygonShape[] sensorShapes = new PolygonShape[4];

	public Entity(float x, float y, float width, float height) {
		super(x, y, width, height);
		physicsDef = new BodyDef();
		physicsDef.type = BodyType.DYNAMIC;
		physicsDef.fixedRotation = true;
		physicsDef.position.set(x / Config.PIXELS_PER_METER,
		                        y / Config.PIXELS_PER_METER);
		
		physicsShape = new PolygonShape();
		physicsShape.setAsBox(width / 2 / Config.PIXELS_PER_METER,
		                     height / 2 / Config.PIXELS_PER_METER);
		
		physicsFixture = new FixtureDef();
		physicsFixture.shape = physicsShape;
		physicsFixture.density = Config.DEFAULT_DENSITY;
		physicsFixture.friction = Config.DEFAULT_FRICTION;
		
		// creates sensors on each side. Config has mapping of integers to TOP, BOTTOM, etc.
		for(int i = 0; i < sensorShapes.length; i++) {
			sensorShapes[i] = new PolygonShape();
		}
		sensorShapes[0].setAsBox(width/2.2f/Config.PIXELS_PER_METER,.1f, new Vec2(0, -height/2/Config.PIXELS_PER_METER), 0);
		sensorShapes[1].setAsBox(width/2.2f/Config.PIXELS_PER_METER,.1f, new Vec2(0, height/2/Config.PIXELS_PER_METER), 0);
		sensorShapes[2].setAsBox(.1f,height/2.2f/Config.PIXELS_PER_METER, new Vec2(-width/2/Config.PIXELS_PER_METER, 0), 0);
		sensorShapes[3].setAsBox(.1f,height/2.2f/Config.PIXELS_PER_METER, new Vec2(width/2/Config.PIXELS_PER_METER, 0), 0);
		for(int i = 0; i < sensors.length; i++){
			sensors[i] = new FixtureDef();
			sensors[i].shape = sensorShapes[i];
			sensors[i].isSensor = true;
		}
	}
	
	public final void addToWorld(World world) {
		physicsBody = world.createBody(physicsDef);
		physicsBody.createFixture(physicsFixture);
		for(int i = 0; i < sensors.length; i++){
			physicsBody.createFixture(sensors[i]).setUserData(new Integer(i));
		}
		physicsWorld = world;
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
	 * Iterates over sensors seeing which are intersecting
	 * @return array corresponding to TOP, BOTTOM, etc as defined in config
	 */
	public final boolean[] sensorsTouching() {
		boolean[] touchingSides = new boolean[sensors.length];
		for(int i = 0; i < sensors.length; i++) {
			touchingSides[i] = false;
		}
		ContactEdge contact = physicsBody.getContactList();
		while(contact != null) {
			if(contact.contact.isTouching()) {
				for(int i = 0; i < sensors.length; i++) {
					Integer data = (Integer) contact.contact.getFixtureA().getUserData();
					if(data != null && data.equals(new Integer(i))){
						touchingSides[i] = true;
					}
					data = (Integer) contact.contact.getFixtureB().getUserData();
					if(data != null && data.equals(new Integer(i))){
						touchingSides[i] = true;
					}
				}
			}
			contact = contact.next;
		}
		return touchingSides;
	}

}
