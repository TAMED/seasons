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
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
	private FixtureDef footFixture;

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
		
		PolygonShape footShape = new PolygonShape();
		footShape.setAsBox(width/2.2f/Config.PIXELS_PER_METER,.1f, new Vec2(0, height/2/Config.PIXELS_PER_METER), 0);
		footFixture = new FixtureDef();
		footFixture.shape = footShape;
		footFixture.isSensor = true;
	}
	
	@Override
	public abstract void render(Graphics graphics);

	@Override
	public abstract void update(GameContainer gc, int delta);
	
	public final void addToWorld(World world) {
		physicsBody = world.createBody(physicsDef);
		physicsBody.createFixture(physicsFixture);
		physicsBody.createFixture(footFixture).setUserData("foot");
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
	
	public final boolean touchingGround() {
		ContactEdge contact = physicsBody.getContactList();
		while(contact != null) {
			if(contact.contact.isTouching()) {
				if(contact.contact.getFixtureA().getUserData() == "foot"){
					return true;
				}
				if(contact.contact.getFixtureB().getUserData() == "foot"){
					return true;
				}
			}
			contact = contact.next;
		}
		return false;
	}

}
