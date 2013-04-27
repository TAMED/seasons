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

	}, 

	RISE {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.getPhysicsBody().getLinearVelocity().y >= Config.VEL_EPSILON) {
				return AnimationState.FALL;
			} 
			return this;
		}

	}, 
	FALL {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.isTouching(Direction.DOWN)) {
				return AnimationState.IDLE;
			}
			if (entity.getPhysicsBody().getLinearVelocity().y < -Config.VEL_EPSILON) {
				return AnimationState.RISE;
			}
			return this;
		}

	}, 
	
	// player specific
	START_JUMP {
		@Override
		AnimationState getNextState(Entity entity) {
			return null;
		}

		@Override
		public boolean isTransition() {
			return true;
		}
	},
	
	JUMP(START_JUMP) {
		@Override
		AnimationState getNextState(Entity entity) {
			if (entity.getPhysicsBody().getLinearVelocity().y >= Config.VEL_EPSILON) {
				return AnimationState.FALL;
			} 
			return this;
		}
	}, 
	
	HOOKING {
		@Override
		AnimationState getNextState(Entity entity) {
			if (!entity.isTouching(Direction.DOWN)) {
				if (entity.getPhysicsBody().getLinearVelocity().y < Config.VEL_EPSILON) {
					return AnimationState.RISE;
				} else {
					return AnimationState.FALL;
				}
			}
			return this;
		}

	};
	
	
	private EnumSet<AnimationState> prohibited;
	private AnimationState transitionFrom = null;
	
	private AnimationState() {
		this.transitionFrom = null;
	}
	
	private AnimationState(AnimationState transitionFrom) {
		this.transitionFrom = transitionFrom;
	}
	
	abstract AnimationState getNextState(Entity entity);
	
	public boolean isTransition() {
		return false;
	}
	
	private void prohibitTransitions(AnimationState... prohibitedTransitions) {
		prohibited = EnumSet.noneOf(AnimationState.class);
		for (AnimationState t : prohibitedTransitions) {
			prohibited.add(t);
		}
	}
	
	public AnimationState transitionsFrom() {
		return transitionFrom;
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
		
//		JUMP.transitionFrom(START_JUMP);
	}
}
