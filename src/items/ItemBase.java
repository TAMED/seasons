package items;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import anim.AnimStateMachine;
import entities.Player;

public abstract class ItemBase {
	
	protected Player owner;

	public ItemBase(Player player) {
		owner = player;
	}

	//public abstract void update(GameContainer gc, int delta);
	public abstract void update(GameContainer gc, int delta, AnimStateMachine anim);

	public abstract void render(Graphics graphics);
	
	/**
	 * @return whether the item can damage enemies
	 */
	public abstract boolean isAttacking();
	
	public abstract void reset();
	
	public void drawRange(Graphics graphics) {
		
	}


}
