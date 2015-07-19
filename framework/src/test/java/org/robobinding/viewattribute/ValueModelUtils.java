package org.robobinding.viewattribute;

import org.robobinding.property.AbstractValueModel;
import org.robobinding.property.ValueModel;

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
		public GenericValueHolder(T o) {
			super(o);
		}
	}

}
