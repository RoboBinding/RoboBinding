package robobinding.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NumberUtils
{
	private NumberUtils(){}
	
	public static boolean isPositive(double d)
	{
		return d>0;
	}
	public static boolean isNonNegative(double d)
	{
		return d>=0;
	}
	public static boolean isBetween(double d, double min, double max)
	{
		return (d>=min) && (d<=max);
	}

	public static int compare(double lhs, double rhs)
	{
		if (lhs < rhs)
		{
			return -1;
		}
		if (lhs > rhs)
		{
			return +1;
		}
		// Need to compare bits to handle 0.0 == -0.0 being true
		// compare should put -0.0 < +0.0
		// Two NaNs are also == for compare purposes
		// where NaN == NaN is false
		long lhsBits = Double.doubleToLongBits(lhs);
		long rhsBits = Double.doubleToLongBits(rhs);
		if (lhsBits == rhsBits)
		{
			return 0;
		}
		// Something exotic! A comparison to NaN or 0.0 vs -0.0
		// Fortunately NaN's long is > than everything else
		// Also negzeros bits < poszero
		// NAN: 9221120237041090560
		// MAX: 9218868437227405311
		// NEGZERO: -9223372036854775808
		if (lhsBits < rhsBits)
		{
			return -1;
		} else
		{
			return +1;
		}
	}
	public static int sum(Collection<Integer> c)
	{
		Validate.notNull(c);
		int sum = 0;
		for(int element : c)
		{
			sum += element;
		}
		return sum;
	}
	public static Double integerToDouble(Integer i)
	{
		if(i == null)
		{
			return null;
		}
		if(i == 0)
		{
			return 0.0;
		}else
		{
			return round((double)i/100, 2);
		}
	}
	public static Integer doubleToInteger(Double d)
	{
		if(d == null)
		{
			return 0;
		}
		return (int)(d.doubleValue()*100);
	}
	public static int doubleToInt(Double d)
	{
		if(d == null)
		{
			return 0;
		}
		return (int)(d.doubleValue()*100);
	}
	private static double round(double value, int numDecimalPlacesToPreserve)
	{
		BigDecimal decimal = new BigDecimal(value);
		decimal = decimal.setScale(numDecimalPlacesToPreserve, RoundingMode.HALF_UP);
		return decimal.doubleValue();
	}
	public static Double createDouble(String str)
	{
		if(StringUtils.isBlank(str))
		{
			return null;
		}
		return Double.valueOf(str);
	}
	public static int createInt(String str, int defaultValue)
	{
		if(StringUtils.isBlank(str))
		{
			return defaultValue;
		}
		return Integer.valueOf(str);
	}
	public static Integer createInteger(String str)
	{
		if(StringUtils.isBlank(str))
		{
			return null;
		}
		return Integer.valueOf(str);
	}
}
