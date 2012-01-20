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
package org.robobinding.viewattribute.compoundbutton;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.BindingAttributeValues;
import org.robobinding.viewattribute.GroupedAttributeDetails;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewAttributeAssert;
import org.robobinding.viewattribute.ViewAttributeUtils;

import android.widget.CheckBox;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributesTest
{
	private OnCheckedChangeAttributes onCheckedChangeAttributes;
	private GroupedAttributeDetails mockGroupedAttributeDetails;
	
	@Before
	public void setUp()
	{
		onCheckedChangeAttributes = new OnCheckedChangeAttributes();
		onCheckedChangeAttributes.setView(mock(CheckBox.class));
		mockGroupedAttributeDetails = mock(GroupedAttributeDetails.class);
		onCheckedChangeAttributes.setGroupedAttributeDetails(mockGroupedAttributeDetails);
	}
	
	@Test
	public void whenAttributeCheckedIsPresent_thenCheckedAttributeCreated()
	{
		makeAttributePresent(OnCheckedChangeAttributes.CHECKED);
		
		onCheckedChangeAttributes.postInitialization();
		
		assertAttributeCreated(CheckedAttribute.class);
	}

	@Test
	public void whenAttributeOnCheckedChangeIsPresent_thenOnCheckedChangeAttributeCreated()
	{
		makeAttributePresent(OnCheckedChangeAttributes.ON_CHECKED_CHANGE);
		
		onCheckedChangeAttributes.postInitialization();
		
		assertAttributeCreated(OnCheckedChangeAttribute.class);
	}
	
	@Test
	public void whenAttributesOnCheckedChangeAndCheckedArePresent_thenBothAttributesCreated()
	{
		makeAttributePresent(OnCheckedChangeAttributes.CHECKED);
		makeAttributePresent(OnCheckedChangeAttributes.ON_CHECKED_CHANGE);
		
		onCheckedChangeAttributes.postInitialization();
		
		assertAttributeCreated(CheckedAttribute.class);
		assertAttributeCreated(OnCheckedChangeAttribute.class);
	}
	
	private void makeAttributePresent(String attribute)
	{
		when(mockGroupedAttributeDetails.hasAttribute(attribute)).thenReturn(true);
		when(mockGroupedAttributeDetails.attributeValueFor(attribute)).thenReturn(BindingAttributeValues.ONE_WAY_BINDING_DEFAULT_PROPERTY_NAME);
	}
	
	private void assertAttributeCreated(Class<? extends ViewAttribute> viewAttributeClass)
	{
		ViewAttribute viewAttribute = ViewAttributeUtils.findFirstViewAttributeOfType(onCheckedChangeAttributes.viewAttributes, viewAttributeClass);
		assertNotNull(viewAttribute);
		ViewAttributeAssert.assertValidationPassed(viewAttribute);
	}
}
