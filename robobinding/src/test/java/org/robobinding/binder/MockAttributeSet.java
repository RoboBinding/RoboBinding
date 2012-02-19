/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import android.util.AttributeSet;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockAttributeSet
{
	private MockAttributeSet(){}
	
	public static AttributeSet withNoBindingAttributes()
	{
		return mock(AttributeSet.class);
	}
		
	public static AttributeSet withAttributes(int numBindingAttributes, int numNonBindingAttributes)
	{
		AttributeSet attributeSet = mock(AttributeSet.class);
		
		int attributeCount = numBindingAttributes + numNonBindingAttributes;
		when(attributeSet.getAttributeCount()).thenReturn(attributeCount);
		
		for (int i = 0; i < numBindingAttributes; i++)
		{
			String attributeName = "binding_attribute_" + i;
			when(attributeSet.getAttributeName(i)).thenReturn(attributeName);
			when(attributeSet.getAttributeValue(AttributeSetParser.ROBOBINDING_NAMESPACE, attributeName)).thenReturn("binding_value_" + i);
		}
		
		for (int i = numBindingAttributes; i < attributeCount; i++)
		{
			when(attributeSet.getAttributeName(i)).thenReturn("non_binding_attribute_" + i);
		}
		
		return attributeSet;
	}
}
