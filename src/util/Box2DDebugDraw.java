/**
 * 
 */
package util;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;

import config.Config;

/**
 * @author Mullings
 *
 */
public class Box2DDebugDraw extends DebugDraw {
	
	private Graphics graphics;

	/**
	 * @param viewport
	 */
	public Box2DDebugDraw() {
		super(new OBBViewportTransform());
	}
	
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawCircle(org.jbox2d.common.Vec2, float, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawCircle(Vec2 center, float radius, Color3f color) {
		graphics.setColor(Util.Color3fToColor(color));
		graphics.draw(new Circle(center.x * Config.PIXELS_PER_METER,
		                         center.y * Config.PIXELS_PER_METER,
		                         radius * Config.PIXELS_PER_METER));

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawPoint(org.jbox2d.common.Vec2, float, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
		graphics.setColor(Util.Color3fToColor(argColor));
		graphics.draw(new Point(argPoint.x * Config.PIXELS_PER_METER,
		                        argPoint.y * Config.PIXELS_PER_METER));
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawSegment(org.jbox2d.common.Vec2, org.jbox2d.common.Vec2, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
		graphics.setColor(Util.Color3fToColor(color));
		graphics.drawLine(p1.x * Config.PIXELS_PER_METER, p1.y * Config.PIXELS_PER_METER,
		                  p2.x * Config.PIXELS_PER_METER, p2.y * Config.PIXELS_PER_METER);
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawSolidCircle(org.jbox2d.common.Vec2, float, org.jbox2d.common.Vec2, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
		graphics.setColor(Util.Color3fToColor(color));
		graphics.fill(new Circle(center.x * Config.PIXELS_PER_METER,
		                         center.y * Config.PIXELS_PER_METER,
		                         radius * Config.PIXELS_PER_METER));
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawSolidPolygon(org.jbox2d.common.Vec2[], int, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		float[] v = new float[vertexCount * 2];
		for (int i = 0; i < vertexCount; i++) {
			v[2*i+0] = vertices[i].x * Config.PIXELS_PER_METER;
			v[2*i+1] = vertices[i].y * Config.PIXELS_PER_METER;
		}
		graphics.setColor(Util.Color3fToColor(color));
		graphics.fill(new Polygon(v));
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawString(float, float, java.lang.String, org.jbox2d.common.Color3f)
	 */
	@Override
	public void drawString(float x, float y, String s, Color3f color) {
		graphics.setColor(Util.Color3fToColor(color));
		graphics.drawString(s, x * Config.PIXELS_PER_METER, y * Config.PIXELS_PER_METER);
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.DebugDraw#drawTransform(org.jbox2d.common.Transform)
	 */
	@Override
	public void drawTransform(Transform xf) {
		// TODO Auto-generated method stub

	}

}
