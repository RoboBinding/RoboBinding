package robobinding.beans;

/**
 * A runtime exception that describes read and write access problems when
 * getting/setting a Java Bean property.
 */
@SuppressWarnings("serial")
public final class PropertyAccessException extends PropertyException
{
	private PropertyAccessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Creates and returns a new PropertyAccessException instance for a failed
	 * read access for the specified bean, property accessor and cause.
	 */
	public static <P> PropertyAccessException createReadAccessException(Object bean, PropertyAccessor<P> propertyAccessor, Throwable cause)
	{
		String message = propertyAccessor.describePropertyReader(bean);
		return new PropertyAccessException(message, cause);
	}

	/**
	 * Creates and returns a new PropertyAccessException instance for a failed
	 * write access for the specified bean, value, property accessor and cause.
	 */
	public static <P> PropertyAccessException createWriteAccessException(Object bean, Object propertyValue, PropertyAccessor<P> propertyAccessor, Throwable cause)
	{

		String message = propertyAccessor.describePropertyWriter(bean, propertyValue);
		return new PropertyAccessException(message, cause);
	}

}
