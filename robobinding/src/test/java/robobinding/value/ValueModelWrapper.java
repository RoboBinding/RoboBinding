package robobinding.value;

import java.beans.PropertyChangeListener;

import robobinding.utils.Validate;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ValueModelWrapper<T> implements ValueModel<T>
{
	private ValueModel<T> forwarding;
	public ValueModelWrapper(ValueModel<T> forwarding)
	{
		Validate.notNull(forwarding);
		
		this.forwarding = forwarding;
	}
	@Override
	public void addValueChangeListener(PropertyChangeListener listener)
	{
		forwarding.addValueChangeListener(listener);
	}
	@Override
	public T getValue()
	{
		return forwarding.getValue();
	}
	@Override
	public void removeValueChangeListener(PropertyChangeListener listener)
	{
		forwarding.removeValueChangeListener(listener);
	}
	@Override
	public void setValue(T newValue) 
	{
		forwarding.setValue(newValue);
	}
	protected ValueModel<T> getForwarding()
	{
		return forwarding;
	}
}
