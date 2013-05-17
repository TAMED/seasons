package entities.enemies;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import config.Config;

import util.Direction;
import ai.FlyingGoomba;
import ai.Goomba;
import ai.Still;
import entities.Entity;
import entities.Player;

public class Bat extends Enemy {
	private static final float WIDTH = 64;
	private static final float HEIGHT = 64;
	private static final float GROUND = 6;
	private static final int RUNSPEED = 2;
	private static final int ACCELERATION = 2;
	private static final int JMPSPEED = 2;
	private static final int MAXHP = 1;
	
	private boolean hooked = false;

	
	
	public Bat(float x, float y) {
		super(x, y, WIDTH, HEIGHT, GROUND, RUNSPEED, JMPSPEED, MAXHP, new FlyingGoomba());
		try {
			setImage(new Image("assets/images/enemies/bat/sprite.png"));
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
		}
	}
	
	@Override
	public void addToWorld(World world, float x, float y) {
		super.addToWorld(world, x, y);
		this.getPhysicsBody().setGravityScale(0);
	}
	
	public void hook(GameContainer gc, int delta, Player player) {
		setAI(new Still());
		this.getPhysicsBodyDef().type = BodyType.STATIC;
		this.getPhysicsBody().setGravityScale(0);
		hooked = true;
	}
}

