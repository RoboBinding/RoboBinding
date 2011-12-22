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

import org.robobinding.binder.BindingAttributeResolver;
import org.robobinding.viewattribute.BindingAttributeProvider;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarAttributeProvider implements BindingAttributeProvider<RatingBar>
{
	private static final String ON_RATING_BAR_CHANGE = "onRatingBarChange";
	private static final String NUM_STARS = "numStars";
	private static final String RATING = "rating";

	@Override
	public void resolveSupportedBindingAttributes(RatingBar ratingBar, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		if (bindingAttributeResolver.hasAttribute(RATING))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(RATING);
			bindingAttributeResolver.resolveAttribute(RATING, new RatingAttribute(ratingBar, attributeValue, preInitializeViews));
		}
		if (bindingAttributeResolver.hasAttribute(NUM_STARS))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(NUM_STARS);
			bindingAttributeResolver.resolveAttribute(NUM_STARS, new NumStarsAttribute(ratingBar, attributeValue, preInitializeViews));
		}
		if (bindingAttributeResolver.hasAttribute(ON_RATING_BAR_CHANGE))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(ON_RATING_BAR_CHANGE);
			bindingAttributeResolver.resolveAttribute(ON_RATING_BAR_CHANGE, new OnRatingBarChangeAttribute(ratingBar, attributeValue));
		}
	}

}
