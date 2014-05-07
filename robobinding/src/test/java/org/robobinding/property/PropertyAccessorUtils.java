package org.robobinding.property;

import java.util.List;

import org.robobinding.internal.java_beans.PropertyDescriptor;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorUtils {
    private PropertyAccessorUtils() {
    }

    public static <T> PropertyAccessor<T> createPropertyAccessor(Class<?> beanClass, String propertyName) {
	PropertyDescriptor propertyDescriptor = PropertyAccessorUtils.getPropertyDescriptor(beanClass, propertyName);
	return new PropertyAccessor<T>(propertyDescriptor, beanClass);
    }

    private static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
	List<PropertyDescriptor> propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
	for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
	    if (propertyDescriptor.getName().equals(propertyName)) {
		return propertyDescriptor;
	    }
	}
	return null;
    }
}
