package org.robobinding.attribute;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.util.RandomValues;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class PendingGroupAttributesTest {
	private PendingGroupAttributes pendingGroupAttributes;
	private String[] attributeNames;

	@Before
	public void setUp() {
		attributeNames = randomAttributeArray();
	}

	@Test
	public void givenAllAttributesArePresent_whenAssertingAllAttributesArePresent_thenDoNothing() {
		allAttributesArePresent();

		pendingGroupAttributes.assertAttributesArePresent(attributeNames);
	}

	@Test(expected = MissingRequiredAttributesException.class)
	public void givenNoAttributesArePresent_whenAssertingAllAttributesArePresent_thenThrowException() {
		noAttributeIsPresent();

		pendingGroupAttributes.assertAttributesArePresent(attributeNames);
	}

	private void noAttributeIsPresent() {
		pendingGroupAttributes = new PendingGroupAttributes(Maps.<String, String> newHashMap());
	}

	private void allAttributesArePresent() {
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		for (String attributeName : attributeNames) {
			presentAttributeMappings.put(attributeName, "attributeValue");
		}

		pendingGroupAttributes = new PendingGroupAttributes(presentAttributeMappings);
	}

	private String[] randomAttributeArray() {
		String[] attributeNames = new String[RandomValues.anyInteger() + 1];

		for (int i = 0; i < attributeNames.length; i++) {
			attributeNames[i] = RandomStringUtils.random(10);
		}

		return attributeNames;
	}
}
