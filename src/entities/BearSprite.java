package entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import anim.AnimationState;

public class BearSprite extends Sprite {

	public BearSprite(float x, float y, float width, float height) {
		super(x, y, width, height);
		Animation running;
		try {
			running = new Animation(new SpriteSheet("assets/images/player/running.png", 152, 152), 1);
			anim.addAnimation(AnimationState.BASIC, running);
			anim.setFrames(AnimationState.BASIC, 14, 60);
			anim.setDefaultAnimation(AnimationState.BASIC);
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}
