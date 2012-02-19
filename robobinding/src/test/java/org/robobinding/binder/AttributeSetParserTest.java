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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.binder.MockAttributeSet.withAttributes;
import static org.robobinding.binder.MockAttributeSet.withNoBindingAttributes;

import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.util.AttributeSet;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class AttributeSetParserTest
{
	private AttributeSetParser attributeSetParser = new AttributeSetParser();

	@Test
	public void givenAttributeSetWithNoBindingAttributes_whenLoading_thenBindingMapShouldBeEmpty()
	{
		Map<String, String> bindingMap = loadBindingMapFromAttributeSet(withNoBindingAttributes());
		assertTrue(bindingMap.isEmpty());
	}
	
	@Test
	public void givenAttributeSetWithXBindingAttributes_whenLoading_thenBindingMapShouldContainXEntries()
	{
		int numberOfBindingAttributes = anyNumber();
		int numberOfNonBindingAttributes = anyNumber();
		
		Map<String, String> bindingMap = loadBindingMapFromAttributeSet(withAttributes(numberOfBindingAttributes, numberOfNonBindingAttributes));
		assertThat(bindingMap.size(), equalTo(numberOfBindingAttributes));
	}
	
	@Test
	public void givenAttributeSetWithBindingAttributes_whenLoading_thenBindingMapKeysShouldMapToCorrectValues()
	{
		int numberOfBindingAttributes = anyNumber();
		int numberOfNonBindingAttributes = anyNumber();
		AttributeSet attributeSetWithAttributes = withAttributes(numberOfBindingAttributes, numberOfNonBindingAttributes);
		
		Map<String, String> bindingMap = loadBindingMapFromAttributeSet(attributeSetWithAttributes);
		for (String attribute : bindingMap.keySet())
			assertThat(bindingMap.get(attribute), equalTo(attributeSetWithAttributes.getAttributeValue(AttributeSetParser.ROBOBINDING_NAMESPACE, attribute)));
	}
	
	private Map<String, String> loadBindingMapFromAttributeSet(AttributeSet attrs)
	{
		return attributeSetParser.parse(attrs);
	}
	
	private int anyNumber()
	{
		return new Random().nextInt(100);
	}
}
