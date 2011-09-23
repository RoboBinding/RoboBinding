package robobinding.utils;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Validate
{
	private Validate(){}
	
	public static void isPositive(double d)
	{
		isTrue(NumberUtils.isPositive(d), "the given value '"+d+"' is not greater than 0");
	}
	public static void isNonNegative(double d)
	{
		isNonNegative(d, "the given value '"+d+"' is not greater than or equivalent to 0");
	}
	public static void isNonNegative(double d, String message)
	{
		isTrue(NumberUtils.isNonNegative(d), message);
	}
	public static void between(double d, double min, double max)
	{
		isTrue(NumberUtils.isBetween(d, min, max), "the given value '"+d+"' is between '"+min+"' and '"+max+"'");
	}
	public static void isEquals(double left, double right)
	{
		isEquals(left, right, 0, "the value of left '"+left+"' is not equal to that of right '"+right+"'");
	}
	public static void isEquals(double left, double right, double delta, String message)
	{
		double difference = Math.abs(left-right);
		Validate.isTrue(difference<=delta, message);
	}
	public static void noDuplicate(Collection<Integer> c)
	{
		notNull(c, "collection is null");
		if(c.isEmpty())
		{
			return;
		}
		Set<Integer> set = Sets.newHashSet(c);
		isEquals(set.size(), c.size());
	}

	public static void blank(String str)
	{
		isTrue(StringUtils.isBlank(str), "str is not blank");
	}
	public static void notBlank(String str)
	{
		notBlank(str, "str is blank");
	}
	public static void notBlank(String str, String message)
	{
		isTrue(StringUtils.isNotBlank(str), message);
	}
	public static void isFalse(boolean expression)
	{
		isFalse(expression, "expression is not false");
	}
	public static void isFalse(boolean expression, String message)
	{
		if(expression)
		{
			fail(message);
		}
	}
	public static void isTrue(boolean expression)
	{
		isTrue(expression, "expression is not true");
	}
	public static void isTrue(boolean expression, String message)
	{
		if(!expression)
		{
			fail(message);
		}
	}
	public static void notNull(Object obj)
	{
		notNull(obj, "obj is null");
	}
	public static void notNull(Object obj, String message)
	{
		if(obj == null)
		{
			fail(message);
		}
	}
	public static void fail(String message)
	{
		throw new IllegalArgumentException(message);
	}

	public static void notEmpty(Collection<?> c)
	{
		if((c==null) || c.isEmpty())
		{
			fail("the collection is empty");
		}
	}
	public static void size(Collection<?> c, int expectedSize)
	{
		notNull(c, "collection is null");
		isEquals(c.size(), expectedSize, 0, "the size of the collection is not '"+expectedSize+"' but '"+c.size()+"'");
	}

	public static void isInstance(Object obj, Class<?> cls)
	{
		Validate.notNull(cls);
		isTrue(cls.isInstance(obj), "the given obj is not assignment-compatible with the cls");
	}
}
