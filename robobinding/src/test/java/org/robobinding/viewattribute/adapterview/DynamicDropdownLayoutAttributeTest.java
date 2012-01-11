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
package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.MockPresentationModelForProperty;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.RandomValues;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class DynamicDropdownLayoutAttributeTest extends AbstractDynamicLayoutAttributeTest
{
	@Before
	public void setUp()
	{
		String attributeValue = MockPresentationModelForProperty.ONE_WAY_BINDING_PROPERTY_NAME;
		dynamicLayoutAttribute = new DropdownLayoutAttribute(adapterView, attributeValue).new DynamicLayoutAttribute();
		dynamicLayoutAttribute.setView(adapterView);
		dynamicLayoutAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(attributeValue));
	}
	
	@Test
	public void givenBound_whenUpdatingValueModel_thenUpdateDropdownLayoutIdOnDataSetAdapter()
	{
		DynamicLayoutAttributeUtils.bindAttribute(dataSetAdapter, dynamicLayoutAttribute);
		
		int newDropdownLayoutId = RandomValues.anyInteger();
		dynamicLayoutAttribute.valueModelUpdated(newDropdownLayoutId);
		
		verify(dataSetAdapter).setDropdownLayoutId(newDropdownLayoutId);
	}
}
