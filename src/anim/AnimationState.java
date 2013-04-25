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
	IDLE, RUN, JUMP, FALL, HOOKING;
	
	private EnumSet<AnimationState> prohibited;
	
	private void prohibitTransitions(AnimationState... prohibitedTransitions) {
		prohibited = EnumSet.noneOf(AnimationState.class);
		for (AnimationState t : prohibitedTransitions) {
			prohibited.add(t);
		}
	}
	
	public boolean canChangeTo(AnimationState state) {
		return !prohibited.contains(state);
	}
	
	static {
		IDLE.prohibitTransitions();
		RUN.prohibitTransitions();
		JUMP.prohibitTransitions(IDLE, RUN);
		FALL.prohibitTransitions(RUN);
		HOOKING.prohibitTransitions();
	}
}
