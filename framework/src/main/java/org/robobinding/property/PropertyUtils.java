package org.robobinding.property;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import org.robobinding.internal.java_beans.BeanInfo;
import org.robobinding.internal.java_beans.IntrospectionException;
import org.robobinding.internal.java_beans.Introspector;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyUtils {
	private static final Set<String> EXCLUDED_PROPERTY_NAMES = Sets.newHashSet("class");

	public static Map<String, PropertyDescriptor> getPropertyDescriptorMap(Class<?> beanClass) {
		try {
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			org.robobinding.internal.java_beans.PropertyDescriptor[] propertyDescriptorArray = info.getPropertyDescriptors();

			Map<String, PropertyDescriptor> propertyDescriptorMap = Maps.newHashMap();
			for (org.robobinding.internal.java_beans.PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
				if (EXCLUDED_PROPERTY_NAMES.contains(propertyDescriptor.getName())) {
					continue;
				}
				propertyDescriptorMap.put(propertyDescriptor.getName(), new PropertyDescriptor(beanClass, propertyDescriptor));
			}
			return propertyDescriptorMap;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	public static Set<String> getPropertyNames(Class<?> beanClass) {
		return getPropertyDescriptorMap(beanClass).keySet();
	}

	public static PropertyDescriptor findPropertyDescriptor(Class<?> beanClass, String propertyName) {
		try {
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			org.robobinding.internal.java_beans.PropertyDescriptor[] propertyDescriptorArray = info.getPropertyDescriptors();

			for (org.robobinding.internal.java_beans.PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
				if (propertyName.equals(propertyDescriptor.getName())) {
					return new PropertyDescriptor(beanClass, propertyDescriptor);
				}
			}

			return null;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	public static String shortDescription(Class<?> beanClass, String proeprtyName) {
		return MessageFormat.format("{0}.{1}", beanClass.getName(), proeprtyName);

	}

	private PropertyUtils() {
	}
}
