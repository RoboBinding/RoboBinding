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
package org.robobinding.viewattribute.ratingbar;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.PropertyValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.ViewAttribute;

import android.content.Context;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingAttribute implements ViewAttribute
{
	private final RatingBar ratingBar;
	private PropertyBindingDetails propertyBindingDetails;

	public RatingAttribute(RatingBar ratingBar, String attributeValue, boolean preInitializeView)
	{
		this.ratingBar = ratingBar;
		propertyBindingDetails = PropertyBindingDetails.createFrom(attributeValue, preInitializeView);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyBindingDetails.propertyName);
		ViewAttribute viewAttribute;

		if (PrimitiveTypeUtils.floatIsAssignableFrom(propertyType))
		{
			viewAttribute = new FloatRatingAttribute();
		} else if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			viewAttribute = new IntegerRatingAttribute();
		} else
			throw new RuntimeException("Could not find a suitable rating attribute class for property type: " + propertyType);

		viewAttribute.bind(presentationModelAdapter, context);
	}

	private class FloatRatingAttribute extends AbstractPropertyViewAttribute<Float>
	{
		public FloatRatingAttribute()
		{
			super(propertyBindingDetails);
		}

		@Override
		protected void valueModelUpdated(Float newRating)
		{
			ratingBar.setRating(newRating);
		}

		@Override
		protected void observeChangesOnTheView(final PropertyValueModel<Float> valueModel)
		{
			ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
				{
					valueModel.setValue(rating);
				}
			});
		}
	}

	public class IntegerRatingAttribute extends AbstractPropertyViewAttribute<Integer>
	{
		public IntegerRatingAttribute()
		{
			super(propertyBindingDetails);
		}

		@Override
		protected void valueModelUpdated(Integer newRating)
		{
			ratingBar.setRating((float)newRating);
		}

		@Override
		protected void observeChangesOnTheView(final PropertyValueModel<Integer> valueModel)
		{
			ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
				{
					valueModel.setValue((int)rating);
				}
			});
		}
	}
}
