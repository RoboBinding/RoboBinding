package org.robobinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.robobinding.util.ClassUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class ReflectionUtils {

	public static <T> void setField(Object target, String fieldName, Class<T> fieldType, T value) {
		Field field = findField(target.getClass(), fieldName, fieldType);
		if(field == null) {
			throw new RuntimeException("unknown given field '"+fieldName+"'");
		}
		
		makeAccessible(field);
		try {
			field.set(target, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}

	private static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) 
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers()) 
				|| Modifier.isFinal(field.getModifiers()))
				&& !field.isAccessible()) {
			field.setAccessible(true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object target, String fieldName, Class<T> fieldType) {
		Field field = findField(target.getClass(), fieldName, fieldType);
		if(field == null) {
			throw new RuntimeException("unknown given field '"+fieldName+"'");
		}
		
		makeAccessible(field);
		try {
			return (T)field.get(target);
		}
		catch (IllegalAccessException ex) {
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage(), ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T tryToGetCompatibleField(Object target, String fieldName, Class<T> compatibleFieldType) {
		Field field = findCompatibleField(target.getClass(), fieldName, compatibleFieldType);
		if(field == null) {
			return null;
		}
		
		makeAccessible(field);
		try {
			return (T)field.get(target);
		}
		catch (IllegalAccessException ex) {
			throw new IllegalStateException(
					"Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage(), ex);
		}
	}

	private static Field findCompatibleField(Class<?> clazz, String name, Class<?> compatibleType) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName())) && (compatibleType == null || compatibleType.isAssignableFrom(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T tryToInvokeMethod(Object target, String name, Object... args) {
		final Class<?>[] parameterTypes = ClassUtils.toClass(args);
		Method method = findMethod(target.getClass(), name, parameterTypes);
		if(method != null) {
			makeAccessible(method);
			try {
				return (T)method.invoke(target, args);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}
	
	private static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName()) &&
						(paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	private static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) &&
				!method.isAccessible()) {
			method.setAccessible(true);
		}
	}
}