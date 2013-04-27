/**
 * 
 */
package anim;

import java.util.EnumSet;

import config.Config;

import util.Direction;

import entities.Entity;

/**
 * An enumeration of all of the animations in the game, as well as their transitions
 * @author Mullings
 *
 */
public enum AnimationState {
	
	BASIC {
		@Override
		AnimationState getNextState(Entity entity) {
			return AnimationState.BASIC;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 
	
	// passive animations
	IDLE {
		@Override
		AnimationState getNextState(Entity entity) {
			if (!entity.isTouching(Direction.DOWN)) {
				if (entity.getPhysicsBody().getLinearVelocity().y >= Config.VEL_EPSILON) {
					return AnimationState.FALL;
				} else {
					return AnimationState.RISE;
				}
			} 
			
			if (!entity.isStill()) {
				return AnimationState.RUN;
			} 
			return this;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 
	RUN {
		@Override
		AnimationState getNextState(Entity entity) {
			if (!entity.isTouching(Direction.DOWN)) {
				if (entity.getPhysicsBody().getLinearVelocity().y >= Config.VEL_EPSILON) {
					return AnimationState.FALL;
				} else {
					return AnimationState.RISE;
				}
			} 
			if (entity.isStill()) {
				return AnimationState.IDLE;
			} 
			
			return this;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 

	RISE {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.getPhysicsBody().getLinearVelocity().y >= Config.VEL_EPSILON) {
				return AnimationState.FALL;
			} 
			return this;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 
	FALL {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.isTouching(Direction.DOWN)) {
				return AnimationState.IDLE;
			}
			if (entity.getPhysicsBody().getLinearVelocity().y < Config.VEL_EPSILON) {
				return AnimationState.RISE;
			}
			return this;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 
	
	// player specific
	
	JUMP {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.getPhysicsBody().getLinearVelocity().y >= Config.VEL_EPSILON) {
				return AnimationState.FALL;
			} 
			return this;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 
	
	HOOKING {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.isTouching(Direction.DOWN)) {
				return AnimationState.IDLE;
			}
			if (entity.getPhysicsBody().getLinearVelocity().y < Config.VEL_EPSILON) {
				return AnimationState.RISE;
			}
			return this;
		}

		@Override
		boolean isTransition() {
			return false;
		}
	}, 
	
	// Transitions
	START_JUMP {
		@Override
		AnimationState getNextState(Entity entity) {
			return null;
		}

		@Override
		boolean isTransition() {
			return true;
		}
	};
	
	abstract AnimationState getNextState(Entity entity);
	abstract boolean isTransition();
	
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
