package items;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import entities.Player;

public class Hookshot extends ItemBase {

	private enum HookState { IN, MOTION, OUT };

	private int aiming; // values 0, 1, 2, 3, 4 correspond to up, up-out, out, down-out, down
	
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
		float yDirection;
		if (owner.isRight) yDirection = 100;
		else yDirection = -100;
		
		if (aiming == 0) graphics.drawLine(x, y, x, y - 100);
		if (aiming == 1) graphics.drawLine(x, y, x + yDirection/diag, y - 100/diag);
		if (aiming == 2) graphics.drawLine(x, y, x + yDirection, y);
		if (aiming == 3) graphics.drawLine(x, y, x + yDirection/diag, y + 100/diag);
		if (aiming == 4) graphics.drawLine(x, y, x, y + 100);
			
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_UP) && (aiming != 0)) {
			aiming--;
		} else if (input.isKeyPressed(Input.KEY_DOWN) && (aiming != 4)) {
			aiming ++;
		}
	}
}
