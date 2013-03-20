package items;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import entities.Entity;
import entities.Player;
import entities.Sprite;

public class Hookshot extends ItemBase {

	private static float HOOK_VEL = 10f;
	
	private enum HookState { IN, MOTION, OUT };

	private HookState state;
	private int aiming; // values 0, 1, 2, 3, 4 correspond to up, up-out, out, down-out, down
	
	private Hook hook;
	
	public Hookshot(Player player) {
		this.owner = player;
		aiming = 2;
	}

	@Override
	public void render(Graphics graphics) {
		graphics.setColor(Color.white);
		float x = owner.getPosition().getX();
		float y = owner.getPosition().getY();
		
		float diag = (float) Math.sqrt(2);
		float yDirection = owner.isRight ? 100 : -100;
		
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
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_UP) && (aiming != 0)) {
			aiming--;
		} else if (input.isKeyPressed(Input.KEY_DOWN) && (aiming != 4)) {
			aiming ++;
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			float x = owner.getPosition().getX();
			float y = owner.getPosition().getY();
			
			hook = new Hook(x, y, 10, 10);
			hook.addToWorld(owner.world);
			
			float right = (owner.isRight) ? 1 : -1;
			float diag = (float) Math.sqrt(2);
			
			if (aiming == 0) hook.getPhysicsBody().setLinearVelocity(new Vec2(0, -HOOK_VEL));
			if (aiming == 1) hook.getPhysicsBody().setLinearVelocity(new Vec2(HOOK_VEL*diag*right, -HOOK_VEL*diag));
			if (aiming == 2) hook.getPhysicsBody().setLinearVelocity(new Vec2(HOOK_VEL*right, 0));
			if (aiming == 3) hook.getPhysicsBody().setLinearVelocity(new Vec2(HOOK_VEL*diag*right, HOOK_VEL*diag));
			if (aiming == 4) hook.getPhysicsBody().setLinearVelocity(new Vec2(0, HOOK_VEL));
		}
	}
}
