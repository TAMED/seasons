package items;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import util.Util;

import entities.Player;

public abstract class ItemBase {
	
	protected Player owner;
	
	protected Vec2 aim; // vector from the player to the aiming cursor
	private float aimLength; // sets distance of the aiming cursor

	public ItemBase() {
		aimLength = 100;
		aim = new Vec2();
	}

	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();
		
		Vec2 mouseCursor = new Vec2(input.getAbsoluteMouseX(), input.getAbsoluteMouseY());
		Vec2 playerLoc = Util.PointToVec2(owner.getScreenPosition());
		
		// This line finds the difference between the two vectors, then scales to the
		// aimLength times a unit vector
		aim = mouseCursor.sub(playerLoc).mul(aimLength / mouseCursor.sub(playerLoc).length());
	}

	public void render(Graphics graphics) {
		float size = 10; // until we have a cursor image we'll draw a box!
		graphics.setColor(Color.magenta);
		graphics.drawRect(owner.getPosition().getX() + aim.x - size/2, owner.getPosition().getY() + aim.y - size/2, size, size);
	}
	
	/**
	 * @return whether the item can damage enemies
	 */
	public abstract boolean isAttacking();
}
