package org.robobinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@SuppressWarnings("serial")
public class Bug extends Error {
	public Bug(String message) {
		super(message);
	}

	public Bug(String message, Exception cause) {
		super(message, cause);
	}
}
