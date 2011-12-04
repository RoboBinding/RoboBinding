/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

import robobinding.internal.java_beans.PropertyDescriptor;
import robobinding.internal.org_apache_commons_lang3.StringUtils;
import robobinding.internal.org_apache_commons_lang3.Validate;

/**
*
* @since 1.0
* @version $Revision: 1.0 $
* @author Cheng Wei
*/
public final class PropertyAccessor<T>
{
	private PropertyDescriptor descriptor;
	private Class<?> beanClass;
	/**
	 * @throws PropertyNotFoundException when the property is not found.
	 */
	public PropertyAccessor(PropertyDescriptor descriptor, Class<?> beanClass)
	{
		this.descriptor = descriptor;
		this.beanClass = beanClass;
		//checkPropertyType();
	}
	/*
	private void checkPropertyType()
	{
    	Class<?> klass = getClass();
    	ParameterizedType genericSuperclass = (ParameterizedType)klass.getGenericSuperclass();
	    @SuppressWarnings("unchecked")
    	Class<T> parameterizedPropertyType =(Class<T>)genericSuperclass.getActualTypeArguments()[0];
	    
	    Class<?> actualPropertyType = descriptor.getPropertyType();
	    if(!parameterizedPropertyType.isAssignableFrom(actualPropertyType))
	    {
	    	throw new RuntimeException("Expected property type is '"+parameterizedPropertyType.getName()+"', but actual property type is '"+actualPropertyType+"'");
	    }
	}*/
	public String getPropertyName()
	{
		return descriptor.getName();
	}
	private boolean isWritable()
	{
		return descriptor.getWriteMethod() != null;
	}
	private boolean isReadable()
	{
		return descriptor.getReadMethod() != null;
	}
	public T getValue(Object bean)
	{
		Validate.notNull(bean, "The bean must not be null.");
		
		checkReadable();
		try
		{
			@SuppressWarnings("unchecked")
			T result = (T)descriptor.getReadMethod().invoke(bean, (Object[])null);
			return result;
		} catch (InvocationTargetException e)
		{
			throw createReadAccessException(e.getCause());
		} catch (IllegalAccessException e)
		{
			throw createReadAccessException(e);
		}
	}
	private RuntimeException createReadAccessException(Throwable cause)
	{
		return new RuntimeException("error when reading the '"+toString()+"'", cause);
	}
	public void setValue(Object bean, T newValue)
	{
		Validate.notNull(bean, "The bean must not be null.");
		checkWritable();
		try
		{
			descriptor.getWriteMethod().invoke(bean, newValue);
		} catch (InvocationTargetException e)
		{
			throw createWriteAccessException(newValue, e.getCause());
		} catch (IllegalAccessException e)
		{
			throw createWriteAccessException(newValue, e);
		} catch (IllegalArgumentException e)
		{
			throw createWriteAccessException(newValue, e);
		}
	}
	private RuntimeException createWriteAccessException(T newValue, Throwable cause)
	{
		return new RuntimeException("error when writing the '"+toString()+"' with newValue '"+newValue+"'", cause);
	}
	public void checkReadable()
	{
		if(!isReadable())
		{
			throw new RuntimeException("The "+toString()+" is not readable");
		}
	}
	public void checkWritable()
	{
		if(!isWritable())
		{
			throw new RuntimeException("The "+toString()+" is not writable");
		}
	}
	public Class<?> getPropertyType()
	{
		return descriptor.getPropertyType();
	}
	public boolean hasAnnotation(Class<? extends Annotation> annotationClass)
	{
		Annotation annotation = findAnnotation(annotationClass);
		return annotation != null;
	}
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
	{
		A annotation = findAnnotation(annotationClass);
		Validate.notNull(annotation, toString()+" is not annotated with '"+annotationClass.getName()+"'");
		return annotation;
	}
	private <A extends Annotation> A findAnnotation(Class<A> annotationClass)
	{
		if(isReadable())
		{
			Method readMethod = descriptor.getReadMethod();
			return readMethod.getAnnotation(annotationClass);
		}
		return null;
	}
	@Override
	public String toString()
	{
		return toString(null);
	}
	public String toString(String additionalDescription)
	{
		String beanClassName = beanClass.getName();
		String propertyDescription = MessageFormat.format("property(name:{0}, propertyType:{1}, isReadable:{2}, isWritable:{3}, beanType:{4}", 
				getPropertyName(), descriptor.getPropertyType(), isReadable(), isWritable(), beanClassName, additionalDescription);
		if(StringUtils.isNotBlank(additionalDescription))
		{
			propertyDescription += ", "+additionalDescription;
		}
		propertyDescription += ")";
		return propertyDescription;
	}
}
