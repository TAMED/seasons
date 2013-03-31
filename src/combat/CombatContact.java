/**
 * 
 */
package combat;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import entities.Player;
import entities.enemies.Enemy;

/**
 * @author Mullings
 *
 */
public class CombatContact implements ContactListener {

	/**
	 * 
	 */
	public CombatContact() {
		
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#beginContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Object fixA = contact.getFixtureA().getUserData();
		Object fixB = contact.getFixtureB().getUserData();
		
		if (fixA instanceof Player && fixB instanceof Enemy) {
			if (((Player) fixA).isAttacking()) {
				((Enemy) fixB).kill();
			} else {
				((Player) fixA).kill();
			}
		}
		
		if (fixB instanceof Player && fixA instanceof Enemy) {
			if (((Player) fixB).isAttacking()) {
				((Enemy) fixA).kill();
			} else {
				((Player) fixB).kill();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#endContact(org.jbox2d.dynamics.contacts.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#postSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.callbacks.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.jbox2d.callbacks.ContactListener#preSolve(org.jbox2d.dynamics.contacts.Contact, org.jbox2d.collision.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

}
