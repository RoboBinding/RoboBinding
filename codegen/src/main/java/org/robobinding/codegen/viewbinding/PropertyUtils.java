package org.robobinding.codegen.viewbinding;

import java.lang.reflect.Method;
import java.util.List;

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
	
	public static List<Method> findSetters(Class<?> cls, String property) {
		return null;
	}
	
}
