package org.robobinding.codegen.viewbinding;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.robobinding.internal.java_beans.IntrospectionException;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.internal.java_beans.PropertyDescriptor;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class BeanInfo {
	private final Map<String, PropertyDescriptor> uniquePropertyMap;
	public BeanInfo(Map<String, PropertyDescriptor> uniquePropertyMap) {
		this.uniquePropertyMap = uniquePropertyMap;
	}
	
	public Method findSetter(String property) {
		PropertyDescriptor descriptor = uniquePropertyMap.get(property);
		if(descriptor == null) {
			return null;
		}
		
		Method setter = descriptor.getWriteMethod();
		if((setter != null) && (setter.getParameterTypes().length == 1)) {
			return setter;
		}
		
		return null;
	}
	
	public static BeanInfo create(Class<?> beanClass) {
		try {
			org.robobinding.internal.java_beans.BeanInfo info = Introspector.getBeanInfo(beanClass);
			return new BeanInfo(uniquePropertyMap(info.getPropertyDescriptors()));
		} catch (IntrospectionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static Map<String, PropertyDescriptor> uniquePropertyMap(PropertyDescriptor[] propertyDescriptorArray) {

		Map<String, PropertyDescriptor> uniquePropertyMap = Maps.newHashMap();
		Set<String> duplicatedPropertyNames = Sets.newHashSet();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			if(uniquePropertyMap.containsKey(propertyDescriptor.getName())) {
				duplicatedPropertyNames.add(propertyDescriptor.getName());
			} else {
				uniquePropertyMap.put(propertyDescriptor.getName(), propertyDescriptor);
			}
		}
		
		for(String duplicatedPropertyName : duplicatedPropertyNames) {
			uniquePropertyMap.remove(duplicatedPropertyName);
		}
		
		return uniquePropertyMap;
	}
}
