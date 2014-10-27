package org.robobinding.util;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CollectionUtils {
	private CollectionUtils() {
	}

	public static boolean isNotEmpty(Collection<?> c) {
		if (c == null) {
			return false;
		}
		return !c.isEmpty();
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		if (map == null) {
			return false;
		}
		return !map.isEmpty();
	}
}
