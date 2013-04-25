package items;

import input.Controls;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import util.Util;

import config.Config;
import entities.Entity;
import entities.Player;

public class Hook extends Entity {
	private static final float SIZE = 30;
	
	private boolean attached;
	private Player owner;
//	private Joint anchor;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Hook(float x, float y, Player owner) {
		super(x, y, SIZE, SIZE, 0, 0, 1, false);
		setColor(Color.red);
		getPhysicsBodyDef().bullet = true;
		getPhysicsFixtureDef().filter.categoryBits = Config.HOOKABLE;
		getPhysicsFixtureDef().filter.maskBits = Config.HOOKABLE;
		attached = false;
		try {
			setImage(new Image("assets/images/nonentities/hookshot/hook/sprite.png"));
		} catch (Exception e) {
			e.printStackTrace();
			setColor(Color.white);
		}		
		
		this.owner = owner;
	}

	@Override
	public void render(Graphics graphics) {
		float x = getScreenPosition().getX() - owner.getScreenPosition().getX();
		float y = getScreenPosition().getY() - owner.getScreenPosition().getY();

		Vector2f myLoc = Util.PointToVector2f(getScreenPosition());
		Vector2f p = Util.PointToVector2f(owner.getScreenPosition());
		Vector2f aim = myLoc.sub(p);
		
//		setRotation((float) Controls.getAimAngle(owner) + 90);
		setRotation((float) aim.getTheta() + 90);
		draw(graphics);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		ArrayList<Body> touching = bodiesTouching();
		if (touching.size() > 0) {
			attach(touching.get(0));
		}
	}
	
	/**
	 * Attaches the hook to the specified body
	 * @param b a body
	 */
	private void attach(Body b) {
		this.getPhysicsBody().setType(BodyType.STATIC);
		attached = true;
	}

	/**
	 * @param detach the hook, if it is connected to a wall
	 */
	public void detach() {
		this.attached = false;
	}

	/**
	 * @return if the hook is attached to a wall
	 */
	public boolean isAttached() {
		return attached;
	}
	
	@Override
	public void reset() {
		detach();
	}

}
