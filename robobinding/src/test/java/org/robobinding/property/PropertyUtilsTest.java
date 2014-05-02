package org.robobinding.property;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyUtilsTest {
    @Test
    public void whenGetPropertyNames_thenReturnExpectedPropertyNames() {
	List<String> propertyNames = PropertyUtils.getPropertyNames(Bean.class);

	assertEquals(Sets.newHashSet(Bean.PROPERTY1, Bean.PROPERTY2), Sets.newHashSet(propertyNames));
    }

    public static class Bean {
	public static final String PROPERTY1 = "property1";
	public static final String PROPERTY2 = "property2";

	public Bean() {
	}

	public String getProperty1() {
	    return "property1";
	}

	public int getProperty2() {
	    return 0;
	}

	public double nonProperty() {
	    return 0.0;
	}

	public boolean getNonPropertyWithParameter(String p1) {
	    return true;
	}
    }
}
