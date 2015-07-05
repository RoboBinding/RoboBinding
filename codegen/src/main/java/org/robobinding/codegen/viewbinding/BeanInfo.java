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
	private final Class<?> beanClass;
	
	public BeanInfo(Class<?> beanClass) {
		this.beanClass = beanClass;
	}
	
	public Setters parseSingleParameterSetters() {
		Map<String, Method> singleParameterSetters = Maps.newHashMap();
		Set<String> propertiesWithAmbiguousSetters = Sets.newHashSet();
		PropertyDescriptor[] propertyDescriptorArray = propertyDescriptorArray();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			if(!hasSingleParameterSetter(propertyDescriptor)) {
				continue;
			}
			
			if(singleParameterSetters.containsKey(propertyDescriptor.getName())) {
				propertiesWithAmbiguousSetters.add(propertyDescriptor.getName());
			}
			singleParameterSetters.put(propertyDescriptor.getName(), propertyDescriptor.getWriteMethod());
		}

		for(String propertyWithAmbiguousSetters : propertiesWithAmbiguousSetters) {
			singleParameterSetters.remove(propertyWithAmbiguousSetters);
		}
		
		return new Setters(singleParameterSetters, propertiesWithAmbiguousSetters);
	}
	
	private PropertyDescriptor[] propertyDescriptorArray() {
		try {
			org.robobinding.internal.java_beans.BeanInfo info = Introspector.getBeanInfo(beanClass);
			return info.getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private boolean hasSingleParameterSetter(PropertyDescriptor propertyDescriptor) {
		Method writeMethod = propertyDescriptor.getWriteMethod();
		return (writeMethod != null) && (writeMethod.getParameterTypes().length == 1);
	}
}
