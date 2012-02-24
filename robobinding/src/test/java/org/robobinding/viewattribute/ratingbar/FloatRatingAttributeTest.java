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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.ratingbar.RatingAttribute.FloatRatingAttribute;
import org.robobinding.viewattribute.view.AbstractPropertyViewAttributeWithViewListenersAwareTest;

import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FloatRatingAttributeTest extends AbstractPropertyViewAttributeWithViewListenersAwareTest<RatingBar, FloatRatingAttribute, MockRatingBarListeners>
{
	@Test
	public void whenValueModelUpdated_thenViewShouldReflectChanges()
	{
		float newRating = RandomValues.anyFloat();
		
		attribute.valueModelUpdated(newRating);

		assertThat((double)view.getRating(), closeTo(newRating, 0.1));
	}
	
	@Test
	public void whenRatingIsChanged_thenUpdateValueModel()
	{
		ValueModel<Float> valueModel = twoWayBindToProperty(Float.class);
		
		view.setRating(RandomValues.anyFloat());
		
		assertThat((double)valueModel.getValue(), closeTo(view.getRating(), 0.1));
	}
	
	@Test
	public void whenTwoWayBinding_thenRegisterWithMulticastListener()
	{
		RatingBarListeners mockRatingBarListeners = mock(RatingBarListeners.class);
		attribute.setViewListeners(mockRatingBarListeners);
		
		twoWayBindToProperty(Float.class);
		
		verify(mockRatingBarListeners).addOnRatingBarChangeListener(any(OnRatingBarChangeListener.class));
	}
}
