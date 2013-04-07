package map;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import util.Corner;

import config.Config;

public class Box2DTile {
	
	private PolygonShape physicsShape;
	private BodyDef physicsDef;
	private FixtureDef physicsFixture;
	private Body physicsBody;
	private Vec2 center;
	private Vec2[] corners = new Vec2[4];
	
	Box2DTile(float x, float y, float width, float height) {
		center = new Vec2((x+.5f)*width / Config.PIXELS_PER_METER,
                (y+.5f)*height / Config.PIXELS_PER_METER);
		corners[Corner.TOPLEFT.ordinal()] = new Vec2((x)*width / Config.PIXELS_PER_METER,
                (y)*height / Config.PIXELS_PER_METER);
		corners[Corner.TOPRIGHT.ordinal()] = new Vec2((x+1)*width / Config.PIXELS_PER_METER,
                (y)*height / Config.PIXELS_PER_METER);
		corners[Corner.BOTTOMLEFT.ordinal()] = new Vec2((x)*width / Config.PIXELS_PER_METER,
                (y+1f)*height / Config.PIXELS_PER_METER);
		corners[Corner.BOTTOMRIGHT.ordinal()] = new Vec2((x+1)*width / Config.PIXELS_PER_METER,
                (y+1)*height / Config.PIXELS_PER_METER);
		
		physicsDef = new BodyDef();
		physicsDef.position.set(center);
		
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
