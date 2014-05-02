package org.robobinding.viewattribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewUpdatePropagationLatch {
    private State state;

    public ViewUpdatePropagationLatch() {
	state = State.NEUTRAL;
    }

    public void turnOn() {
	state = State.LOCKED;
    }

    public void turnOff() {
	state = State.NEUTRAL;
    }

    public boolean tryToPass() {
	boolean isPassible = state.isPassible();
	state = state.getNextState();
	return isPassible;
    }

    private static enum State {
	NEUTRAL {
	    @Override
	    public boolean isPassible() {
		return true;
	    }

	    @Override
	    public State getNextState() {
		return NEUTRAL;
	    }

	},

	LOCKED {
	    @Override
	    public boolean isPassible() {
		return false;
	    }

	    @Override
	    public State getNextState() {
		return UNLOCKED;
	    }

	},

	UNLOCKED {
	    @Override
	    public boolean isPassible() {
		return true;
	    }

	    @Override
	    public State getNextState() {
		return UNLOCKED;
	    }

	};

	public abstract boolean isPassible();

	public abstract State getNextState();

    }
}
