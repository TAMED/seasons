/**
 * 
 */
package entities.enemies;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import config.Biome;
import config.Config;

import util.Direction;
import ai.FlyingGoomba;
import ai.Goomba;
import anim.AnimationState;
import entities.Entity;
import entities.Player;

/**
 * @author Mullings
 *
 */
public class Ent extends Enemy {
	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;
	private static final float GROUND = 6;
	private static final int RUNSPEED = 2;
	private static final int JMPSPEED = 2;
	private static final int MAXHP = 1;
	
	private boolean hooked = false;
	
	public Ent(float x, float y) {
		super(x, y, WIDTH, HEIGHT, GROUND, RUNSPEED, JMPSPEED, MAXHP, new FlyingGoomba());
		addFeet(RUNSPEED, 1, JMPSPEED);
		try {
			Animation a = (new Animation(new SpriteSheet("assets/images/enemies/ent/running.png", 103, 92), 100));
			anim.addAnimation(AnimationState.BASIC, a);
			anim.setDefaultAnimation(AnimationState.BASIC);
			anim.setFrames(AnimationState.BASIC, 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < this.getPhysicsFixtureDefs().length; i++) {
			this.getPhysicsFixtureDefs()[i].filter.maskBits = Config.HOOKABLE;
			this.getPhysicsFixtureDefs()[i].filter.categoryBits = Config.HOOKABLE;
			this.getPhysicsFixtureDefs()[i].isSensor = true;
		}
	}

	public void update(GameContainer gc, int delta, Player player) {
		super.update(gc, delta);
		this.getPhysicsBody().setGravityScale(0);
		if (hooked) {
			if(player.getHookshot().isIn()) {
				this.getPhysicsBodyDef().type = BodyType.DYNAMIC;
				this.getPhysicsBody().setGravityScale(0);
				setAI(new FlyingGoomba(getFacing()));
				hooked = false;
			}
			else {
				player.getHookshot().getHook().setPosition(this.getPosition());
			}
		}
	}
	
	@Override
	public void addToWorld(World world, float x, float y) {
		super.addToWorld(world, x, y);
		this.getPhysicsBody().setGravityScale(0);
	}
	
	public void hook(GameContainer gc, int delta, Player player) {
		hooked = true;
	}
	
	@Override
	public void render(Graphics g, Biome biome) {
		g.setColor(biome.getColor());
		float offset = (float) (.1*Config.PIXELS_PER_METER);
		g.drawRect(getX() - getWidth()/2-offset, getY() - getHeight()/2-offset, getWidth()+2*offset, getHeight()+2*offset);
		super.render(g);
	}

}
