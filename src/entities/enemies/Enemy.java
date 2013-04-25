/**
 * 
 */
package entities.enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

import ai.AI;
import entities.Entity;

/**
 * @author Mullings
 *
 */
public class Enemy extends Entity {
	private AI ai;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Enemy(float x, float y, float width, float height, float runSpeed, float jmpSpeed, int maxHp, AI ai) {
		this(x, y, width, height, 0, runSpeed, jmpSpeed, maxHp, ai);
	}
	
	public Enemy(float x, float y, float width, float height, float ground, float runSpeed, float jmpSpeed, int maxHp, AI ai) {
		super(width, height, ground, maxHp, true);
		this.ai = ai;
		setColor(Color.red);
		setPosition(x, y);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		ai.update(this, delta);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
