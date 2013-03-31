package items;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.Player;

public abstract class ItemBase {
	
	protected Player owner;

	public ItemBase() {
		
	}

	public void update(GameContainer gc, int delta) {
		
	}

	public void render(Graphics graphics) {
		
	}
	
	/**
	 * @return whether the item can damage enemies
	 */
	public abstract boolean isAttacking();
}
