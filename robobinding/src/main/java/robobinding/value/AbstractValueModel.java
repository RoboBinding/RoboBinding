package robobinding.value;


public abstract class AbstractValueModel<T> extends AbstractValueModel0<T> implements ValueModel<T>
{
	private T value;
	public AbstractValueModel(T value, boolean checkIdentity)
	{
		super(checkIdentity);
		
		this.value = value;
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
	/**
	 * Sets a new value. Fires a value change event if the old and new value
	 * differ. The difference is tested with {@code ==} if
	 * {@code isIdentityCheckEnabled} answers {@code true}. The values are
	 * compared with {@code #equals} if the identity check is disabled.
	 */
	@Override
	public void setValue(T newValue)
	{
		T oldValue = getValue();
		if (oldValue == newValue)
			return;
		value = newValue;
		fireValueChange(oldValue, newValue);
	}
}
