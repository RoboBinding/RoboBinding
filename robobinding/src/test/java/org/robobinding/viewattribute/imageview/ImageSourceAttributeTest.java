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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.BitmapImageSourceAttribute;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.DrawableImageSourceAttribute;
import org.robobinding.viewattribute.imageview.ImageSourceAttribute.IntegerImageSourceAttribute;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ImageSourceAttributeTest
{
	private ImageView imageView;
	private ImageSourceAttribute imageSourceAttribute;
	private PresentationModelAdapter presentationModelAdapter;

	@Before
	public void setUp()
	{
		 imageSourceAttribute = new ImageSourceAttribute(imageView, "{property_name}", true);
		 presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@Test
	public void whenBindingWithAnIntPrimitiveProperty_ThenInitializeIntegerImageSourceAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class)int.class);
		
		assertThat(imageSourceAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(IntegerImageSourceAttribute.class));
	}
	
	@Test
	public void whenBindingWithAnIntegerProperty_ThenInitializeIntegerImageSourceAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class)Integer.class);
		
		assertThat(imageSourceAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(IntegerImageSourceAttribute.class));
	}
	
	@Test
	public void whenBindingWithADrawableProperty_ThenInitializeDrawableImageSourceAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class)Drawable.class);
		
		assertThat(imageSourceAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(DrawableImageSourceAttribute.class));
	}
	
	@Test
	public void whenBindingWithABitmapProperty_ThenInitializeBitmapImageSourceAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class)Bitmap.class);
		
		assertThat(imageSourceAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(BitmapImageSourceAttribute.class));
	}
}
