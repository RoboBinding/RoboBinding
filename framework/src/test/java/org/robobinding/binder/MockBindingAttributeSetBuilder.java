package org.robobinding.binder;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import android.util.AttributeSet;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockBindingAttributeSetBuilder {
	private Map<String, String> attributeMap;

	private MockBindingAttributeSetBuilder() {
		attributeMap = Maps.newLinkedHashMap();
	}

	public static MockBindingAttributeSetBuilder aBindingAttributeSet() {
		return new MockBindingAttributeSetBuilder();
	}

	public MockBindingAttributeSetBuilder withAttribute(String name, String value) {
		attributeMap.put(name, value);
		return this;
	}

	public AttributeSet build() {
		AttributeSet attributeSet = mock(AttributeSet.class);

		when(attributeSet.getAttributeCount()).thenReturn(attributeMap.size());

		int i = 0;
		for (Map.Entry<String, String> attributeEntry : attributeMap.entrySet()) {
			String name = attributeEntry.getKey();
			String value = attributeEntry.getValue();
			when(attributeSet.getAttributeName(i)).thenReturn(name);
			when(attributeSet.getAttributeValue(eq(BindingAttributeParser.ROBOBINDING_NAMESPACE), eq(name))).thenReturn(value);
			i++;
		}

		return attributeSet;
	}
}
