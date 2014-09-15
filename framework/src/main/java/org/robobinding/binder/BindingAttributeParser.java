package org.robobinding.binder;

import java.util.Map;

import android.util.AttributeSet;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingAttributeParser {
	public static final String ROBOBINDING_NAMESPACE = "http://robobinding.org/android";

	public Map<String, String> parse(AttributeSet attributeSet) {
		Map<String, String> bindingAttributes = Maps.newHashMap();

		for (int i = 0; i < attributeSet.getAttributeCount(); i++) {
			String attributeName = attributeSet.getAttributeName(i);
			//TODO:Robolectric bug.
			if(attributeName.startsWith("bind:")) {
				attributeName = attributeName.substring(5);
			}

			String attributeValue = attributeSet.getAttributeValue(
					ROBOBINDING_NAMESPACE, attributeName);
			

			if (attributeValue != null)
				bindingAttributes.put(attributeName, attributeValue);
		}

		return bindingAttributes;
	}
}
