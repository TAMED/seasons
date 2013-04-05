package items;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.Entity;

public class Hook extends Entity {
	private static final float SIZE = 10;
	
	private Hookshot hookshot;
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Hook(float x, float y) {
		super(x, y, SIZE, SIZE, 0, 0, 1);
		setImage(Color.red);
	}

	@Override
	public void render(Graphics graphics) {
		draw(graphics);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		
	}

}
