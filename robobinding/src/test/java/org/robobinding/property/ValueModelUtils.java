package org.robobinding.property;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
public class ValueModelUtils {
    private ValueModelUtils() {
    }

    public static ValueModel<Boolean> createBoolean(boolean b) {
	return new BooleanValueHolder(b);
    }

    public static ValueModel<Double> createDouble(double d) {
	return new DoubleValueHolder(d);
    }

    public static ValueModel<Float> createFloat(float f) {
	return new FloatValueHolder(f);
    }

    public static ValueModel<Integer> createInteger(int i) {
	return new IntegerValueHolder(i);
    }

    public static ValueModel<Long> createLong(long l) {
	return new LongValueHolder(l);
    }

    public static <T> ValueModel<T> create() {
	return new GenericValueHolder<T>(null);
    }

    public static <T> ValueModel<T> create(T o) {
	return new GenericValueHolder<T>(o);
    }

    public static <T> ValueModel<T> create(T o, boolean checkIdentity) {
	return new GenericValueHolder<T>(o, checkIdentity);
    }

    public static DataSetValueModel<Object> createDataSetValueModel(Object presentationModel, String propertyName) {
	return new PropertyCreator(presentationModel).createDataSetProperty(propertyName);
    }

    private static class BooleanValueHolder extends AbstractValueModel<Boolean> {
	public BooleanValueHolder(boolean b) {
	    super(Boolean.valueOf(b));
	}
    }

    private static class DoubleValueHolder extends AbstractValueModel<Double> {
	public DoubleValueHolder(double d) {
	    super(Double.valueOf(d));
	}
    }

    private static class FloatValueHolder extends AbstractValueModel<Float> {
	public FloatValueHolder(float f) {
	    super(Float.valueOf(f));
	}
    }

    private static class IntegerValueHolder extends AbstractValueModel<Integer> {
	public IntegerValueHolder(int i) {
	    super(Integer.valueOf(i));
	}
    }

    private static class LongValueHolder extends AbstractValueModel<Long> {
	public LongValueHolder(long l) {
	    super(Long.valueOf(l));
	}
    }

    private static class GenericValueHolder<T> extends AbstractValueModel<T> {
	public GenericValueHolder(T o, boolean checkIdentity) {
	    super(o, checkIdentity);
	}

	public GenericValueHolder(T o) {
	    super(o);
	}
    }

    private abstract static class AbstractValueModel<T> implements ValueModel<T> {
	private static final String PROPERTY_VALUE = "value";

	private T value;
	private PresentationModelPropertyChangeSupport propertyChangeSupport;

	public AbstractValueModel(T value, boolean checkIdentity) {
	    this.value = value;

	    propertyChangeSupport = new PresentationModelPropertyChangeSupport(this);
	}

	public AbstractValueModel(T value) {
	    this(value, false);
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

	private void fireValueChange() {
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

}
