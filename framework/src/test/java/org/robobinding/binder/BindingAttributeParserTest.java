package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.binder.MockAttributeSet.withAttributes;
import static org.robobinding.binder.MockAttributeSet.withNoBindingAttributes;

import java.util.Map;

import org.junit.Test;
import org.robobinding.util.RandomValues;

import android.util.AttributeSet;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingAttributeParserTest {
	private BindingAttributeParser bindingAttributeParser = new BindingAttributeParser();

	@Test
	public void givenAttributeSetWithNoBindingAttributes_whenLoading_thenBindingMapShouldBeEmpty() {
		Map<String, String> bindingMap = loadBindingMapFromAttributeSet(withNoBindingAttributes());
		assertTrue(bindingMap.isEmpty());
	}

	@Test
	public void givenAttributeSetWithXBindingAttributes_whenLoading_thenBindingMapShouldContainXEntries() {
		int numberOfBindingAttributes = anyNumber();
		int numberOfNonBindingAttributes = anyNumber();

		Map<String, String> bindingMap = loadBindingMapFromAttributeSet(withAttributes(numberOfBindingAttributes, numberOfNonBindingAttributes));
		assertThat(bindingMap.size(), equalTo(numberOfBindingAttributes));
	}

	@Test
	public void givenAttributeSetWithBindingAttributes_whenLoading_thenBindingMapKeysShouldMapToCorrectValues() {
		int numberOfBindingAttributes = anyNumber();
		int numberOfNonBindingAttributes = anyNumber();
		AttributeSet attributeSetWithAttributes = withAttributes(numberOfBindingAttributes, numberOfNonBindingAttributes);

		Map<String, String> bindingMap = loadBindingMapFromAttributeSet(attributeSetWithAttributes);
		for (String attribute : bindingMap.keySet()) {
			assertThat(bindingMap.get(attribute),
					equalTo(attributeSetWithAttributes.getAttributeValue(BindingAttributeParser.ROBOBINDING_NAMESPACE, attribute)));
		}
	}

	private Map<String, String> loadBindingMapFromAttributeSet(AttributeSet attrs) {
		return bindingAttributeParser.parse(attrs);
	}

	private int anyNumber() {
		return RandomValues.nextInt(100);
	}
}
