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

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingAttribute extends AbstractMultiTypePropertyViewAttribute<RatingBar>
{
	@Override
	protected AbstractPropertyViewAttribute<RatingBar, ?> createPropertyViewAttribute(Class<?> propertyType)
	{
		if (PrimitiveTypeUtils.floatIsAssignableFrom(propertyType))
		{
			return new FloatRatingAttribute();
		} 
		else if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType))
		{
			return new IntegerRatingAttribute();
		} 
		else
			throw new RuntimeException("Could not find a suitable rating attribute class for property type: " + propertyType);
	}
	
	static class FloatRatingAttribute extends AbstractPropertyViewAttribute<RatingBar, Float>
	{
		@Override
		protected void valueModelUpdated(Float newRating)
		{
			view.setRating(newRating);
		}

		@Override
		protected void observeChangesOnTheView(final ValueModel<Float> valueModel)
		{
			RatingBarListenerUtils.addOnRatingBarChangeListener(view, new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
				{
					valueModel.setValue(rating);
				}
			});
		}
	}

	static class IntegerRatingAttribute extends AbstractPropertyViewAttribute<RatingBar, Integer>
	{
		@Override
		protected void valueModelUpdated(Integer newRating)
		{
			view.setRating((float)newRating);
		}

		@Override
		protected void observeChangesOnTheView(final ValueModel<Integer> valueModel)
		{
			view.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser)
				{
					valueModel.setValue((int)rating);
				}
			});
		}
	}
}
