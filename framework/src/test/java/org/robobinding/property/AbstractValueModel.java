package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractValueModel<T> implements ValueModel<T> {
	protected T value;
	private PropertyChangeListeners propertyChangeListeners;

	public AbstractValueModel(T value) {
		this.value = value;

		propertyChangeListeners = new PropertyChangeListeners();
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T newValue) {
		value = newValue;
		fireValueChange();
	}

	protected void fireValueChange() {
		propertyChangeListeners.firePropertyChange();
	}

	@Override
	public final void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeListeners.add(listener);
	}

	@Override
	public final void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeListeners.remove(listener);
	}

	@Override
	public String toString() {
		return getClass().getName() + "[" + paramString() + "]";
	}

	protected String paramString() {
		return "value=" + valueString();
	}

	protected String valueString() {
		try {
			Object value = getValue();
			return value == null ? "null" : value.toString();
		} catch (Exception e) {
			return "Can't read";
		}
	}
}