/**
 * 
 */
package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

/**
 * 
 * @author Mullings
 *
 */
public class Sprite {
	
	public enum ImageType { SIMPLE, IMAGE, ANIMATION };
	
	/**
	 * The color of the rectangle displayed if an image or animation is not assigned
	 */
	private Color color;
	private Image image;	
	private Animation anim;
	/**
	 * How the sprite will be drawn
	 */
	private ImageType imageType;
	
	private float width;
	private float height;
	private Point position;

	public Sprite(float x, float y, float width, float height) {
		position = new Point(x, y);
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics graphics) {
		draw(graphics);
	}
	
	public void update() {
		
	}
	
	protected void draw(Graphics graphics) {
		switch (imageType) {
			case SIMPLE:
				graphics.setColor(color);
				graphics.drawRect(getX() - (width / 2), 
				                  getY() - (height / 2), width, height);
				break;
			case IMAGE:
			case ANIMATION:
				break;
		}
	}
	
	public void setImage(Color c) {
		imageType = ImageType.SIMPLE;
		this.color = c;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @return the x coordinate of the position
	 */
	public final float getX() {
		return getPosition().getX();
	}

	/**
	 * @return the y coordinate of the position
	 */
	public final float getY() {
		return getPosition().getY();
	}

	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void setPosition(float x, float y) {
		this.position.setLocation(x, y);
	}

	/**
	 * @param position the position to set
	 */
	public final void setPosition(Point position) {
		setPosition(position.getX(), position.getY());
	}

	/**
	 * @param x the x coordinate
	 */
	public final void setX(float x) {
		setPosition(x, getY());
	}

	/**
	 * @param y the y coordinate
	 */
	public final void setY(float y) {
		setPosition(getX(), y);
	}

}
