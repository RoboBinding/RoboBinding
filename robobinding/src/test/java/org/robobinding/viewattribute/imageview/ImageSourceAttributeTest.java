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
package org.robobinding.viewattribute.imageview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractAttributeTest;
import org.robobinding.viewattribute.DrawableData;
import org.robobinding.viewattribute.MockPresentationModelForProperty;
import org.robobinding.viewattribute.RandomValues;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageSourceAttributeTest extends AbstractAttributeTest<ImageView, ImageSourceAttribute>
{
	private DrawableData drawableData = DrawableData.get(0);
	
	@Test
	public void givenValueModelIsIntegerType_WhenUpdatingPresentationModel_ThenViewShouldReflectViewModel()
	{
		MockPresentationModelForProperty<Integer> presentationModel = initializeForOneWayBinding(RandomValues.primitiveOrBoxedIntegerClass());
		
		presentationModel.updatePropertyValue(drawableData.resourceId);
		
		assertThat(view.getDrawable(), equalTo(drawableData.drawable));
	}
	
	@Test
	public void givenValueModelIsDrawableType_WhenUpdatingPresentationModel_ThenViewShouldReflectViewModel()
	{
		MockPresentationModelForProperty<Drawable> presentationModel = initializeForOneWayBinding(Drawable.class);
		
		presentationModel.updatePropertyValue(drawableData.drawable);
		
		assertThat(view.getDrawable(), equalTo(drawableData.drawable));
	}
	
	@Test
	public void givenValueModelIsBitmapType_WhenUpdatingPresentationModel_ThenViewShouldReflectViewModel()
	{
		MockPresentationModelForProperty<Bitmap> presentationModel = initializeForOneWayBinding(Bitmap.class);
		
		presentationModel.updatePropertyValue(drawableData.bitmap);
		
		assertThat(view.getDrawable(), equalTo(drawableData.drawable));
	}
}
