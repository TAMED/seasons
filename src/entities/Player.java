/**
 * 
 */
package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * @author Mullings
 *
 */
public class Player extends Entity {

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		setImage(Color.white);
	}

	/* (non-Javadoc)
	 * @see entities.Entity#render(org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(Graphics graphics) {
		draw(graphics);
	}

	/* (non-Javadoc)
	 * @see entities.Entity#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
