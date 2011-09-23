package robobinding.value;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import robobinding.utils.Validate;

/**
 * Converts value from type {@code S} to type {@code D}. 
 * {@code S} - source type. 
 * {@code D} - destination type.
 */
public abstract class AbstractConverter<S, D> extends AbstractValueModel0<D> implements ValueModel<D>
{
	private ValueModel<S> source;
	private PropertyChangeListener listener;
	/**
	 * @param source the ValueModel that holds the source value
	 */
	public AbstractConverter(ValueModel<S> source)
	{
		super(false);
		
		Validate.notNull(source);
		this.source = source;
		
		listener = new ValueChangeHandler();
		this.source.addValueChangeListener(listener);
	}
	/**
	 * Converts a value from the source type to the destination type or format used by this converter.
	 * 
	 * @param sourceValue the source's value
	 * @return the converted value in the destination type or format used by this converter
	 */
	public abstract D convertFromSource(S sourceValue);
	public abstract S convertFromDestination(D destinationValue);
	/**
	 * Converts the source's value and returns the converted value.
	 * 
	 * @return the converted value
	 */
	@Override
	public D getValue()
	{
		return convertFromSource(source.getValue());
	}
	@Override
	public void setValue(D newValue)
	{
		source.setValue(convertFromDestination(newValue));
	}
	
	/**
	 * Listens to value changes in the source, converts the old and new
	 * value - if any - and fires a value change for this converter.
	 */
	private final class ValueChangeHandler implements PropertyChangeListener
	{

		/**
		 * Notifies listeners about a change in the underlying source. The old
		 * and new value used in the PropertyChangeEvent to be fired are
		 * converted versions of the observed old and new values. The observed
		 * old and new value are converted only if they are non-null. This is
		 * because {@code null} may be a valid value or may indicate
		 * <em>not available</em>.
		 * <p>
		 * 
		 * @param event the property change event to be handled.
		 */
		public void propertyChange(PropertyChangeEvent event)
		{
			@SuppressWarnings("unchecked")
			Object convertedOldValue = event.getOldValue() == null ? null : convertFromSource((S)event.getOldValue());
			@SuppressWarnings("unchecked")
			Object convertedNewValue = event.getNewValue() == null ? null : convertFromSource((S)event.getNewValue());
			fireValueChange(convertedOldValue, convertedNewValue);
		}

	}

}
