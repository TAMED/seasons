package items;

import input.Controls;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.joints.WeldJointDef;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import util.Util;
import entities.Player;

public class Hookshot extends ItemBase {

	private static final float PULL_VEL_BUFFER = 7f;
	// How far away from the player the hook starts when it is shot
	private static final float STARTING_DIST = 100;
	private static final float STARTING_VEL = 100;
	// additional tolerance for deciding when to complete grapple
	private static final float EPSILON = 2;
	
	private enum HookState { IN, MOTION, OUT, PULL };

	private HookState state;
	
	private Hook hook;
	private WeldJointDef joint;
	
	public Hookshot(Player player) {
		super(player);
		state = HookState.IN;
	}

	@Override
	public void render(Graphics graphics) {
		// draw hook
		switch (state) {
			case MOTION: case OUT: case PULL:
				hook.render(graphics);
				break;
		}
		
		// draw tether
		switch (state) {
			case OUT: case PULL:
				graphics.setColor(Color.white);
				graphics.drawLine(owner.getX(), owner.getY(), hook.getX(), hook.getY());
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			switch (state) {
				case IN:
					spawnHook();
					state = HookState.MOTION;
					break;
				case OUT:
					state = HookState.PULL;
					break;
			}
		}
		
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			switch (state) {
				case OUT: case PULL:
					hook.getPhysicsBody().getWorld().destroyBody(hook.getPhysicsBody());
					hook = null;
					state = HookState.IN;
					break;
			}
		}
		
		switch (state) {
			case MOTION:
				// check for collision with a wall
				if (!hook.sidesTouching().isEmpty()) {
					state = HookState.OUT;
					hook.getPhysicsBody().setActive(false);
				}
				break;
			case PULL:
				Vector2f diff = new Vector2f(hook.getX() - owner.getX(), hook.getY() - owner.getY());
				
				// stop pulling the hook if you are close enough to the hook OR the player stops moving (is blocked)
				if ((diff.length() < owner.getMaxDim() / 2 + EPSILON) || (owner.getVelocity() < 2)) {
					removeHook();
					state = HookState.IN;
				}
				Vector2f vel = diff.copy().scale(1/PULL_VEL_BUFFER);
				owner.getPhysicsBody().setLinearVelocity(Util.Vector2fToVec2(vel));
				break;
		}
	}

	/**
	 * Creates a new Hook object and projects it in the direction of the aim
	 */
	private void spawnHook() {
		float x = owner.getPosition().getX();
		float y = owner.getPosition().getY();
		Vector2f aim = new Vector2f(Controls.getAimAngle(owner));
		Vector2f start = aim.copy().scale(STARTING_DIST);
		
		hook = new Hook(x + start.x, y + start.y);
		hook.addToWorld(owner.getPhysicsWorld());
		hook.getPhysicsBody().setLinearVelocity(Util.Vector2fToVec2(aim.copy().scale(STARTING_VEL)));
		
		hook.getPhysicsBody().getFixtureList().setFriction(10000);
	}

	/**
	 * Removes the Hook object
	 */
	private void removeHook() {
		owner.getPhysicsWorld().destroyBody(hook.getPhysicsBody());
		hook = null;
	}

	@Override
	public boolean isAttacking() {
		return state == HookState.PULL;
	}
}
