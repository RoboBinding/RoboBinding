package org.robobinding.dynamicbinding;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.robobinding.internal.java_beans.BeanInfo;
import org.robobinding.internal.java_beans.IntrospectionException;
import org.robobinding.internal.java_beans.Introspector;
import org.robobinding.internal.java_beans.PropertyDescriptor;
import org.robobinding.property.PropertyUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessor {
	private final Class<?> beanClass;
	private final PropertyDescriptor descriptor;
	private final Setter setter;

	public PropertyAccessor(Class<?> beanClass, PropertyDescriptor descriptor) {
		this.beanClass = beanClass;
		this.descriptor = descriptor;
		setter = new Setter();
	}

	private RuntimeException createWriteAccessException(Object newValue, Throwable cause) {
		return new RuntimeException("error when writing property '" + getShortDescription() + "' with newValue '" + newValue + "'", cause);
	}

	private String getShortDescription() {
		return PropertyUtils.shortDescription(beanClass, descriptor.getName());
	}

	public boolean isWritable() {
		return descriptor.getWriteMethod() != null;
	}

	public Setter getSetter() {
		return setter;
	}

	public static PropertyAccessor findPropertyAccessor(Class<?> beanClass, String propertyName) {
		try {
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor[] propertyDescriptorArray = info.getPropertyDescriptors();

			for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
				if (propertyName.equals(propertyDescriptor.getName())) {
					return new PropertyAccessor(beanClass, propertyDescriptor);
				}
			}

			return null;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	public class Setter {
		private Setter() {
		}

		public void doSet(Object bean, Object newValue) {
			try {
				descriptor.getWriteMethod().invoke(bean, newValue);
			} catch (InvocationTargetException e) {
				throw createWriteAccessException(newValue, e.getCause());
			} catch (IllegalAccessException e) {
				throw createWriteAccessException(newValue, e);
			} catch (IllegalArgumentException e) {
				if (isParameterTypeMismatch(newValue)) {
					Class<?> actualParameterType = newValue.getClass();
					throw new IllegalArgumentException(MessageFormat.format("Unexpected parameter type ''{0}'' for the setter ''{1}''",
							actualParameterType.getName(), descriptor.getWriteMethod().toString()), e);
				} else {
					throw e;
				}
			}
		}

		private boolean isParameterTypeMismatch(Object actualParameter) {
			if (actualParameter == null)
				return false;

			Class<?> parameterType = getParameterType();
			return !parameterType.isAssignableFrom(actualParameter.getClass());
		}

		private Class<?> getParameterType() {
			return descriptor.getWriteMethod().getParameterTypes()[0];
		}
	}
}