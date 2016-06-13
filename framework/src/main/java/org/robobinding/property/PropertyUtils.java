package org.robobinding.property;

import java.text.MessageFormat;
import java.util.Set;

import org.robobinding.internal.java_beans.BeanInfo;
import org.robobinding.internal.java_beans.IntrospectionException;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.internal.java_beans.PropertyDescriptor;
import org.robobinding.util.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyUtils {
	private static final Set<String> EXCLUDED_PROPERTY_NAMES = Sets.newHashSet("class");

	public static Set<String> getPropertyNames(Class<?> beanClass) {
		PropertyDescriptor[] propertyDescriptorArray = getPropertyDescriptors0(beanClass);

		Set<String> propertyNames = Sets.newHashSet();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			if (EXCLUDED_PROPERTY_NAMES.contains(propertyDescriptor.getName())) {
				continue;
			}
			
			propertyNames.add(propertyDescriptor.getName());
		}
		return propertyNames;
	}
	
	private static PropertyDescriptor[] getPropertyDescriptors0(Class<?> beanClass) {
		try {
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			return info.getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Set<PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass) {
		PropertyDescriptor[] propertyDescriptorArray = getPropertyDescriptors0(beanClass);

		Set<PropertyDescriptor> propertyDescriptors = Sets.newHashSet();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			if (EXCLUDED_PROPERTY_NAMES.contains(propertyDescriptor.getName())) {
				continue;
			}
			
			propertyDescriptors.add(propertyDescriptor);
		}
		return propertyDescriptors;
	}
	
	public static String shortDescription(Class<?> beanClass, String proeprtyName) {
		return MessageFormat.format("{0}.{1}", beanClass.getName(), proeprtyName);

	}

	private PropertyUtils() {
	}
}
