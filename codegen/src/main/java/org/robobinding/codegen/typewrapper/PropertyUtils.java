package org.robobinding.codegen.typewrapper;

import org.apache.commons.lang3.StringUtils;
import org.robobinding.Bug;
import org.robobinding.internal.java_beans.Introspector;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyUtils {
	
	private static String findRawPropertyNameFromGetter(MethodElementWrapper getter) {
		String methodName = getter.methodName();
		if (methodName.startsWith("is") && getter.getReturnType().isBoolean()) {
			return StringUtils.removeStart(methodName, "is");
		} else if (methodName.startsWith("get")) {
			return StringUtils.removeStart(methodName, "get");
		} else {
			return null;
		}
	}
	
	public static String propertyNameFromGetter(MethodElementWrapper getter) {
		String propertyName = findRawPropertyNameFromGetter(getter);
		if(StringUtils.isBlank(propertyName)) {
			throw new Bug(getter.toString()+" is not a property Getter");
		} else {
			return Introspector.decapitalize(propertyName);
		}
	}

	public static boolean isGetter(MethodElementWrapper method) {
		if(method.hasParameter() || method.hasNoReturn()) {
			return false;
		}
		
		String propertyName = findRawPropertyNameFromGetter(method);
		
		return isFirstLetterUpperCase(propertyName);
	}
	
	private static boolean isFirstLetterUpperCase(String text) {
		return StringUtils.isNotBlank(text) && Character.isUpperCase(text.charAt(0));
	}
	
	public static boolean isSetter(MethodElementWrapper method) {
		String methodName = method.methodName();
		if (methodName.startsWith("set") && method.hasSingleParameter() && method.hasNoReturn()) {
			String propertyName = findRawPropertyNameFromSetter(methodName);
			if(isFirstLetterUpperCase(propertyName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static String propertyNameFromSetter(MethodElementWrapper setter) {
		String propertyName = findRawPropertyNameFromSetter(setter.methodName());
		if(StringUtils.isBlank(propertyName)) {
			throw new Bug(setter.toString()+" is not a property Setter");
		} else {
			return Introspector.decapitalize(propertyName);
		}
	}
	
	private static String findRawPropertyNameFromSetter(String methodName) {
		return StringUtils.removeStart(methodName, "set");
	}
}
