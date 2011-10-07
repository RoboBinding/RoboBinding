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
package robobinding.binding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import android.util.AttributeSet;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class BindingAttributeMapTest
{
	@Test
	public void givenAnAttributeSetWithNoBindingAttributes_WhenCreatingBindingMap_ThenReturnEmptyMap()
	{
		BindingAttributeMap bindingMap = BindingAttributeMap.createFrom(MockAttributeSet.withNoBindingAttributes());
		assertTrue(bindingMap.isEmpty());
	}
	
	@Test
	public void givenAnAttributeSetWithXBindingAttributes_WhenCreatingBindingMap_ThenReturnMapWithXEntries()
	{
		int numberOfBindingAttributes = anyNumber();
		int numberOfNonBindingAttributes = anyNumber();
		BindingAttributeMap bindingMap = BindingAttributeMap.createFrom(MockAttributeSet.withAttributes(numberOfBindingAttributes, numberOfNonBindingAttributes));
		assertThat(bindingMap.size(), equalTo(numberOfBindingAttributes));
	}
	
	@Test
	public void givenAnAttributeSetWithBindingAttributes_WhenCreatingBindingMap_ThenBindingMapKeysShouldMapToCorrectValues()
	{
		int numberOfBindingAttributes = anyNumber();
		int numberOfNonBindingAttributes = anyNumber();
		AttributeSet attributeSetWithAttributes = MockAttributeSet.withAttributes(numberOfBindingAttributes, numberOfNonBindingAttributes);
		BindingAttributeMap bindingMap = BindingAttributeMap.createFrom(attributeSetWithAttributes);
		
		for (String attribute : bindingMap.keySet())
			assertThat(bindingMap.get(attribute), equalTo(attributeSetWithAttributes.getAttributeValue(BindingAttributeMap.ROBOBINDING_NAMESPACE, attribute)));
	}
	
	private int anyNumber()
	{
		return new Random().nextInt(100);
	}
}
