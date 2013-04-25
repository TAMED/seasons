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
import org.newdawn.slick.geom.Vector2f;

import states.LevelState;
import util.Direction;
import anim.AnimStateMachine;

/**
 * 
 * @author Mullings
 *
 */
public class Sprite {
	/**
	 * The color of the rectangle displayed if an image or animation is not assigned
	 */
	private Color color;
	private Image image;
	protected AnimStateMachine anim;
	
	private float width;
	private float height;
	private Point position;
	private Direction facing;
	private float ground;
	public Sprite(float x, float y, float width, float height) {
		this(x, y, width, height, 0f);
	}
	
	public Sprite(float x, float y, float width, float height, float ground) {
		position = new Point(x, y);
		anim = new AnimStateMachine();
		this.width = width;
		this.height = height;
		this.facing = Direction.RIGHT;
		this.ground = ground;
	}
	
	public void render(Graphics graphics) {
		draw(graphics);
	}
	
	public void update(GameContainer gc, int delta) {
		Animation currentAnim = anim.getCurrentAnimation();
		if (currentAnim != null) {
			currentAnim.update(delta);		
		}
	}
	
	protected void draw(Graphics graphics) {
		float hw = width / 2;
		float hh = height / 2;
		float x = getX();
		float y = getY();
		
		Animation currentAnim = anim.getCurrentAnimation();
		if (currentAnim != null || image != null) {
			Image img;
			if (currentAnim != null) {
				img = currentAnim.getCurrentFrame()
				           .getFlippedCopy(facing == Direction.RIGHT, false);
			} else {
				img = image.getFlippedCopy(facing == Direction.RIGHT, false);
			}
			img.draw(x - hh, y - hh, height + 2*ground, height + 2*ground);
		} else if (color != null) {
			graphics.setColor(color);
			graphics.drawRect(x - hw, y - hh, width, height);
			// marker to indicate direction of sprite
			int offset = (facing == Direction.RIGHT) ? 1 : -1;
			graphics.drawOval(x + offset * hw - 5, y - hh, 10, 10);
			// text to indicate current animation
			if (anim.getCurrentState() != null)
				graphics.drawString(anim.getCurrentState().toString(), x - hw, y - hh);
		}
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public void setImage(Image i) {
		this.image = i;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return the maximum dimension of the sprite
	 */
	public float getMaxDim() {
		return Math.max(width, height);
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @return the position on the sprite, relative to the screen
	 */
	public Point getScreenPosition() {
		Point cam = LevelState.getCamera().getPosition();
		return new Point(getX() - cam.getX(), getY() - cam.getY());
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
	 * @return the x center coordinate of the position
	 */
	public final float getCenterX() {
		return getPosition().getX() + getWidth()/2;
	}

	/**
	 * @return the y center coordinate of the position
	 */
	public final float getCenterY() {
		return getPosition().getY() + getHeight()/2;
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
	 * @param vec the position to set
	 */
	public final void setPosition(Vector2f vec) {
		setPosition(vec.x, vec.y);
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
