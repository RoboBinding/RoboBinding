package org.robobinding.codegen.typemirror;

import java.util.Set;

import org.robobinding.property.PropertyUtils;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyDescriptorUtils {
	public static Set<PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass) {
		Set<PropertyDescriptor> descriptors = Sets.newHashSet();
		for(org.robobinding.internal.java_beans.PropertyDescriptor descriptor : PropertyUtils.getPropertyDescriptors(beanClass)) {
			descriptors.add(new PropertyDescriptor(descriptor));
		}
		return descriptors;
	}
}
