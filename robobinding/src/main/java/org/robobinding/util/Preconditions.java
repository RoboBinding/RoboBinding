package org.robobinding.util;

import static org.robobinding.internal.guava.Preconditions.checkArgument;

import org.robobinding.internal.guava.Strings;

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
}
