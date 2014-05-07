package org.robobinding.property;

import static com.google.common.base.Preconditions.checkNotNull;

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
public final class PropertyAccessor<T> {
    private PropertyDescriptor descriptor;
    private Class<?> beanClass;

    /**
     * @throws PropertyNotFoundException
     *             when the property is not found.
     */
    public PropertyAccessor(PropertyDescriptor descriptor, Class<?> beanClass) {
	this.descriptor = descriptor;
	this.beanClass = beanClass;
	// checkPropertyType();
    }

    /*
     * private void checkPropertyType() { Class<?> klass = getClass();
     * ParameterizedType genericSuperclass =
     * (ParameterizedType)klass.getGenericSuperclass();
     * 
     * @SuppressWarnings("unchecked") Class<T> parameterizedPropertyType
     * =(Class<T>)genericSuperclass.getActualTypeArguments()[0];
     * 
     * Class<?> actualPropertyType = descriptor.getPropertyType();
     * if(!parameterizedPropertyType.isAssignableFrom(actualPropertyType)) {
     * throw new
     * RuntimeException("Expected property type is '"+parameterizedPropertyType
     * .getName()+"', but actual property type is '"+actualPropertyType+"'"); }
     * }
     */
    public String getPropertyName() {
	return descriptor.getName();
    }

    private boolean isWritable() {
	return descriptor.getWriteMethod() != null;
    }

    private boolean isReadable() {
	return descriptor.getReadMethod() != null;
    }

    public T getValue(Object bean) {
	checkNotNull(bean, "The bean must not be null.");

	checkReadable();
	try {
	    @SuppressWarnings("unchecked")
	    T result = (T) descriptor.getReadMethod().invoke(bean, (Object[]) null);
	    return result;
	} catch (InvocationTargetException e) {
	    throw createReadAccessException(e.getCause());
	} catch (IllegalAccessException e) {
	    throw createReadAccessException(e);
	}
    }

    private RuntimeException createReadAccessException(Throwable cause) {
	return new RuntimeException("error when reading property '" + propertyDescription() + "'", cause);
    }

    public void setValue(Object bean, T newValue) {
	checkNotNull(bean, "The bean must not be null.");
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

    private RuntimeException createWriteAccessException(T newValue, Throwable cause) {
	return new RuntimeException("error when writing property '" + propertyDescription() + "' with newValue '" + newValue + "'", cause);
    }

    public void checkReadable() {
	if (!isReadable()) {
	    throw new RuntimeException("The " + propertyDescription() + " is not readable");
	}
    }

    public void checkWritable() {
	if (!isWritable()) {
	    throw new RuntimeException("The " + propertyDescription() + " is not writable");
	}
    }

    public Class<?> getPropertyType() {
	return descriptor.getPropertyType();
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
	Annotation annotation = findAnnotation(annotationClass);
	return annotation != null;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
	A annotation = findAnnotation(annotationClass);
	checkNotNull(annotation, propertyDescription() + " is not annotated with '" + annotationClass.getName() + "'");
	return annotation;
    }

    private <A extends Annotation> A findAnnotation(Class<A> annotationClass) {
	if (isReadable()) {
	    Method readMethod = descriptor.getReadMethod();
	    return readMethod.getAnnotation(annotationClass);
	}
	return null;
    }

    @Override
    public String toString() {
	return toString(null);
    }

    public String toString(String additionalDescription) {
	String beanClassName = beanClass.getName();
	String propertyDescription = MessageFormat.format("property(name:{0}, propertyType:{1}, isReadable:{2}, isWritable:{3}, beanType:{4}",
		getPropertyName(), descriptor.getPropertyType(), isReadable(), isWritable(), beanClassName, additionalDescription);
	if (!Strings.isNullOrEmpty(additionalDescription)) {
	    propertyDescription += ", " + additionalDescription;
	}
	propertyDescription += ")";
	return propertyDescription;
    }

    public String propertyDescription() {
	String beanClassName = beanClass.getName();
	return MessageFormat.format("{0}.{1}", beanClassName, getPropertyName());
    }
}
