package org.robobinding.util;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PrimitiveTypeUtils {
	private PrimitiveTypeUtils() {
	}

	public static boolean booleanIsAssignableFrom(Class<?> type) {
		return Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type);
	}

	public static boolean integerIsAssignableFrom(Class<?> type) {
		return Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type);
	}

	public static boolean floatIsAssignableFrom(Class<?> type) {
		return Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type);
	}
}
