package robobinding.beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import robobinding.utils.Validate;

class PropertyAccessor<P>
{
	private static final String NONE = "None";
	
	private PropertyDescriptor descriptor;
	private Class<?> beanClass;
	/**
	 * @throws PropertyNotFoundException when the property is not found.
	 */
	public PropertyAccessor(String propertyName, Class<?> beanClass, String readMethodName, String writeMethodName)
	{
		try
		{
			descriptor = new PropertyDescriptor(propertyName, beanClass, readMethodName, writeMethodName);
		} catch (IntrospectionException e)
		{
			throw new PropertyNotFoundException(propertyName, beanClass, e);
		}
		this.beanClass = beanClass;
	}
	private String getPropertyName()
	{
		return descriptor.getName();
	}
	public boolean isReadOnly()
	{
		return descriptor.getWriteMethod() == null;
	}
	private String getWriteMethodName()
	{
		if(isReadOnly())
		{
			return NONE;
		}else
		{
			return descriptor.getWriteMethod().getName();
		}
	}
	public boolean isWriteOnly()
	{
		return descriptor.getReadMethod() == null;
	}
	private String getReadMethodName()
	{
		if(isWriteOnly())
		{
			return NONE;
		}else
		{
			return descriptor.getReadMethod().getName();
		}
	}
	public String describePropertyReader(Object bean)
	{
		String beanType = bean.getClass().getName();
		String getter = getReadMethodName();
		return MessageFormat.format("property(name:{0}, getter:{1}, beanType:{2})", 
				getPropertyName(), getter, beanType);
	}
	public String describePropertyWriter(Object bean, Object propertyValue)
	{
		String beanType = bean.getClass().getName();
		String setter = getWriteMethodName();
		return MessageFormat.format("property(name:{0}, value:{1}, setter:{2}, beanType:{3})", 
				getPropertyName(), propertyValue, setter, beanType);
	}
	public String describeProperty(Object bean)
	{
		Object value = safeGetValue(bean);
		String beanType = bean.getClass().getName();
		String setter = getWriteMethodName();
		String getter = getReadMethodName();
		return MessageFormat.format("property(name:{0}, value:{1}, propertyType:{2}, setter:{3}, getter:{4}, beanType:{5}", 
				getPropertyName(), value, descriptor.getPropertyType(), setter, getter, beanType);
	}
	private P safeGetValue(Object bean)
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
	public P getValue(Object bean)
	{
		Validate.notNull(bean, "The bean must not be null.");
		
		if (isWriteOnly())
		{
			throw new UnsupportedOperationException("The property '" + getPropertyName() + "' is write-only.");
		}
		try
		{
			@SuppressWarnings("unchecked")
			P result = (P)descriptor.getReadMethod().invoke(bean, (Object[])null);
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
	 * @throws PropertyVetoException if the invoked writer throws such an exception
	 * @throws PropertyAccessException if an error occurs when accessing the property.
	 */
	public void setValue(Object bean, P newValue) throws PropertyVetoException
	{
		Validate.notNull(bean, "The bean must not be null.");
		if (isReadOnly())
		{
			throw new UnsupportedOperationException("The property '" + getPropertyName() + "' is read-only.");
		}
		try
		{
			descriptor.getWriteMethod().invoke(bean, newValue);
		} catch (InvocationTargetException e)
		{
			Throwable cause = e.getCause();
			if (cause instanceof PropertyVetoException)
			{
				throw (PropertyVetoException) cause;
			}
			throw PropertyAccessException.createWriteAccessException(bean, newValue, this, cause);
		} catch (IllegalAccessException e)
		{
			throw PropertyAccessException.createWriteAccessException(bean, newValue, this, e);
		} catch (IllegalArgumentException e)
		{
			throw PropertyAccessException.createWriteAccessException(bean, newValue, this, e);
		}
	}
	public boolean beanClassEquals(Class<?> klass)
	{
		return beanClass.equals(klass);
	}
}
