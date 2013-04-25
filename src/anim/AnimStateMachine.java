/**
 * 
 */
package anim;

import java.util.EnumMap;

import org.newdawn.slick.Animation;

import util.Direction;
import entities.Entity;


/**
 * A state machine for handling animation transitions
 * @author Mullings
 *
 */
public class AnimStateMachine {
	private EnumMap<AnimationState, Animation> animMap;
	protected AnimationState currentState;

	public AnimStateMachine() {
		animMap = new EnumMap<AnimationState, Animation>(AnimationState.class);
	}
	
	public void addAnimation(AnimationState type, Animation animation) {
		animMap.put(type, animation);
	}
	
	public void update(Entity entity) {
		if (currentState == null) return;
		
		if (currentState == AnimationState.FALL && animMap.get(AnimationState.START_JUMP) != null) {
			animMap.get(AnimationState.START_JUMP).restart();
		}
		
		switch(currentState) {
			case JUMP:
				if (entity.getPhysicsBody().getLinearVelocity().y >= 0) {
					play(AnimationState.FALL);
//					System.out.println("JUMP => FALL");
				}
				break;
			case FALL:
				if (entity.isTouching(Direction.DOWN)) {
					play(AnimationState.IDLE);
//					System.out.println("FALL => IDLE");
				}
				break;
			case IDLE:
			case RUN:
				if (!entity.isTouching(Direction.DOWN)) {
					play(AnimationState.FALL);
//					System.out.println("IDLE/RUN => FALL");
				}
				break;
		}
	}
	
	
	public boolean play(AnimationState state) {
		if (currentState == null || currentState.canChangeTo(state)) {
			currentState = state;
			return true;
		}
		return false;
	}

	public AnimationState getCurrentState() {
		return currentState;
	}

	public Animation getCurrentAnimation() {
		if (currentState != null) {
			if (currentState.transitionsFrom() == null )
				return animMap.get(currentState);

			Animation transition = animMap.get(currentState.transitionsFrom());

			if (transition.isStopped()) {
				return animMap.get(currentState);
			} else {
				return animMap.get(currentState.transitionsFrom());
			}
		
		}
		return null;
	}
	
	public void setFrames(AnimationState as, int numFrames, int durPerFrame) {
		for (int i = 0; i < numFrames; i++) {
			Animation a = animMap.get(as);
			a.setDuration(i, durPerFrame);
			animMap.put(as, a);
		}
		for (int i = numFrames; i < animMap.get(as).getFrameCount(); i++) {
			Animation a = animMap.get(as);
			a.setDuration(i, 0);
			animMap.put(as, a);
		}
	}
	
}
