/**
 * 
 */
package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
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
	public enum Direction { LEFT, RIGHT };
	
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
	private Direction facing;

	public Sprite(float x, float y, float width, float height) {
		position = new Point(x, y);
		this.width = width;
		this.height = height;
		this.facing = Direction.RIGHT;
	}
	
	public void render(Graphics graphics) {
		draw(graphics);
	}
	
	public void update(GameContainer gc, int delta) {
		if (imageType == ImageType.ANIMATION) anim.update(delta);		
	}
	
	protected void draw(Graphics graphics) {
		float hw = width / 2;
		float hh = height / 2;
		float x = getX();
		float y = getY();
		Image img;
		
		switch (imageType) {
			case SIMPLE:
				graphics.setColor(color);
				graphics.drawRect(x - hw, y - hh, width, height);
				// marker to indicate direction of sprite
				int offset = (facing == Direction.RIGHT) ? 1 : -1;
				graphics.drawOval(x + offset * hw - 5, y - hh, 10, 10);
				break;
			case IMAGE:
				img = image.getFlippedCopy(facing == Direction.RIGHT, false);
				img.draw(x - hh, y - hh, height, height);
				break;
			case ANIMATION:
				img = anim.getCurrentFrame().getFlippedCopy(facing == Direction.RIGHT, false);
				img.draw(x - hh, y - hh, height, height);
				break;
		}
	}
	
	public void setImage(Color c) {
		imageType = ImageType.SIMPLE;
		this.color = c;
	}
	
	public void setImage(Image i) {
		imageType = ImageType.IMAGE;
		this.image = i;
	}
	
	public void setImage(Animation a) {
		imageType = ImageType.ANIMATION;
		this.anim = a;
		this.anim.setAutoUpdate(false);
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

	/**
	 * @return the direction the sprite is facing
	 */
	public Direction getFacing() {
		return facing;
	}

	/**
	 * @param dir the direction the sprite should face
	 */
	public void setFacing(Direction dir) {
		this.facing = dir;
	}
}
