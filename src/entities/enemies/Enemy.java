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
		super(x, y, width, height, runSpeed, jmpSpeed, maxHp);
		this.ai = ai;
		setImage(Color.red);
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
