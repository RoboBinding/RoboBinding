package robobinding.value;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import robobinding.beans.ExtendedPropertyChangeSupport;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public abstract class AbstractValueModel0<T> implements ValueModel<T>
{
	public static final String PROPERTY_VALUE = "value";
	private ExtendedPropertyChangeSupport propertyChangeSupport;
	public AbstractValueModel0(boolean checkIdentity)
	{
		propertyChangeSupport = new ExtendedPropertyChangeSupport(this, checkIdentity);
	}
	@Override
	public final void addValueChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(PROPERTY_VALUE, listener);
	}

	@Override
	public final void removeValueChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(PROPERTY_VALUE, listener);
	}

	protected final void fireValueChange(Object oldValue, Object newValue)
	{
		propertyChangeSupport.firePropertyChange(PROPERTY_VALUE, oldValue, newValue);
	}
	/**
     * @see PropertyChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
     */
	public final void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
	 */
	public final void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}
	/**
	 * @param checkIdentity true to compare the old and new value using {@code ==}, false to use {@code #equals}
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	protected final void fireValueChange(Object oldValue, Object newValue, boolean checkIdentity)
	{
		propertyChangeSupport.firePropertyChange(PROPERTY_VALUE, oldValue, newValue, checkIdentity);
	}

	/**
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(String, Object, Object)
	 */
	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	/**
	 * @param checkIdentity true to compare the old and new value using {@code ==}, false to use {@code #equals}
	 * 
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(String, Object, Object)
	 */
	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue, checkIdentity);
	}
	//
	// /**
	// * @see java.beans.PropertyChangeSupport
	// */
	// public final void fireValueChange(boolean oldValue, boolean newValue)
	// {
	// fireValueChange(Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
	// }
	//
	// /**
	// * @see java.beans.PropertyChangeSupport
	// */
	// public final void fireValueChange(int oldValue, int newValue)
	// {
	// fireValueChange(Integer.valueOf(oldValue), Integer.valueOf(newValue));
	// }
	//
	// /**
	// * @see java.beans.PropertyChangeSupport
	// */
	// public final void fireValueChange(long oldValue, long newValue)
	// {
	// fireValueChange(Long.valueOf(oldValue), Long.valueOf(newValue));
	// }
	//
	// /**
	// * @see java.beans.PropertyChangeSupport
	// */
	// public final void fireValueChange(double oldValue, double newValue)
	// {
	// fireValueChange(Double.valueOf(oldValue), Double.valueOf(newValue));
	// }
	//
	// /**
	// * @see java.beans.PropertyChangeSupport
	// */
	// public final void fireValueChange(float oldValue, float newValue)
	// {
	// fireValueChange(Float.valueOf(oldValue), Float.valueOf(newValue));
	// }

	// /**
	// * Converts this model's value and returns it as a {@code boolean}.
	// *
	// * @return the {@code boolean} value
	// * @throws ClassCastException if the model value is not of type {@code
	// Boolean}
	// * @throws NullPointerException if the model value is {@code null}
	// */
	// public final boolean booleanValue()
	// {
	// return ((Boolean)getValue()).booleanValue();
	// }
	//
	// /**
	// * Converts this model's value and returns it as a {@code double}.
	// *
	// * @return the {@code double} value
	// * @throws ClassCastException if the model value is not of type {@code
	// Double}
	// * @throws NullPointerException if the model value is {@code null}
	// */
	// public final double doubleValue()
	// {
	// return ((Double)getValue()).doubleValue();
	// }
	//
	// /**
	// * Converts this model's value and returns it as a {@code float}.
	// *
	// * @return the {@code float} value
	// * @throws ClassCastException if the model value is not of type {@code
	// Float}
	// * @throws NullPointerException if the model value is {@code null}
	// */
	// public final float floatValue()
	// {
	// return ((Float)getValue()).floatValue();
	// }
	//
	// /**
	// * Converts this model's value and returns it as an {@code int}.
	// *
	// * @return the {@code int} value
	// * @throws ClassCastException if the model value is not of type {@code
	// Integer}
	// * @throws NullPointerException if the model value is {@code null}
	// */
	// public final int intValue()
	// {
	// return ((Integer)getValue()).intValue();
	// }
	//
	// /**
	// * Converts this model's value and returns it as a {@code long}.
	// *
	// * @return the {@code long} value
	// * @throws ClassCastException if the model value is not of type {@code
	// Long}
	// * @throws NullPointerException if the model value is {@code null}
	// */
	// public final long longValue()
	// {
	// return ((Long)getValue()).longValue();
	// }
	//
	// /**
	// * Converts this model's value and returns it as a {@code String}.
	// *
	// * @return this model's value as String
	// * @throws ClassCastException if the model value is not of type {@code
	// String}
	// */
	// public String getStringValue()
	// {
	// return (String)getValue();
	// }
	//
	// public final void setValue(boolean b)
	// {
	// setValue(Boolean.valueOf(b));
	// }
	//
	// public final void setValue(double d)
	// {
	// setValue(Double.valueOf(d));
	// }
	//
	// public final void setValue(float f)
	// {
	// setValue(Float.valueOf(f));
	// }
	//
	// public final void setValue(int i)
	// {
	// setValue(Integer.valueOf(i));
	// }
	//
	// public final void setValue(long l)
	// {
	// setValue(Long.valueOf(l));
	// }
	//
	/**
	 * Returns a string representation of this value model.
	 * 
	 * @return a string representation of this value model
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[" + paramString() + "]";
	}

	/**
	 * Returns a string representing the state of this model. This method is
	 * intended to be used only for debugging purposes.
	 * 
	 * @return a string representation of this model's state
	 */
	protected String paramString()
	{
		return "value=" + valueString();
	}

	/**
	 * Returns a string representing the value of this model. This method is
	 * intended to be used for debugging purposes only.
	 * 
	 * @return a string representation of this model's value
	 */
	protected String valueString()
	{
		try
		{
			Object value = getValue();
			return value == null ? "null" : value.toString();
		} catch (Exception e)
		{
			return "Can't read";
		}
	}

}