package items;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import config.Config;
import entities.Entity;

public class Hook extends Entity {
	private static final float SIZE = 10;
	
	private boolean attached;
//	private Joint anchor;
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Hook() {
		super(SIZE, SIZE, 0, 0, 1, false);
		setColor(Color.red);
		getPhysicsBodyDef().bullet = true;
		for (FixtureDef f : getPhysicsFixtureDefs()) {
			f.filter.categoryBits = Config.HOOKABLE;
			f.filter.maskBits = Config.HOOKABLE;
		}
		attached = false;
	}

	@Override
	public void render(Graphics graphics) {
		draw(graphics);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		ArrayList<Body> touching = bodiesTouching();
		if (touching.size() > 0) {
			System.out.println(touching.get(0).getUserData());
			System.out.println(getPhysicsBody().getUserData());
			attach(touching.get(0));
		}
	}
	
	/**
	 * Anchors the hook at its current location
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
