package org.robobinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PreInitializingViews {
	public final boolean defaultValue;
	public final boolean value;
	
	public PreInitializingViews(boolean defaultValue, boolean value) {
		this.defaultValue = defaultValue;
		this.value = value;
	}
	
	public PreInitializingViews withValue(boolean newValue) {
		return new PreInitializingViews(defaultValue, newValue);
	}
	
	public static PreInitializingViews initial(boolean value) {
		return new PreInitializingViews(value, value);
	}
}
