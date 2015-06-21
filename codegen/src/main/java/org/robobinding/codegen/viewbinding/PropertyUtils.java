package org.robobinding.codegen.viewbinding;

import org.apache.commons.lang3.StringUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyUtils {
	public static String setterNameOf(String property) {
		return "set"+StringUtils.capitalize(property);
	}
}
