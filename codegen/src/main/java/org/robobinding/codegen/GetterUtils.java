package org.robobinding.codegen;

import javax.lang.model.type.TypeKind;

import org.apache.commons.lang3.StringUtils;
import org.robobinding.function.Bug;
import org.robobinding.internal.java_beans.Introspector;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class GetterUtils {
	
	private static String findRawPropertyNameFromGetter(MethodElementWrapper getter) {
		String methodName = getter.methodName();
		if (methodName.startsWith("is") && getter.isOfReturnType(TypeKind.BOOLEAN)) {
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
			throw new Bug(getter.toString()+" is not a property");
		} else {
			return Introspector.decapitalize(propertyName);
		}
	}

	public static boolean isGetter(MethodElementWrapper method) {
		if(method.hasParameter()) {
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
		if (methodName.startsWith("set") && method.hasSingleParameter() && method.isOfReturnType(TypeKind.VOID)) {
			String propertyName = StringUtils.removeStart(methodName, "set");
			if(isFirstLetterUpperCase(propertyName)) {
				return true;
			}
		}
		return false;
	}
}
