package org.robobinding.property;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

import com.google.common.base.Strings;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessor {
	private final Object bean;
	private final PropertyDescriptor descriptor;

	public PropertyAccessor(Object bean, PropertyDescriptor descriptor) {
		this.bean = bean;
		this.descriptor = descriptor;
	}

	public Object getValue() {
		return descriptor.doGet(bean);
	}

	public void checkReadable() {
		descriptor.checkReadable();
	}

	public void setValue(Object newValue) {
		descriptor.doSet(bean, newValue);
	}

	public void checkWritable() {
		descriptor.checkWritable();
	}

	public Class<?> getPropertyType() {
		return descriptor.getPropertyType();
	}

	public String getPropertyName() {
		return descriptor.getName();
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
		Annotation annotation = descriptor.findAnnotation(annotationClass);
		return annotation != null;
	}

	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		A annotation = descriptor.findAnnotation(annotationClass);
		if (annotation == null) {
			throw new RuntimeException(getShortDescription() + " is not annotated with '" + annotationClass.getName() + "'");
		}
		return annotation;
	}

	public String getShortDescription() {
		return PropertyUtils.shortDescription(bean.getClass(), getPropertyName());
	}

	private String getBeanClassName() {
		return bean.getClass().getName();
	}

	public String getDescription() {
		return decriptionWithExtraInformation(null);
	}

	public String decriptionWithExtraInformation(String extraInformation) {
		String propertyDetails = MessageFormat.format("property(name:{0}, propertyType:{1}, isReadable:{2}, isWritable:{3}, beanType:{4}", getPropertyName(),
				getPropertyType().getName(), descriptor.isReadable(), descriptor.isWritable(), getBeanClassName());

		if (!Strings.isNullOrEmpty(extraInformation)) {
			propertyDetails += ", " + extraInformation;
		}
		propertyDetails += ")";
		return propertyDetails;
	}
}
