package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractValueModel<T> implements ValueModel<T> {
    private static final String PROPERTY_VALUE = "value";

    protected T value;
    private PresentationModelPropertyChangeSupport propertyChangeSupport;

    public AbstractValueModel(T value) {
	this.value = value;

	propertyChangeSupport = new PresentationModelPropertyChangeSupport(this);
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
	propertyChangeSupport.firePropertyChange(PROPERTY_VALUE);
    }

    @Override
    public final void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	propertyChangeSupport.addPropertyChangeListener(PROPERTY_VALUE, listener);
    }

    @Override
    public final void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	propertyChangeSupport.removePropertyChangeListener(PROPERTY_VALUE, listener);
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