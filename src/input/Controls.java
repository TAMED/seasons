/**
 * 
 */
package input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import util.Util;
import entities.Player;


/**
 * @author Mullings
 *
 */
public class Controls {
	public enum Action { UP, DOWN, LEFT, RIGHT, JUMP, PRIMARY1, PRIMARY2, SECONDARY }
	
	private static GameContainer container;

	public static void setGC(GameContainer gc) {
		container = gc;
	}
	
	public static double getAimAngle(Player player) {
		Vector2f mouse = new Vector2f(container.getInput().getAbsoluteMouseX(), 
		                              container.getInput().getAbsoluteMouseY());
		Vector2f p = Util.PointToVector2f(player.getScreenPosition());

		Vector2f aim = mouse.sub(p);
		
		return aim.getTheta();
	}
	
	public static boolean isKeyPressed(Action action) {
		return false;
	}
}