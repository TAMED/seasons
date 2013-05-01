package items;


import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

import util.Util;
import anim.AnimationState;
import entities.Player;
import entities.Sprite;


public class Chain extends Sprite {
	private Player player;
	private Hook hook;
	
	
	public Chain(float x, float y, float length, float height, Player player, Hook hook) {
		super(x, y, length, height);
		try {
			setImage(new Image("assets/images/nonentities/hookshot/chain/sprite.png"));
			Animation animation = new Animation(new SpriteSheet("assets/images/nonentities/hookshot/chain/animation.png", 558, 32), 10);
			anim.addAnimation(AnimationState.BASIC, animation);
			anim.setFrames(AnimationState.BASIC, 8, 50);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.player = player;
		this.hook = hook;

		anim.play(AnimationState.BASIC);
	}
	
	@Override
	public void render(Graphics graphics) {

		// custom draw method copied from Sprite.draw to deal with scaling and subsectioning
		Vec2 dist = Util.PointToVec2(getHookAttPoint()).sub(Util.PointToVec2(getPlayerAttPoint()));
	
		
		Vec2 halfDist = new Vec2((getPlayerAttPoint().getX() + getHookAttPoint().getX())/2,
								 (getPlayerAttPoint().getY() + getHookAttPoint().getY())/2);
		
		float length = dist.length();
		
		Animation currentAnim = anim.getCurrentAnimation();
		
		Image img = currentAnim.getCurrentFrame();

		Image subImg = img.getSubImage(0,0,(int)length, img.getHeight());
		subImg = subImg.getScaledCopy((int)length - 20, (int)drawHeight);
		subImg.setRotation(getAngle());
		subImg.drawCentered(halfDist.x, halfDist.y );
	}

	private Point getPlayerAttPoint() {
		return player.getPosition();
	}
	
	private Point getHookAttPoint() {
		return hook.getPosition();
	}
	
	public float getAngle() {
		Vector2f myLoc = Util.PointToVector2f(getHookAttPoint());
		Vector2f p = Util.PointToVector2f(getPlayerAttPoint());
		Vector2f aim = myLoc.sub(p);
		return (float) aim.getTheta();
	}




}
