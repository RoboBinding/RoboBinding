package robobinding.binding.viewattribute;

import robobinding.property.PropertyValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class ValueHolders
{
	private ValueHolders(){}
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
}
