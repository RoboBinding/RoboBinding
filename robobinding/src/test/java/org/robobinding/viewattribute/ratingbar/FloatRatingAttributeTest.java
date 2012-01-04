/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.FloatRatingAttribute;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FloatRatingAttributeTest extends AbstractPropertyViewAttributeTest<RatingBar, FloatRatingAttribute>
{
	@Before
	public void setUp()
	{
		//TODO Remove this line once Robolectric auto-initializes this field to 100 as it should do (pull request submitted)
		view.setMax(100);
	}
	
	@Test
	public void whenValueModelUpdated_ThenViewShouldReflectChanges()
	{
		float newRating = RandomValues.anyFloat();
		
		attribute.valueModelUpdated(newRating);

		assertThat((double)view.getRating(), closeTo(newRating, 0.1));
	}
	
	@Test
	public void whenRatingIsChanged_ThenUpdateValueModel()
	{
		ValueModel<Float> valueModel = twoWayBindToProperty(Float.class);
		
		view.setRating(RandomValues.anyFloat());
		
		assertThat((double)valueModel.getValue(), closeTo(view.getRating(), 0.1));
	}
}
