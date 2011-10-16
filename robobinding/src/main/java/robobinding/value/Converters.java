/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.value;

import java.text.Format;
import java.text.ParseException;

import robobinding.utils.Validate;
/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 *
 */
public class Converters
{

	private Converters(){}

	/**
	 * Creates and returns a ValueModel that negates Booleans and leaves null unchanged.
	 */
	public static ValueModel<Boolean> createBooleanNegator(ValueModel<Boolean> source)
	{
		return new BooleanNegator(source);
	}

	/**
	 * Creates and returns a ValueModel that converts Booleans to the associated
	 * of the two specified strings, and vice versa. Null values are mapped to
	 * an empty string. Ignores cases when setting a text.
	 * <p>
	 * 
	 * @throws IllegalArgumentException if the trueText equals the falseText.
	 */
	public static ValueModel<String> createBooleanToStringConverter(ValueModel<Boolean> source, String trueText, String falseText)
	{
		return createBooleanToStringConverter(source, trueText, falseText, "");
	}

	/**
	 * Creates and returns a ValueModel that converts Booleans to the associated
	 * of the two specified strings, and vice versa. Null values are mapped to
	 * the specified text. Ignores cases when setting a text.
	 * <p>
	 * 
	 * @throws IllegalArgumentException if the trueText equals the falseText
	 */
	public static ValueModel<String> createBooleanToStringConverter(ValueModel<Boolean> source, String trueText, String falseText, String nullText)
	{
		return new BooleanToStringConverter(source, trueText, falseText, nullText);
	}

	/**
	 * Creates and returns a ValueModel that converts Doubles using the
	 * specified multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Double(1.23) -> Double(123), multiplier=1000,
	 * Double(1.23) -> Double(1230)
	 * <p>
	 * 
	 */
	public static ValueModel<Double> createDoubleConverter(ValueModel<Double> source, double multiplier)
	{
		return new DoubleConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Doubles to Integer, and
	 * vice versa.
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createDoubleToIntegerConverter(ValueModel<Double> source)
	{
		return createDoubleToIntegerConverter(source, 1);
	}

	/**
	 * Creates and returns a ValueModel that converts Doubles to Integer, and
	 * vice versa. The multiplier can be used to convert Doubles to percent,
	 * permill, etc. For a percentage, set the multiplier to be 100, for a
	 * permill, set the multiplier to be 1000.
	 * <p>
	 * 
	 * Examples: multiplier=100, Double(1.23) -> Integer(123), multiplier=1000,
	 * Double(1.23) -> Integer(1230)
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createDoubleToIntegerConverter(ValueModel<Double> source, double multiplier)
	{
		return new DoubleToIntegerConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Floats using the specified
	 * multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Float(1.23) -> Float(123), multiplier=1000,
	 * Float(1.23) -> Float(1230)
	 * <p>
	 * 
	 */
	public static ValueModel<Float> createFloatConverter(ValueModel<Float> source, float multiplier)
	{
		return new FloatConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Floats to Integer, and
	 * vice versa.
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createFloatToIntegerConverter(ValueModel<Float> source)
	{
		return createFloatToIntegerConverter(source, 1);
	}

	/**
	 * Creates and returns a ValueModel that converts Floats to Integer, and
	 * vice versa. The multiplier can be used to convert Floats to percent,
	 * permill, etc. For a percentage, set the multiplier to be 100, for a
	 * permill, set the multiplier to be 1000.
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createFloatToIntegerConverter(ValueModel<Float> source, float multiplier)
	{
		return new FloatToIntegerConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Integers using the
	 * specified multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Integer(3) -> Integer(300), multiplier=1000,
	 * Integer(3) -> Integer(3000)
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createIntegerConverter(ValueModel<Integer> source, double multiplier)
	{
		return new IntegerConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Long using the specified
	 * multiplier.
	 * <p>
	 * 
	 * Examples: multiplier=100, Long(3) -> Long(300), multiplier=1000, Long(3)
	 * -> Long(3000)
	 * <p>
	 * 
	 */
	public static ValueModel<Long> createLongConverter(ValueModel<Long> source, double multiplier)
	{
		return new LongConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts Longs to Integer and vice
	 * versa.
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createLongToIntegerConverter(ValueModel<Long> source)
	{
		return createLongToIntegerConverter(source, 1);
	}

	/**
	 * Creates and returns a ValueModel that converts Longs to Integer and vice
	 * versa.
	 * <p>
	 * 
	 */
	public static ValueModel<Integer> createLongToIntegerConverter(ValueModel<Long> source, int multiplier)
	{
		return new LongToIntegerConverter(source, multiplier);
	}

	/**
	 * Creates and returns a ValueModel that converts objects to Strings and
	 * vice versa. The conversion is performed by a {@code Format}.
	 * <p>
	 * 
	 */
	public static <T extends Object> ValueModel<String> createStringConverter(ValueModel<T> source, Format format)
	{
		return new StringConverter<T>(source, format);
	}

	/**
	 * @see Converters#createBooleanNegator(ValueModel)
	 */
	private static final class BooleanNegator extends AbstractConverter<Boolean, Boolean>
	{
		public BooleanNegator(ValueModel<Boolean> source)
		{
			super(source);
		}
		@Override
		public Boolean convertFromSource(Boolean sourceValue)
		{
			return negate(sourceValue);
		}
		@Override
		public Boolean convertFromDestination(Boolean destinationValue)
		{
			return negate(destinationValue);
		}
		private static Boolean negate(Boolean value)
		{
			if(Boolean.TRUE.equals(value))
			{
				return Boolean.FALSE;
			}else if (Boolean.FALSE.equals(value))
			{
				return Boolean.TRUE;
			}else
			{
				return null;
			}
		}
	}

	/**
	 * @see Converters#createBooleanToStringConverter(ValueModel, String, String, String)
	 */
	private static final class BooleanToStringConverter extends AbstractConverter<Boolean, String>
	{
		private final String trueText;
		private final String falseText;
		private final String nullText;

		public BooleanToStringConverter(ValueModel<Boolean> source, String trueText, String falseText, String nullText)
		{
			super(source);
			Validate.notNull(trueText, "The trueText must not be null.");
			Validate.notNull(falseText, "The falseText must not be null.");
			Validate.notNull(nullText, "The nullText must not be null.");
			Validate.isFalse(trueText.equals(falseText), "The trueText and falseText must be different.");
			
			this.trueText = trueText;
			this.falseText = falseText;
			this.nullText = nullText;
		}

		@Override
		public String convertFromSource(Boolean sourceValue)
		{
			if(Boolean.TRUE.equals(sourceValue))
			{
				return trueText;
			}else if (Boolean.FALSE.equals(sourceValue))
			{
				return falseText;
			}else
			{
				return nullText;
			}
		}

		@Override
		public Boolean convertFromDestination(String destinationValue)
		{
			if(trueText.equalsIgnoreCase(destinationValue))
			{
				return Boolean.TRUE;
			}else if (falseText.equalsIgnoreCase(destinationValue))
			{
				return Boolean.FALSE;
			}else
			{
				return null;
			}
		}
	}

	/**
	 * @see Converters#createDoubleConverter(ValueModel, double);
	 */
	private static final class DoubleConverter extends AbstractConverter<Double, Double>
	{
		private final double multiplier;

		public DoubleConverter(ValueModel<Double> source, double multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}
		
		@Override
		public Double convertFromSource(Double sourceValue)
		{
			double doubleValue = sourceValue.doubleValue();
			return Double.valueOf(doubleValue * multiplier);
		}

		@Override
		public Double convertFromDestination(Double destinationValue)
		{
			double doubleValue = destinationValue.doubleValue();
			return Double.valueOf(doubleValue / multiplier);
		}
	}

	/**
	 * @see Converters#createDoubleToIntegerConverter(ValueModel, int)
	 */
	private static final class DoubleToIntegerConverter extends AbstractConverter<Double, Integer>
	{

		private final double multiplier;

		public DoubleToIntegerConverter(ValueModel<Double> source, double multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}

		@Override
		public Integer convertFromSource(Double sourceValue)
		{
			double doubleValue = sourceValue.doubleValue();
			if (multiplier != 1)
			{
				doubleValue *= multiplier;
			}
			return Integer.valueOf((int)Math.round(doubleValue));
		}

		@Override
		public Double convertFromDestination(Integer destinationValue)
		{
			double doubleValue = destinationValue.doubleValue();
			if (multiplier != 1)
			{
				doubleValue /= multiplier;
			}
			return Double.valueOf(doubleValue);
		}
	}

	/**
	 * @see Converters#createFloatConverter(ValueModel, float)
	 */
	private static final class FloatConverter extends AbstractConverter<Float, Float>
	{
		private final float multiplier;

		public FloatConverter(ValueModel<Float> source, float multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}

		@Override
		public Float convertFromSource(Float sourceValue)
		{
			float floatValue = sourceValue.floatValue();
			return Float.valueOf(floatValue * multiplier);
		}

		@Override
		public Float convertFromDestination(Float destinationValue)
		{
			float floatValue = destinationValue.floatValue();
			return Float.valueOf(floatValue / multiplier);
		}
	}

	/**
	 * @see Converters#createFloatToIntegerConverter(ValueModel, int)
	 */
	private static final class FloatToIntegerConverter extends AbstractConverter<Float, Integer>
	{
		private final float multiplier;

		public FloatToIntegerConverter(ValueModel<Float> source, float multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}

		@Override
		public Integer convertFromSource(Float sourceValue)
		{
			float floatValue = sourceValue.floatValue();
			if (multiplier != 1)
			{
				floatValue *= multiplier;
			}
			return Integer.valueOf(Math.round(floatValue));
		}

		@Override
		public Float convertFromDestination(Integer destinationValue)
		{
			float floatValue = destinationValue.floatValue();
			if (multiplier != 1)
			{
				floatValue /= multiplier;
			}
			return Float.valueOf(floatValue);
		}
	}

	/**
	 * @see Converters#createIntegerConverter(ValueModel, double)
	 */
	private static final class IntegerConverter extends AbstractConverter<Integer, Integer>
	{
		private final double multiplier;

		public IntegerConverter(ValueModel<Integer> source, double multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}
		@Override
		public Integer convertFromSource(Integer sourceValue)
		{
			double doubleValue = sourceValue.doubleValue();
			return Integer.valueOf((int) (doubleValue * multiplier));
		}

		@Override
		public Integer convertFromDestination(Integer destinationValue)
		{
			double doubleValue = destinationValue.doubleValue();
			return Integer.valueOf((int) (doubleValue / multiplier));
		}

	}

	/**
	 * @see Converters#createLongConverter(ValueModel, double)
	 */
	private static final class LongConverter extends AbstractConverter<Long, Long>
	{
		private final double multiplier;
	
		public LongConverter(ValueModel<Long> source, double multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}
	
		@Override
		public Long convertFromSource(Long sourceValue)
		{
			double doubleValue = sourceValue.doubleValue();
			return Long.valueOf((long) (doubleValue * multiplier));
		}

		@Override
		public Long convertFromDestination(Long destinationValue)
		{
			double doubleValue = destinationValue.doubleValue();
			return Long.valueOf((long) (doubleValue / multiplier));
		}
	}

	/**
	 * @see Converters#createLongToIntegerConverter(ValueModel, int)
	 */
	private static final class LongToIntegerConverter extends AbstractConverter<Long, Integer>
	{

		private final int multiplier;

		public LongToIntegerConverter(ValueModel<Long> source, int multiplier)
		{
			super(source);
			this.multiplier = multiplier;
		}
		@Override
		public Integer convertFromSource(Long sourceValue)
		{
			int intValue = sourceValue.intValue();
			if (multiplier != 1)
			{
				intValue *= multiplier;
			}
			return Integer.valueOf(intValue);
		}

		@Override
		public Long convertFromDestination(Integer destinationValue)
		{
			long longValue = destinationValue.longValue();
			if (multiplier != 1)
			{
				longValue /= multiplier;
			}
			return Long.valueOf(longValue);
		}
	}

	/**
	 * @see Converters#createStringConverter(ValueModel, Format)
	 */
	private static final class StringConverter<T extends Object> extends AbstractConverter<T, String>
	{
		private final Format format;
		public StringConverter(ValueModel<T> source, Format format)
		{
			super(source);
			
			Validate.notNull(format, "The format must not be null.");
			this.format = format;
		}

		@Override
		public String convertFromSource(Object sourceValue)
		{
			return format.format(sourceValue);
		}

		@SuppressWarnings("unchecked")
		@Override
		public T convertFromDestination(String destinationValue)
		{
			try
			{
				return (T)format.parseObject(destinationValue);
			} catch (ParseException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

}
