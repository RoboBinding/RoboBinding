package org.robobinding.widget.edittext;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public enum ValueCommitMode {
	ON_FOCUS_LOST("onFocusLost"), ON_CHANGE("onChange");

	private final String value;

	private ValueCommitMode(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

	public static ValueCommitMode from(String value) {
		for (ValueCommitMode mode : ValueCommitMode.values()) {
			if (mode.value.equals(value)) {
				return mode;
			}
		}

		throw new RuntimeException("no matching ValueCommitMode found for '" + value + "'");
	}
}
