/**
 * 
 */
package entities.enemies;

import org.newdawn.slick.Image;

import ai.Goomba;

/**
 * @author Mullings
 *
 */
public class Ent extends Enemy {

	public Ent(float x, float y) {
		super(x, y, 64, 64, 2, 2, new Goomba());
		try {
			setImage(new Image("assets/images/ent1.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
