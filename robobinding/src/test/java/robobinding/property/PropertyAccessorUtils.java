/**
 * PropertyUtilsEx.java
 * Oct 29, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.property;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import robobinding.property.PropertyAccessor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorUtils
{
	private PropertyAccessorUtils()
	{
	}
	public static <T> PropertyAccessor<T> createPropertyAccessor(Object bean, String propertyName)
	{
		PropertyDescriptor propertyDescriptor = PropertyAccessorUtils.getPropertyDescriptor(bean, propertyName);
		return new PropertyAccessor<T>(propertyDescriptor, bean.getClass());
	}
	private static PropertyDescriptor getPropertyDescriptor(Object bean, String propertyName)
	{
		try
		{
			return PropertyUtils.getPropertyDescriptor(bean, propertyName);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		} catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}
}
