/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ParameterizedTypeUtils
{

	@SuppressWarnings("unchecked")
	public static <T> T createTypeArgument(ParameterizedType type, int i)
	{
		try
		{
			Type argumentType = type.getActualTypeArguments()[i];
			Class<?> rawType = (argumentType instanceof Class<?>) ? 
				(Class<?>)argumentType : (Class<?>)((ParameterizedType)argumentType).getRawType();
			Constructor<?> constructor = rawType.getDeclaredConstructor();
			makeAccessible(constructor);
			return (T)constructor.newInstance();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static void makeAccessible(Constructor<?> ctor)
	{
		if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers()))
				&& !ctor.isAccessible())
		{
			ctor.setAccessible(true);
		}
	}
}
