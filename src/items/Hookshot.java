package items;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import entities.Entity;
import entities.Player;
import entities.Sprite;
import entities.Sprite.Direction;

public class Hookshot extends ItemBase {

	private static float HOOK_VEL = 40f;
	private static float PULL_VEL_BUFFER = 7f;
	
	private enum HookState { IN, MOTION, OUT, PULL };

	private HookState state;
	private int aiming; // values 0, 1, 2, 3, 4 correspond to up, up-out, out, down-out, down
	
	private Hook hook;
	private WeldJointDef joint;
	
	public Hookshot(Player player) {
		this.owner = player;
		state = HookState.IN;
		aiming = 2;
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.white);
		float x = owner.getPosition().getX();
		float y = owner.getPosition().getY();
		
		float diag = (float) Math.sqrt(2);
		float yDirection = (owner.getFacing() == Direction.RIGHT) ? 100 : -100;
		
		if (aiming == 0) graphics.drawLine(x, y, x, y - 100);
		if (aiming == 1) graphics.drawLine(x, y, x + yDirection/diag, y - 100/diag);
		if (aiming == 2) graphics.drawLine(x, y, x + yDirection, y);
		if (aiming == 3) graphics.drawLine(x, y, x + yDirection/diag, y + 100/diag);
		if (aiming == 4) graphics.drawLine(x, y, x, y + 100);
		
		if (hook != null) {
			hook.render(graphics);
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) {		
		// Check for a collision of the hook with a wall
		if (state == HookState.MOTION) {
			for (boolean sensor : hook.sensorsTouching()) {
				if (sensor) {
					state = HookState.OUT;
					hook.getPhysicsBody().setActive(false);
				}
			}
		}
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_W) && (aiming != 0)) {
			aiming--;
		} else if (input.isKeyPressed(Input.KEY_S) && (aiming != 4)) {
			aiming ++;
		}
		
		if (input.isKeyPressed(Input.KEY_J)) {
			if (state == HookState.MOTION) {
				//owner.world.destroyBody(hook.getPhysicsBody());
				//spawnHook();
			} else if (state == HookState.IN) {
				spawnHook();
				state = HookState.MOTION;
			} else if (state == HookState.OUT) {
				state = HookState.PULL;
				float xDiff = hook.getX() - owner.getX();
				float yDiff = hook.getY() - owner.getY();
				owner.getPhysicsBody().setLinearVelocity(new Vec2(xDiff/PULL_VEL_BUFFER, yDiff/PULL_VEL_BUFFER));
			}
		}
		
		if (state == HookState.PULL) {
			float xDiff = hook.getX() - owner.getX();
			float yDiff = hook.getY() - owner.getY();
			
			// stop the hook pull if you are close enough to the hook OR the player stops moving (is blocked)
			if ((Math.sqrt(xDiff*xDiff + yDiff*yDiff) < 30) || (owner.getPhysicsBody().getLinearVelocity().length() < 2)) {
				owner.getPhysicsWorld().destroyBody(hook.getPhysicsBody());
				hook = null;
				state = HookState.IN;
			}
			
			owner.getPhysicsBody().setLinearVelocity(new Vec2(xDiff/PULL_VEL_BUFFER, yDiff/PULL_VEL_BUFFER));
		}
	}

	/**
	 * creates a new Hook object and projects it in the direction of the aim
	 */
	private void spawnHook() {
		float x = owner.getPosition().getX();
		float y = owner.getPosition().getY();

		float right = (owner.getFacing() == Direction.RIGHT) ? 1 : -1;
		float diag = (float) (1/Math.sqrt(2));
		
		float playerRad = 100;
		float hookSize = 10;
		
		if (aiming == 0) {
			hook = new Hook(x, y - playerRad, hookSize, hookSize);
			hook.addToWorld(owner.getPhysicsWorld());
			hook.getPhysicsBody().setLinearVelocity(new Vec2(0, -HOOK_VEL));
		} else if (aiming == 1) {
			hook = new Hook(x + playerRad*diag*right, y - playerRad*diag, hookSize, hookSize);
			hook.addToWorld(owner.getPhysicsWorld());
			hook.getPhysicsBody().setLinearVelocity(new Vec2(HOOK_VEL*diag*right, -HOOK_VEL*diag));
		} else if (aiming == 2) {
			hook = new Hook(x + playerRad*right, y, hookSize, hookSize);
			hook.addToWorld(owner.getPhysicsWorld());
			hook.getPhysicsBody().setLinearVelocity(new Vec2(HOOK_VEL*right, 0));
		} else if (aiming == 3) {
			hook = new Hook(x + playerRad*diag*right, y + playerRad*diag, hookSize, hookSize);
			hook.addToWorld(owner.getPhysicsWorld());
			hook.getPhysicsBody().setLinearVelocity(new Vec2(HOOK_VEL*diag*right, HOOK_VEL*diag));
		} else if (aiming == 4) {
			hook = new Hook(x, y + playerRad*diag, hookSize, hookSize);
			hook.addToWorld(owner.getPhysicsWorld());
			hook.getPhysicsBody().setLinearVelocity(new Vec2(0, HOOK_VEL));
		}
		
		hook.getPhysicsBody().getFixtureList().setFriction(10000);
	}
}
