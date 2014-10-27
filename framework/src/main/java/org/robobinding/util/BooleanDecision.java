package org.robobinding.util;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BooleanDecision {
	private boolean result;

	public BooleanDecision() {
		result = false;
	}

	public BooleanDecision or(boolean expression) {
		if (result == false) {
			result = expression;
		}

		return this;
	}

	public boolean getResult() {
		return result;
	}

}
