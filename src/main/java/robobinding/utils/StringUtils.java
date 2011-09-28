package robobinding.utils;

import java.util.Iterator;

import org.apache.commons.lang3.CharSequenceUtils;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class StringUtils
{
	/**
	 * The empty String {@code ""}.
	 * 
	 * @since 2.0
	 */
	private static final String EMPTY = "";

	/**
	 * Represents a failed index search.
	 * 
	 * @since 2.1
	 */
	private static final int INDEX_NOT_FOUND = -1;

	public static boolean isNotBlank(String str)
	{
		return !isBlank(str);
	}

	public static boolean isBlank(String str)
	{
		return (str == null) || isEmpty(str.trim());
	}

	public static boolean isEmpty(String str)
	{
		return (str == null) || str.equals("");
	}

	public static String trim(String str)
	{
		if (str == null)
		{
			return str;
		} else
		{
			return str.trim();
		}
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String (""). Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 * 
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String separator)
	{
		if (array == null)
		{
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String (""). Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 * 
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @param startIndex
	 *            the first index to start joining from. It is an error to pass
	 *            in an end index past the end of the array
	 * @param endIndex
	 *            the index to stop joining from (exclusive). It is an error to
	 *            pass in an end index past the end of the array
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String separator, int startIndex, int endIndex)
	{
		if (array == null)
		{
			return null;
		}
		if (separator == null)
		{
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0)
		{
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++)
		{
			if (i > startIndex)
			{
				buf.append(separator);
			}
			if (array[i] != null)
			{
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 * 
	 * @param iterable
	 *            the {@code Iterable} providing the values to join together,
	 *            may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(Iterable<?> iterable, String separator)
	{
		if (iterable == null)
		{
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 * 
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(Iterator<?> iterator, String separator)
	{

		// handle null, zero and one elements before building a buffer
		if (iterator == null)
		{
			return null;
		}
		if (!iterator.hasNext())
		{
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext())
		{
			return ObjectUtils.toString(first);
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16,
													// probably too small
		if (first != null)
		{
			buf.append(first);
		}

		while (iterator.hasNext())
		{
			if (separator != null)
			{
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null)
			{
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	public static boolean equalsIgnoreCase(String str1, String str2)
	{
		if (str1 == null || str2 == null)
		{
			return str1 == str2;
		} else
		{
			return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, Math.max(str1.length(), str2.length()));
		}
	}

	public static boolean equals(String str1, String str2)
	{
		if (str1 == null || str2 == null)
		{
			return str1 == str2;
		} else
		{
			return str1.equals(str2);
		}
	}

	/**
	 * <p>
	 * Removes all occurrences of a substring from within the source string.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} source string will return {@code null}. An empty ("")
	 * source string will return the empty string. A {@code null} remove string
	 * will return the source string. An empty ("") remove string will return
	 * the source string.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.remove(null, *)        = null
	 * StringUtils.remove("", *)          = ""
	 * StringUtils.remove(*, null)        = *
	 * StringUtils.remove(*, "")          = *
	 * StringUtils.remove("queued", "ue") = "qd"
	 * StringUtils.remove("queued", "zz") = "queued"
	 * </pre>
	 * 
	 * @param str
	 *            the source String to search, may be null
	 * @param remove
	 *            the String to search for and remove, may be null
	 * @return the substring with the string removed if found, {@code null} if
	 *         null String input
	 * @since 2.1
	 */
	public static String remove(String str, String remove)
	{
		if (isEmpty(str) || isEmpty(remove))
		{
			return str;
		}
		return replace(str, remove, EMPTY, -1);
	}

	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, for the
	 * first {@code max} values of the search String.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.replace(null, *, *, *)         = null
	 * StringUtils.replace("", *, *, *)           = ""
	 * StringUtils.replace("any", null, *, *)     = "any"
	 * StringUtils.replace("any", *, null, *)     = "any"
	 * StringUtils.replace("any", "", *, *)       = "any"
	 * StringUtils.replace("any", *, *, 0)        = "any"
	 * StringUtils.replace("abaa", "a", null, -1) = "abaa"
	 * StringUtils.replace("abaa", "a", "", -1)   = "b"
	 * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
	 * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
	 * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
	 * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, may be null
	 * @param searchString
	 *            the String to search for, may be null
	 * @param replacement
	 *            the String to replace it with, may be null
	 * @param max
	 *            maximum number of values to replace, or {@code -1} if no
	 *            maximum
	 * @return the text with any replacements processed, {@code null} if null
	 *         String input
	 */
	public static String replace(String text, String searchString, String replacement, int max)
	{
		if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0)
		{
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == INDEX_NOT_FOUND)
		{
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuilder buf = new StringBuilder(text.length() + increase);
		while (end != INDEX_NOT_FOUND)
		{
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0)
			{
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	// Case conversion
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Converts a String to upper case as per {@link String#toUpperCase()}.
	 * </p>
	 * 
	 * <p>
	 * A {@code null} input String returns {@code null}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.upperCase(null)  = null
	 * StringUtils.upperCase("")    = ""
	 * StringUtils.upperCase("aBc") = "ABC"
	 * </pre>
	 * 
	 * <p>
	 * <strong>Note:</strong> As described in the documentation for
	 * {@link String#toUpperCase()}, the result of this method is affected by
	 * the current locale. For platform-independent case transformations, the
	 * method {@link #lowerCase(String, Locale)} should be used with a specific
	 * locale (e.g. {@link Locale#ENGLISH}).
	 * </p>
	 * 
	 * @param str
	 *            the String to upper case, may be null
	 * @return the upper cased String, {@code null} if null String input
	 */
	public static String upperCase(String str)
	{
		if (str == null)
		{
			return null;
		}
		return str.toUpperCase();
	}

	public static String lowerCase(String str)
	{
		if (str == null)
		{
			return null;
		}
		return str.toLowerCase();
	}
}
