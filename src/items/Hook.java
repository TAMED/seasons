package items;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.Entity;

public class Hook extends Entity {

	private Hookshot hookshot;
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Hook(float x, float y, float width, float height) {
		super(x, y, width, height, 0, 0);
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
