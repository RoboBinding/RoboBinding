package org.robobinding.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyDescriptor {
    private final Class<?> beanClass;
    private final org.robobinding.internal.java_beans.PropertyDescriptor descriptor;
    private final Getter getter;
    private final Setter setter;

    public PropertyDescriptor(Class<?> beanClass, org.robobinding.internal.java_beans.PropertyDescriptor descriptor) {
	this.beanClass = beanClass;
	this.descriptor = descriptor;
	getter = new Getter();
	setter = new Setter();
    }

    public Object doGet(Object bean) {
	return getter.doGet(bean);
    }

    private RuntimeException createReadAccessException(Throwable cause) {
	return new RuntimeException("error when reading property '" + getShortDescription() + "'", cause);
    }

    private String getShortDescription() {
	return PropertyUtils.shortDescription(beanClass, descriptor.getName());
    }

    public void doSet(Object bean, Object newValue) {
	setter.doSet(bean, newValue);
    }

    private RuntimeException createWriteAccessException(Object newValue, Throwable cause) {
	return new RuntimeException("error when writing property '" + getShortDescription() + "' with newValue '" + newValue + "'", cause);
    }

    public boolean isReadable() {
	return descriptor.getReadMethod() != null;
    }

    public boolean isWritable() {
	return descriptor.getWriteMethod() != null;
    }

    public void checkReadable() {
	if (!isReadable()) {
	    throw new RuntimeException("The " + getShortDescription() + " is not readable");
	}
    }

    public void checkWritable() {
	if (!isWritable()) {
	    throw new RuntimeException("The " + getShortDescription() + " is not writable");
	}
    }

    public Class<?> getPropertyType() {
	return descriptor.getPropertyType();
    }

    public String getName() {
	return descriptor.getName();
    }

    public <A extends Annotation> A findAnnotation(Class<A> annotationClass) {
	if (isReadable()) {
	    Method readMethod = descriptor.getReadMethod();
	    return readMethod.getAnnotation(annotationClass);
	}
	return null;
    }

    public Getter getGetter() {
	return getter;
    }

    public Setter getSetter() {
	return setter;
    }

    public class Getter {
	private Getter() {
	}

	public Object doGet(Object bean) {
	    try {
		return descriptor.getReadMethod().invoke(bean, (Object[]) null);
	    } catch (InvocationTargetException e) {
		throw createReadAccessException(e.getCause());
	    } catch (IllegalAccessException e) {
		throw createReadAccessException(e);
	    }
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
		    throw new IllegalArgumentException(MessageFormat.format(
			    "Unexpected parameter type ''{0}'' for the setter ''{1}''", 
			    actualParameterType.getName(), descriptor.getWriteMethod().toString()), 
			    e);
		} else {
		    throw e;
		}
	    }
	}
	
	private boolean isParameterTypeMismatch(Object actualParameter) {
	    if (actualParameter == null) return false;
	    
	    Class<?> parameterType = getParameterType();
	    return !parameterType.isAssignableFrom(actualParameter.getClass());
	}
	
	private Class<?> getParameterType() {
	    return descriptor.getWriteMethod().getParameterTypes()[0];
	}
    }
}
