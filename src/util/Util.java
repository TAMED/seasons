package util;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

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
}
