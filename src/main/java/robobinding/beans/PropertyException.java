package robobinding.beans;

@SuppressWarnings("serial")
abstract class PropertyException extends RuntimeException
{
	public PropertyException(String message)
	{
		this(message, null);
	}
	public PropertyException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
