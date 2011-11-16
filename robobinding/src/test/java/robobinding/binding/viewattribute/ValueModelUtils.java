package robobinding.binding.viewattribute;

import robobinding.property.PropertyChangeListener;
import robobinding.property.PropertyChangeSupport;
import robobinding.property.PropertyValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ValueModelUtils
{
	private ValueModelUtils(){}
	public static PropertyValueModel<Boolean> createBoolean(boolean b)
	{
		return new BooleanValueHolder(b);
	}
	public static PropertyValueModel<Double> createDouble(double d)
	{
		return new DoubleValueHolder(d);
	}
	public static PropertyValueModel<Float> createFloat(float f)
	{
		return new FloatValueHolder(f);
	}
	public static PropertyValueModel<Integer> createInteger(int i)
	{
		return new IntegerValueHolder(i);
	}
	public static PropertyValueModel<Long> createLong(long l)
	{
		return new LongValueHolder(l);
	}
	public static <T> PropertyValueModel<T> create()
	{
		return new GenericValueHolder<T>(null);
	}
	public static <T> PropertyValueModel<T> create(T o)
	{
		return new GenericValueHolder<T>(o);
	}
	public static <T> PropertyValueModel<T> create(T o, boolean checkIdentity)
	{
		return new GenericValueHolder<T>(o, checkIdentity);
	}
	private static class BooleanValueHolder extends AbstractValueModel<Boolean>
	{
		public BooleanValueHolder(boolean b)
		{
			super(Boolean.valueOf(b));
		}
	}
	private static class DoubleValueHolder extends AbstractValueModel<Double>
	{
		public DoubleValueHolder(double d)
		{
			super(Double.valueOf(d));
		}
	}
	private static class FloatValueHolder extends AbstractValueModel<Float>
	{
		public FloatValueHolder(float f)
		{
			super(Float.valueOf(f));
		}
	}
	private static class IntegerValueHolder extends AbstractValueModel<Integer>
	{
		public IntegerValueHolder(int i)
		{
			super(Integer.valueOf(i));
		}
	}
	private static class LongValueHolder extends AbstractValueModel<Long>
	{
		public LongValueHolder(long l)
		{
			super(Long.valueOf(l));
		}
	}
	private static class GenericValueHolder<T> extends AbstractValueModel<T>
	{
		public GenericValueHolder(T o, boolean checkIdentity)
		{
			super(o, checkIdentity);
		}
		public GenericValueHolder(T o)
		{
			super(o);
		}
	}
	
	private abstract static class AbstractValueModel<T> implements PropertyValueModel<T>
	{
		private static final String PROPERTY_VALUE = "value";
		
		private T value;
		private PropertyChangeSupport propertyChangeSupport;
		
		public AbstractValueModel(T value, boolean checkIdentity)
		{
			this.value = value;
			
			propertyChangeSupport = new PropertyChangeSupport(this);
		}
		public AbstractValueModel(T value)
		{
			this(value, false);
		}
		@Override
		public T getValue()
		{
			return value;
		}
		@Override
		public void setValue(T newValue)
		{
			T oldValue = getValue();
			if (oldValue == newValue)
				return;
			value = newValue;
			fireValueChange(oldValue, newValue);
		}
		private void fireValueChange(Object oldValue, Object newValue)
		{
			propertyChangeSupport.firePropertyChange(PROPERTY_VALUE);
		}
		
		@Override
		public final void addPropertyChangeListener(PropertyChangeListener listener)
		{
			propertyChangeSupport.addPropertyChangeListener(PROPERTY_VALUE, listener);
		}

		@Override
		public final void removePropertyChangeListener(PropertyChangeListener listener)
		{
			propertyChangeSupport.removePropertyChangeListener(PROPERTY_VALUE, listener);
		}
		
		@Override
		public String toString()
		{
			return getClass().getName() + "[" + paramString() + "]";
		}

		protected String paramString()
		{
			return "value=" + valueString();
		}
		
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
}
