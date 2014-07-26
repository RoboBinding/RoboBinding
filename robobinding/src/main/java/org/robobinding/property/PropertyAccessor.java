package org.robobinding.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.robobinding.internal.java_beans.PropertyDescriptor;

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
        checkReadable();
        try {
            return descriptor.getReadMethod().invoke(bean, (Object[]) null);
        } catch (InvocationTargetException e) {
            throw createReadAccessException(e.getCause());
        } catch (IllegalAccessException e) {
            throw createReadAccessException(e);
        }
    }

    public void checkReadable() {
        if (!isReadable()) {
            throw new RuntimeException("The " + getShortDescription() + " is not readable");
        }
    }

    private boolean isReadable() {
        return descriptor.getReadMethod() != null;
    }

    private RuntimeException createReadAccessException(Throwable cause) {
        return new RuntimeException("error when reading property '" + getShortDescription() + "'", cause);
    }

    public void setValue(Object newValue) {
        checkWritable();
        try {
            descriptor.getWriteMethod().invoke(bean, newValue);
        } catch (InvocationTargetException e) {
            throw createWriteAccessException(newValue, e.getCause());
        } catch (IllegalAccessException e) {
            throw createWriteAccessException(newValue, e);
        } catch (IllegalArgumentException e) {
            throw createWriteAccessException(newValue, e);
        }
    }

    public void checkWritable() {
        if (!isWritable()) {
            throw new RuntimeException("The " + getShortDescription() + " is not writable");
        }
    }

    private boolean isWritable() {
        return descriptor.getWriteMethod() != null;
    }

    private RuntimeException createWriteAccessException(Object newValue, Throwable cause) {
        return new RuntimeException("error when writing property '" + getShortDescription() + "' with newValue '" + newValue + "'", cause);
    }

    public Class<?> getPropertyType() {
        return descriptor.getPropertyType();
    }

    public String getPropertyName() {
	return descriptor.getName();
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
	Annotation annotation = findAnnotation(annotationClass);
	return annotation != null;
    }

    private <A extends Annotation> A findAnnotation(Class<A> annotationClass) {
        if (isReadable()) {
            Method readMethod = descriptor.getReadMethod();
            return readMethod.getAnnotation(annotationClass);
        }
        return null;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
	A annotation = findAnnotation(annotationClass);
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
        String propertyDetails = MessageFormat.format("property(name:{0}, propertyType:{1}, isReadable:{2}, isWritable:{3}, beanType:{4}",
        	getPropertyName(), getPropertyType().getName(), isReadable(), isWritable(), getBeanClassName());
        
        if (!Strings.isNullOrEmpty(extraInformation)) {
            propertyDetails += ", " + extraInformation;
        }
        propertyDetails += ")";
        return propertyDetails;
    }
}
