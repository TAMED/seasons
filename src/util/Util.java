package util;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Util {

	public static Rectangle AABBToRectangle(AABB boundingBox) {
		float x = boundingBox.lowerBound.x;
		float y = boundingBox.lowerBound.y;
		float w = boundingBox.upperBound.x - x;
		float h = boundingBox.upperBound.y - y;
		
		return new Rectangle(x, y, w, h);
	}
	
	public static Color Color3fToColor(Color3f color) {
		return new Color(color.x, color.y, color.z, 1);
	}
	
	public static Vec2 PointToVec2(Point p) {
		return new Vec2(p.getX(), p.getY());
	}
	
	public static Point Vec2ToPoint(Vec2 v) {
		return new Point(v.x, v.y);
	}
	
	public static Vector2f PointToVector2f(Point p) {
		return new Vector2f(p.getX(), p.getY());
	}
	
	public static Point Vector2fToPoint(Vector2f v) {
		return new Point(v.x, v.y);
	}
	
	public static Vec2 Vector2fToVec2(Vector2f v) {
		return new Vec2(v.getX(), v.getY());
	}
	
	public static Vector2f Vec2ToVector2f(Vec2 v) {
		return new Vector2f(v.x, v.y);
	}
	
	public static PolygonShape getBoxShape(float halfWidth, float halfHeight) { return getBoxShape(halfWidth, halfHeight, new Vec2(), 0); }
	
	public static PolygonShape getBoxShape(float halfWidth, float halfHeight, Vec2 center, float angle) {
		PolygonShape box = new PolygonShape();
		box.setAsBox(halfWidth, halfHeight, center, angle);
		return box;
	}
	
	public static CircleShape getCircleShape(float radius) {
		CircleShape circ = new CircleShape();
		circ.setRadius(radius);
		return circ;
	}
}
