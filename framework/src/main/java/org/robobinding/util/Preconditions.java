package org.robobinding.util;

import java.util.Collection;

/**
 * Migrated from {@link com.google.common.base.Preconditions}
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Preconditions {
  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param reference an object reference
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }
  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null.
   *
   * @param reference an object reference
   * @param errorMessage the exception message to use if the check fails; will be converted to a
   *     string using {@link String#valueOf(Object)}
   * @return the non-null reference that was validated
   * @throws NullPointerException if {@code reference} is null
   */
  public static <T> T checkNotNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }
  /**
   * Ensures the truth of an expression involving one or more parameters to the calling method.
   *
   * @param expression a boolean expression
   * @param errorMessage the exception message to use if the check fails; will be converted to a
   *     string using {@link String#valueOf(Object)}
   * @throws IllegalArgumentException if {@code expression} is false
   */
  public static void checkArgument(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalArgumentException(String.valueOf(errorMessage));
    }
  }

	private Preconditions() {
	}

	public static void checkNotBlank(String str, String errorMessage) {
		checkArgument(!Strings.isNullOrEmpty(str), errorMessage);
	}

	public static void checkNotBlank(String errorMessage, String... strs) {
		checkArgument(!ArrayUtils.isEmpty(strs), errorMessage);
		for (String str : strs) {
			checkNotBlank(str, errorMessage);
		}
	}

	public static void checkValidResourceId(int resourceId, String errorMessage) {
		checkArgument(resourceId != 0, errorMessage);
	}
	
	public static void checkNotEmpty(Collection<?> c, String errorMessage) {
		if((c == null) || c.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
