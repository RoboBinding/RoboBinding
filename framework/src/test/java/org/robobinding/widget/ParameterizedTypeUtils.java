package org.robobinding.widget;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.reflect.ConstructorUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ParameterizedTypeUtils {
	private ParameterizedTypeUtils() {
	}

	public static <T> T createTypeArgument(ParameterizedType type, int typeArgumentIndex) {
		return createTypeArgument(type, typeArgumentIndex, null, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createTypeArgument(ParameterizedType type, int typeArgumentIndex, Class<?> clazz, Object constructorArg) {
		try {
			Type argumentType = type.getActualTypeArguments()[typeArgumentIndex];
			Class<?> rawType = (argumentType instanceof Class<?>) ? (Class<?>) argumentType : (Class<?>) ((ParameterizedType) argumentType).getRawType();

			// Constructor<?> constructor = clazz == null ?
			// rawType.getDeclaredConstructor() :
			// rawType.getDeclaredConstructor(clazz);
			Constructor<?> constructor = clazz == null ? rawType.getDeclaredConstructor() : ConstructorUtils.getMatchingAccessibleConstructor(rawType, clazz);
			makeAccessible(constructor);

			if (constructorArg != null)
				return (T) constructor.newInstance(constructorArg);

			return (T) constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void makeAccessible(Constructor<?> ctor) {
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
			ctor.setAccessible(true);
		}
	}
}
