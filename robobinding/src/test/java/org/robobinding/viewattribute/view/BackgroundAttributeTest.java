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
package org.robobinding.viewattribute.view;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.Drawables;
import org.robobinding.viewattribute.MockPresentationModelAdapterForProperty;

import android.graphics.drawable.Drawable;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BackgroundAttributeTest extends AbstractViewPropertyAttributeTest
{
	@Test
	public void givenResourceIdBoundAttribute_whenValueModelUpdated_ThenViewShouldReflectChanges()
	{
		int resourceId = 1;
		MockPresentationModelAdapterForProperty<Integer> mockPresentationModelAdapter = createBoundAttribute(Integer.class);

		mockPresentationModelAdapter.updatePropertyValue(resourceId);

		Assert.assertEquals(resourceId, shadowView.getBackgroundResourceId());
	}

	@Test
	public void givenDrawableBoundAttribute_whenValueModelUpdated_ThenViewShouldReflectChanges()
	{
		Drawable drawable = Drawables.get()[0];

		MockPresentationModelAdapterForProperty<Drawable> mockPresentationModelAdapter = createBoundAttribute(Drawable.class);

		mockPresentationModelAdapter.updatePropertyValue(drawable);

		Assert.assertEquals(drawable, shadowView.getBackground());
	}
	
	@Test(expected=RuntimeException.class)
	public void whenBindToUnsupportedType_thenThrowException()
	{
		createBoundAttribute(Long.class);
	}

	private <T> MockPresentationModelAdapterForProperty<T> createBoundAttribute(Class<T> propertyType)
	{
		BackgroundAttribute backgroundAttribute = new BackgroundAttribute(view, MockPresentationModelAdapterForProperty.ONE_WAY_BINDING_PROPERTY_NAME, true);
		MockPresentationModelAdapterForProperty<T> mockPresentationModelAdapter = bindToProperty(backgroundAttribute, propertyType);
		return mockPresentationModelAdapter;
	}
}