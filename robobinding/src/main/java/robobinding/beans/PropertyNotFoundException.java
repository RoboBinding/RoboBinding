package robobinding.beans;

@SuppressWarnings("serial")
public final class PropertyNotFoundException extends PropertyException
{
	public PropertyNotFoundException(String propertyName, Class<?> beanClass, Throwable cause)
	{
		super("Property '" + propertyName + "' not found in bean class " + beanClass, cause);
	}

}
