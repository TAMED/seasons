/**
 * 
 */
package input;

import java.util.EnumSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import util.Util;
import entities.Player;


/**
 * @author Mullings
 *
 */
public class Controls {
	public enum Action { UP, DOWN, LEFT, RIGHT, JUMP, FIRE, PULL, RELEASE,
	                     PAUSE, RESET, FULLSCREEN,
	                     DEBUG, SKIP, GOD_MODE, SLOW_DOWN }
	
	private static Set<Action> cache;
	private static float mouseX;
	private static float mouseY;

	public static void update(GameContainer gc) {
		Input input = gc.getInput();
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		
		cache = EnumSet.noneOf(Action.class);
		if (input.isKeyDown(Input.KEY_W)) cache.add(Action.UP);
		if (input.isKeyDown(Input.KEY_S)) cache.add(Action.DOWN);
		if (input.isKeyDown(Input.KEY_A)) cache.add(Action.LEFT);
		if (input.isKeyDown(Input.KEY_D)) cache.add(Action.RIGHT);
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			cache.add(Action.JUMP);
			cache.add(Action.RELEASE);
		}
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			cache.add(Action.FIRE);
			cache.add(Action.PULL);
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) cache.add(Action.PAUSE);
		if (input.isKeyPressed(Input.KEY_F5))     cache.add(Action.RESET);
		if (input.isKeyPressed(Input.KEY_F11))    cache.add(Action.FULLSCREEN);

		if (input.isKeyPressed(Input.KEY_F2)) cache.add(Action.GOD_MODE);
		if (input.isKeyPressed(Input.KEY_F3)) cache.add(Action.DEBUG);
		if (input.isKeyPressed(Input.KEY_F4)) cache.add(Action.SLOW_DOWN);
		if (input.isKeyPressed(Input.KEY_F6)) cache.add(Action.SKIP);
	}
	
	public static double getAimAngle(Player player) {
		Vector2f mouse = new Vector2f(mouseX, mouseY);
		Vector2f p = Util.PointToVector2f(player.getScreenPosition());
		Vector2f aim = mouse.sub(p);
		return aim.getTheta();
	}
	
	public static boolean isKeyPressed(Action action) {
		return cache.contains(action);
	}
}
