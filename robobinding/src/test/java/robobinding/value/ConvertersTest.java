package robobinding.value;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
public class ConvertersTest
{
	@Test
	public void testInvertable()
	{
		long value = 1L;
		ValueModel<Long> source = ValueHolders.createLong(value);
		@SuppressWarnings("unchecked")
		AbstractConverter<Long, Long> converter = (AbstractConverter<Long, Long>)Converters.createLongConverter(source, 100);
		try
		{
			converter.setValue(converter.convertFromSource(value));
		}catch (RuntimeException e)
		{
			Assert.fail("Unexpected exception is thrown: " + e);
		}
	}
	@Test
	public void testBooleanNegator()
	{
		ValueModel<Boolean> source = ValueHolders.createBoolean(true);
		ValueModel<Boolean> converter = Converters.createBooleanNegator(source);
		
		assertConversions(source, Boolean.TRUE, converter, Boolean.FALSE);
		assertConversions(source, Boolean.FALSE, converter, Boolean.TRUE);
		assertConversions(source, null, converter, null);
	}

	@Test
	public void testBooleanToStringConverter()
	{
		String trueText = "true";
		String falseText = "false";
		String nullText = "unknown";
		ValueModel<Boolean> source = ValueHolders.createBoolean(Boolean.TRUE);
		ValueModel<String> converter = Converters.createBooleanToStringConverter(source, trueText, falseText, nullText);
		
		assertConversions(source, Boolean.TRUE, converter, trueText);
		assertConversions(source, Boolean.FALSE, converter, falseText);
		assertConversions(source, null, converter, nullText);
	}
	@Test
	public void testDoubleConverter()
	{
		double value = 1.0;
		double convertedValue = 100;
		ValueModel<Double> source = ValueHolders.createDouble(value);
		ValueModel<Double> converter = Converters.createDoubleConverter(source, 100);
		
		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testDoubleToIntegerConverter()
	{
		double value = 100.0;
		int convertedValue = 100;
		ValueModel<Double> source = ValueHolders.createDouble(value);
		ValueModel<Integer> converter = Converters.createDoubleToIntegerConverter(source, 1);
		
		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testFloatConverter()
	{
		float value = 1.0f;
		float convertedValue = 100f;
		ValueModel<Float> source = ValueHolders.createFloat(value);
		ValueModel<Float> converter = Converters.createFloatConverter(source, 100);
		
		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testFloatToIntegerConverter()
	{
		float value = 100.0f;
		int convertedValue = 100;
		ValueModel<Float> source = ValueHolders.createFloat(value);
		ValueModel<Integer> converter = Converters.createFloatToIntegerConverter(source, 1);
		
		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testIntegerConverter()
	{
		int value = 1;
		int convertedValue = 100;
		ValueModel<Integer> source = ValueHolders.createInteger(value);
		ValueModel<Integer> converter = Converters.createIntegerConverter(source, 100);

		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testLongConverter()
	{
		long value = 1L;
		long convertedValue = 100L;
		ValueModel<Long> source = ValueHolders.createLong(value);
		ValueModel<Long> converter = Converters.createLongConverter(source, 100);
		
		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testLongToIntegerConverter()
	{
		long value = 100L;
		int convertedValue = 100;
		ValueModel<Long> source = ValueHolders.createLong(value);
		ValueModel<Integer> converter = Converters.createLongToIntegerConverter(source, 1);

		assertConversions(source, value, converter, convertedValue);
	}
	@Test
	public void testStringConverter()
	{
		long value = 100L;
		String convertedValue = "100";
		DecimalFormat format = new DecimalFormat("#", new DecimalFormatSymbols(Locale.US));
		format.setParseIntegerOnly(true);
		ValueModel<Long> source = ValueHolders.createLong(value);
		ValueModel<String> converter = Converters.createStringConverter(source, format);

		assertConversions(source, value, converter, convertedValue);
	}

	private <S,D> void assertConversions(ValueModel<S> source, S soruceValue, ValueModel<D> converter, D convertedValue)
	{
		source.setValue(soruceValue);
		Assert.assertEquals("The value returned by converter is not the expected one:", convertedValue, converter.getValue());
		converter.setValue(convertedValue);
		Assert.assertEquals("The value returned by subject is not the expected one:", soruceValue, source.getValue());
	}

}
