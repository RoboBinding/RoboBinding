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
package org.robobinding.viewattribute.textview;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.MockPresentationModelAdapterForProperty;
import org.robobinding.viewattribute.textview.TextColorAttribute;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import android.graphics.Color;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class TextColorAttributeTest extends AbstractTextViewPropertyAttributeTest
{
	@Test
	public void givenBoundAttribute_whenValueModelUpdated_ThenViewShouldReflectChanges()
	{
		Integer color = Color.RED;
		MockPresentationModelAdapterForProperty<Integer> mockPresentationModelAdapter = createBoundAttribute();

		mockPresentationModelAdapter.updatePropertyValue(color);

		Assert.assertEquals(color, shadowTextView.getTextColorHexValue());
	}
	private MockPresentationModelAdapterForProperty<Integer> createBoundAttribute()
	{
		TextColorAttribute textColorAttribute = new TextColorAttribute(textView, MockPresentationModelAdapterForProperty.ONE_WAY_BINDING_PROPERTY_NAME, true);
		MockPresentationModelAdapterForProperty<Integer> mockPresentationModelAdapter = bindToProperty(textColorAttribute, Integer.class);
		return mockPresentationModelAdapter;
	}
}
