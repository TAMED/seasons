package entities.enemies;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import ai.FlyingGoomba;
import anim.AnimationState;
import config.Biome;
import config.Config;
import entities.Player;

public class Bat extends Enemy {
	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;
	private static final float GROUND = 0;
	private static final int RUNSPEED = 2;
	private static final int JMPSPEED = 2;
	private static final int MAXHP = 1;
	
	private boolean hooked = false;

	public Bat(float x, float y) {
		super(x, y, WIDTH, HEIGHT, GROUND, RUNSPEED, JMPSPEED, MAXHP, new FlyingGoomba());
		try {
			Animation a = (new Animation(new SpriteSheet("assets/images/enemies/bat/flying.png", 117, 108), 100));
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
		/*
		setAI(new Still());
		this.getPhysicsBodyDef().type = BodyType.STATIC;
		this.getPhysicsBody().setGravityScale(0);
		*/
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

