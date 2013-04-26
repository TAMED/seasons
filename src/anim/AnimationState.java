/**
 * 
 */
package anim;

import java.util.EnumSet;

/**
 * An enumeration of all of the animations in the game, as well as their transitions
 * @author Mullings
 *
 */
public enum AnimationState {
	BASIC, IDLE, RUN, JUMP, RISE, FALL, HOOKING, START_JUMP;
	
	private EnumSet<AnimationState> prohibited;
	private AnimationState transition = null;
	
	private void prohibitTransitions(AnimationState... prohibitedTransitions) {
		prohibited = EnumSet.noneOf(AnimationState.class);
		for (AnimationState t : prohibitedTransitions) {
			prohibited.add(t);
		}
	}
	
	private void transitionFrom(AnimationState as) {
		transition = as;
	}
	
	public AnimationState transitionsFrom() {
		return transition;
	}
	
	public boolean canChangeTo(AnimationState state) {
		return !prohibited.contains(state);
	}
	
	static {
		BASIC.prohibitTransitions();
		
		IDLE.prohibitTransitions();
		RUN.prohibitTransitions();
		JUMP.prohibitTransitions(IDLE, RUN);
		RISE.prohibitTransitions(IDLE, RUN);
		FALL.prohibitTransitions(RUN);
		HOOKING.prohibitTransitions();
		START_JUMP.prohibitTransitions();
		
		JUMP.transitionFrom(START_JUMP);
	}
}
