package robobinding.value;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	ValueHoldersTest.class,
	BooleanNegatorTest.class,
	BooleanToStringConverterTest.class,
	DoubleConverterTest.class,
	DoubleToIntegerConverterTest.class,
	FloatConverterTest.class,
	FloatToIntegerConverterTest.class,
	IntegerConverterTest.class,
	LongConverterTest.class,
	LongToIntegerConverterTest.class,
	StringConverterTest.class
})
public class AllTests
{
}
