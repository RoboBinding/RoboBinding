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

import org.robobinding.viewattribute.AbstractAttributeProvider;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.IntegerRatingAttribute;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RatingBarAttributeProvider extends AbstractAttributeProvider<RatingBar>
{
	@Override
	protected void populateViewAttributeMappings(ViewAttributeMappings<RatingBar> mappings)
	{
		mappings.addTypePropertyMapping("rating", typeMap(Integer.class, IntegerRatingAttribute.class),
				typeMap(Float.class, FloatRatingAttribute.class));
		
		mappings.addPropertyMapping("numStars", NumStarsAttribute.class);
		
		mappings.addCommandMapping("onRatingBarChange", OnRatingBarChangeAttribute.class);
	}
}
