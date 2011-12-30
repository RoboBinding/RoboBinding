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

import org.robobinding.viewattribute.AbstractBindingAttributeProviderTest;
import org.robobinding.viewattribute.BindableView;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarAttributeProviderTest extends AbstractBindingAttributeProviderTest<RatingBar>
{

	@Override
	protected BindableView<RatingBar> getBindingAttributeProvider()
	{
		return new RatingBarAttributeProvider();
	}

	@Override
	protected RatingBar createNewViewInstance()
	{
		return new RatingBar(null);
	}

	@Override
	protected void populateAttributeClassMappings(AttributeClassMappings attributeClassMappings)
	{
		attributeClassMappings.add("rating", RatingAttribute.class);
		attributeClassMappings.add("numStars", NumStarsAttribute.class);
		attributeClassMappings.add("onRatingBarChange", OnRatingBarChangeAttribute.class);
	}

}
