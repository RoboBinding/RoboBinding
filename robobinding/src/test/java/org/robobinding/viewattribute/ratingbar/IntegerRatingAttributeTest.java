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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.robobinding.viewattribute.RandomValues.anyInteger;
import static org.robobinding.viewattribute.RandomValues.nextInt;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.IntegerRatingAttribute;
import org.robobinding.viewattribute.view.AbstractPropertyViewAttributeWithViewListenersAwareTest;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class IntegerRatingAttributeTest extends AbstractPropertyViewAttributeWithViewListenersAwareTest<RatingBar, IntegerRatingAttribute, MockRatingBarListeners>
{
	private static final int NUM_STARS_TO_SHOW = 5;

	@Before
	public void prepareRatingBar() 
	{
		view.setNumStars(NUM_STARS_TO_SHOW);
	}
	
	@Test
	public void whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		int newRating = nextInt(NUM_STARS_TO_SHOW);
		
		attribute.valueModelUpdated(newRating);

		assertThat(view.getRating(), equalTo((float)newRating));
	}
	
	@Test
	public void whenRatingIsChanged_thenUpdateValueModel()
	{
		ValueModel<Integer> valueModel = twoWayBindToProperty(Integer.class);
		
		view.setRating(anyInteger());
		
		assertThat((float)valueModel.getValue(), equalTo(view.getRating()));
	}
	
	@Test
	public void whenTwoWayBinding_thenRegisterWithMulticastListener()
	{
		twoWayBindToProperty(Integer.class);
		
		assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
	}
}
