/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
