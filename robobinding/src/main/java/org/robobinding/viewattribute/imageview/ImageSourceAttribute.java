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

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ImageSourceAttribute implements PropertyViewAttribute
{
	private final ImageView imageView;
	private PropertyBindingDetails propertyBindingDetails;

	public ImageSourceAttribute(ImageView imageView, String attributeValue, boolean preInitializeView)
	{
		this.imageView = imageView;
		propertyBindingDetails = PropertyBindingDetails.createFrom(attributeValue, preInitializeView);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		PropertyViewAttribute propertyViewAttribute = lookupPropertyViewAttribute(presentationModelAdapter);
		propertyViewAttribute.bind(presentationModelAdapter, context);
	}
	
	PropertyViewAttribute lookupPropertyViewAttribute(PresentationModelAdapter presentationModelAdapter)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyBindingDetails.propertyName);
		
		if (int.class.isAssignableFrom(propertyType) || Integer.class.isAssignableFrom(propertyType))
		{
			return new IntegerImageSourceAttribute();
		}
		else if (Drawable.class.isAssignableFrom(propertyType))
		{
			return new DrawableImageSourceAttribute();
		}
		else if (Bitmap.class.isAssignableFrom(propertyType))
		{
			return new BitmapImageSourceAttribute();
		}
		
		throw new RuntimeException("Could not find a suitable image attribute class for property type: " + propertyType);
	}

	
	class IntegerImageSourceAttribute extends AbstractReadOnlyPropertyViewAttribute<Integer>
	{
		public IntegerImageSourceAttribute()
		{
			super(propertyBindingDetails);
		}

		@Override
		protected void valueModelUpdated(Integer resourceId)
		{
			imageView.setImageResource(resourceId);
		}
	}
	
	class DrawableImageSourceAttribute extends AbstractReadOnlyPropertyViewAttribute<Drawable>
	{
		public DrawableImageSourceAttribute()
		{
			super(propertyBindingDetails);
		}

		@Override
		protected void valueModelUpdated(Drawable drawable)
		{
			imageView.setImageDrawable(drawable);
		}
	}
	
	class BitmapImageSourceAttribute extends AbstractReadOnlyPropertyViewAttribute<Bitmap>
	{
		public BitmapImageSourceAttribute()
		{
			super(propertyBindingDetails);
		}

		@Override
		protected void valueModelUpdated(Bitmap bitmap)
		{
			imageView.setImageBitmap(bitmap);
		}
	}

}
