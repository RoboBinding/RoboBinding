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
package org.robobinding.viewattribute.listview;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.adapterview.SubViewVisibilityAttribute;
import org.robobinding.viewattribute.adapterview.SubViewVisibilityAttribute.IntegerVisibilityAttribute;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class IntegerVisibilityAttributeTest
{
	private IntegerVisibilityAttribute attribute;
	private MockHeaderOrFooterVisibility mockVisibility;
	
	@Before
	public void setUp()
	{
		mockVisibility = new MockHeaderOrFooterVisibility();
		SubViewVisibilityAttribute headerOrFooterVisibilityAttribute = new SubViewVisibilityAttribute(mockVisibility);
		attribute = headerOrFooterVisibilityAttribute.new IntegerVisibilityAttribute();
	}
	
	@Test
	public void whenValueModelUpdated_thenVisibilityStateUpdatedAccordingly()
	{
		int visibility = RandomValues.anyVisibility();
		
		attribute.valueModelUpdated(visibility);
		
		assertEquals(visibility, mockVisibility.state);
	}
}
