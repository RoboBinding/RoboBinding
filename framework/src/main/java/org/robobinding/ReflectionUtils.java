package org.robobinding;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

	private static Field findField(Class<?> clazz, String name, Class<?> type) {
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
}