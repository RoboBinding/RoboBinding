package org.robobinding.util;

import static com.google.common.base.Preconditions.checkArgument;

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
	checkArgument(!Strings.isNullOrEmpty(str), errorMessage);
    }

    public static void checkNotBlank(String errorMessage, String... strs) {
	checkArgument(!ArrayUtils.isEmpty(strs), errorMessage);
	for (String str : strs) {
	    checkNotBlank(str, errorMessage);
	}
    }
}
