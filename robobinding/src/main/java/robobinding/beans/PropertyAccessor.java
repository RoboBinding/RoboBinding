package robobinding.beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;

import robobinding.utils.Validate;

final class PropertyAccessor<T>
{
	private static final String NONE = "None";
	
	private PropertyDescriptor descriptor;
	private Class<?> beanClass;
	/**
	 * @throws PropertyNotFoundException when the property is not found.
	 */
	public PropertyAccessor(String propertyName, Class<?> beanClass)
	{
		try
		{
			descriptor = new PropertyDescriptor(propertyName, beanClass);
		} catch (IntrospectionException e)
		{
			throw new PropertyNotFoundException(propertyName, beanClass, e);
		}
		this.beanClass = beanClass;
		checkPropertyType();
	}
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
	}
	public String getPropertyName()
	{
		return descriptor.getName();
	}
	private boolean isWritable()
	{
		return descriptor.getWriteMethod() != null;
	}
	private String safeGetWriteMethodName()
	{
		if(isWritable())
		{
			return descriptor.getWriteMethod().getName();
		}else
		{
			return NONE;
		}
	}
	private boolean isReadable()
	{
		return descriptor.getReadMethod() != null;
	}
	private String safeGetReadMethodName()
	{
		if(isReadable())
		{
			return descriptor.getReadMethod().getName();
		}else
		{
			return NONE;
		}
	}
	public String describeProperty(Object bean)
	{
		Object value = safeGetValue(bean);
		String beanType = bean.getClass().getName();
		String setter = safeGetWriteMethodName();
		String getter = safeGetReadMethodName();
		return MessageFormat.format("property(name:{0}, value:{1}, propertyType:{2}, setter:{3}, getter:{4}, beanType:{5}", 
				getPropertyName(), value, descriptor.getPropertyType(), setter, getter, beanType);
	}
	public String describePropertyGetter(Object bean)
	{
		String beanType = bean.getClass().getName();
		String getter = safeGetReadMethodName();
		return MessageFormat.format("property(name:{0}, getter:{1}, beanType:{2})", 
				getPropertyName(), getter, beanType);
	}
	public String describePropertySetter(Object bean, Object propertyValue)
	{
		String beanType = bean.getClass().getName();
		String setter = safeGetWriteMethodName();
		return MessageFormat.format("property(name:{0}, value:{1}, setter:{2}, beanType:{3})", 
				getPropertyName(), propertyValue, setter, beanType);
	}
	private T safeGetValue(Object bean)
	{
		try
		{
			return getValue(bean);
		}catch(UnsupportedOperationException e)
		{
			return null;
		}catch(PropertyAccessException e)
		{
			return null;
		}
	}
	/**
	 * @throws UnsupportedOperationException if the property is write-only.
	 * @throws PropertyAccessException if an error occurs when accessing the property.
	 */
	public T getValue(Object bean)
	{
		Validate.notNull(bean, "The bean must not be null.");
		
		if (isReadable())
		{
			throw new UnsupportedOperationException("The property '" + getPropertyName() + "' is write-only.");
		}
		try
		{
			@SuppressWarnings("unchecked")
			T result = (T)descriptor.getReadMethod().invoke(bean, (Object[])null);
			return result;
		} catch (InvocationTargetException e)
		{
			throw PropertyAccessException.createReadAccessException(bean, this, e.getCause());
		} catch (IllegalAccessException e)
		{
			throw PropertyAccessException.createReadAccessException(bean, this, e);
		}
	}

	/**
	 * @throws UnsupportedOperationException if the property is write-only.
	 * @throws PropertyAccessException if an error occurs when accessing the property.
	 */
	public void setValue(Object bean, T newValue)
	{
		Validate.notNull(bean, "The bean must not be null.");
		if(isWritable())
		{
			throw new UnsupportedOperationException("The property '" + getPropertyName() + "' is read-only.");
		}
		try
		{
			descriptor.getWriteMethod().invoke(bean, newValue);
		} catch (InvocationTargetException e)
		{
			throw PropertyAccessException.createWriteAccessException(bean, newValue, this, e.getCause());
		} catch (IllegalAccessException e)
		{
			throw PropertyAccessException.createWriteAccessException(bean, newValue, this, e);
		} catch (IllegalArgumentException e)
		{
			throw PropertyAccessException.createWriteAccessException(bean, newValue, this, e);
		}
	}
	public void checkReadable()
	{
		if(!isReadable())
		{
			throw new RuntimeException(toString()+" is not readable");
		}
	}
	public void checkWritable()
	{
		if(!isReadable())
		{
			throw new RuntimeException(toString()+" is not writable");
		}
	}
	@Override
	public String toString()
	{
		return describeProperty(beanClass, getPropertyName());
	}
	static String describeProperty(Class<?> beanClass, String propertyName)
	{
		String beanType = beanClass.getName();
		return MessageFormat.format("property(name:{0}, beanType:{1})", propertyName, beanType);
	}
	public Class<?> getPropertyType()
	{
		return descriptor.getPropertyType();
	}
}
