package org.robobinding.util;

import java.util.Collection;

import com.google.common.base.Strings;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Preconditions {
	private Preconditions() {
	}

	public static void checkNotBlank(String str, String errorMessage) {
		com.google.common.base.Preconditions.checkArgument(!Strings.isNullOrEmpty(str), errorMessage);
	}

	public static void checkNotBlank(String errorMessage, String... strs) {
		com.google.common.base.Preconditions.checkArgument(!ArrayUtils.isEmpty(strs), errorMessage);
		for (String str : strs) {
			checkNotBlank(str, errorMessage);
		}
	}

	public static void checkValidResourceId(int resourceId, String errorMessage) {
		com.google.common.base.Preconditions.checkArgument(resourceId != 0, errorMessage);
	}
	
	public static void checkNotEmpty(Collection<?> c, String errorMessage) {
		if((c == null) || c.isEmpty()) {
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
