package org.robobinding.binder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.util.AttributeSet;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockAttributeSet {
	private MockAttributeSet() {
	}

	public static AttributeSet withNoBindingAttributes() {
		return mock(AttributeSet.class);
	}

	public static AttributeSet withAttributes(int numBindingAttributes, int numNonBindingAttributes) {
		AttributeSet attributeSet = mock(AttributeSet.class);

		int attributeCount = numBindingAttributes + numNonBindingAttributes;
		when(attributeSet.getAttributeCount()).thenReturn(attributeCount);

		for (int i = 0; i < numBindingAttributes; i++) {
			String attributeName = "binding_attribute_" + i;
			when(attributeSet.getAttributeName(i)).thenReturn(attributeName);
			when(attributeSet.getAttributeValue(BindingAttributeParser.ROBOBINDING_NAMESPACE, attributeName)).thenReturn("binding_value_" + i);
		}

		for (int i = numBindingAttributes; i < attributeCount; i++) {
			when(attributeSet.getAttributeName(i)).thenReturn("non_binding_attribute_" + i);
		}

		return attributeSet;
	}
}
