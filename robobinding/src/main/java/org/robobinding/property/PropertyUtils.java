/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.property;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Set;

import org.robobinding.internal.java_beans.BeanInfo;
import org.robobinding.internal.java_beans.IntrospectionException;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.internal.java_beans.PropertyDescriptor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyUtils
{
	private static Set<String> EXCLUDED_PROPERTY_NAMES = Sets.newHashSet("class");
	//TODO:seems it accept setters with two parameters.
	public static List<PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass)
	{
		checkNotNull(beanClass, "beanClass cannot be null");
		try
		{
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor[] propertyDescriptorArray = info.getPropertyDescriptors();
			
			List<PropertyDescriptor> propertyDescriptors = Lists.newArrayList();
			for(PropertyDescriptor propertyDescriptor : propertyDescriptorArray)
			{
				if(EXCLUDED_PROPERTY_NAMES.contains(propertyDescriptor.getName()))
				{
					continue;
				}
				propertyDescriptors.add(propertyDescriptor);
			}
			return propertyDescriptors;
		} catch (IntrospectionException e)
		{
			throw new RuntimeException(e);
		}
	}
	public static List<String> getPropertyNames(Class<?> beanClass)
	{
		List<PropertyDescriptor> propertyDescriptors = getPropertyDescriptors(beanClass);
		List<String> propertyNames = Lists.newArrayList();
		for(PropertyDescriptor propertyDescriptor : propertyDescriptors)
		{
			propertyNames.add(propertyDescriptor.getName());
		}
		return propertyNames;
	}
	
	private PropertyUtils(){}
}
