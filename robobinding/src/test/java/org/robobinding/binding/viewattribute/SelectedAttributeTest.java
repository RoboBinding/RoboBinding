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
package org.robobinding.binding.viewattribute;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class SelectedAttributeTest extends AbstractViewAttributeTest
{
	@Test
	public void givenBoundAttribute_whenValueModelUpdated_ThenViewShouldReflectChanges()
	{
		Boolean selected = Boolean.TRUE;
		MockPresentationModelAdapterForProperty<Boolean> mockPresentationModelAdapter = createBoundAttribute();

		mockPresentationModelAdapter.updatePropertyValue(selected);

		Assert.assertEquals(selected, shadowView.isSelected());
	}
	private MockPresentationModelAdapterForProperty<Boolean> createBoundAttribute()
	{
		SelectedAttribute selectedAttribute = new SelectedAttribute(view, MockPresentationModelAdapterForProperty.ONE_WAY_BINDING_PROPERTY_NAME, true);
		MockPresentationModelAdapterForProperty<Boolean> mockPresentationModelAdapter = bindToProperty(selectedAttribute, Boolean.class);
		return mockPresentationModelAdapter;
	}
}