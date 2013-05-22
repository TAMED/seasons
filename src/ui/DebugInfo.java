/**
 * 
 */
package ui;

import main.MainGame;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

import config.Config;
import entities.Player;
import entities.Sprite;

/**
 * @author Mullings
 *
 */
public class DebugInfo extends Sprite {

	public DebugInfo(float x, float y) {
		super(x, y, 0, 0);
	}

	/* (non-Javadoc)
	 * @see entities.Sprite#render(org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		Player p = MainGame.player;
		Point slickPos = p.getPosition();
		Vec2 box2dPos = p.getPhysicsBody().getPosition();
		Vec2 box2dVel = p.getPhysicsBody().getLinearVelocity();
		Vec2 slickVel = box2dVel.mul(Config.PIXELS_PER_METER);
		
		String pos = String.format("Position: %+08.2f, %+08.2f (%+08.2f, %+08.2f)", slickPos.getX(), slickPos.getY(), box2dPos.x, box2dPos.y);
		String vel = String.format("Velocity: %+08.2f, %+08.2f (%+08.2f, %+08.2f)", slickVel.x, slickVel.y, box2dVel.x, box2dVel.y);
		
		graphics.setColor(Color.white);
		graphics.drawString(pos, getX(), getY());
		graphics.drawString(vel,    getX(), getY() + 25);
	}

}
