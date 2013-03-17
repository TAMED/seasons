package map;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import config.Config;

public class Box2DTile {
	
	private PolygonShape physicsShape;
	private BodyDef physicsDef;
	private FixtureDef physicsFixture;
	private Body physicsBody;
	
	Box2DTile(float x, float y, float width, float height) {
		physicsDef = new BodyDef();
		physicsDef.position.set(x*width / Config.PIXELS_PER_METER,
		                        y*height / Config.PIXELS_PER_METER);
		
		physicsShape = new PolygonShape();
		physicsShape.setAsBox(width / 2 / Config.PIXELS_PER_METER,
		                     height / 2 / Config.PIXELS_PER_METER);
		
		physicsFixture = new FixtureDef();
		physicsFixture.shape = physicsShape;
		physicsFixture.density = Config.DEFAULT_DENSITY;
		physicsFixture.friction = Config.DEFAULT_FRICTION;
	}
	
	public final void addToWorld(World world) {
		physicsBody = world.createBody(physicsDef);
		physicsBody.createFixture(physicsFixture);
	}

}
