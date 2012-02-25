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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

import android.widget.RatingBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnRatingBarChangeAttributeTest extends AbstractCommandViewAttributeWithViewListenersAwareTest<RatingBar, OnRatingBarChangeAttribute, MockRatingBarListeners>
{
	private float newRatingValue;

	@Before
	public void setUp()
	{
		newRatingValue = RandomValues.anyFloat();
	}
	
	@Test
	public void givenBoundAttribute_whenChangeChecked_thenEventReceived()
	{
		bindAttribute();

		updateRating();

		assertEventReceived();
	}

	@Test
	public void whenBinding_thenRegisterWithMulticastListener()
	{
		bindAttribute();
		
		assertTrue(viewListeners.addOnRatingBarChangeListenerInvoked);
	}
	
	private void updateRating()
	{
		view.setRating(newRatingValue);
	}

	private void assertEventReceived()
	{
		assertEventReceived(RatingBarEvent.class);
		RatingBarEvent ratingBarEvent = getEventReceived();
		assertThat(ratingBarEvent.getRatingBar(), sameInstance(view));
		assertThat((double)ratingBarEvent.getRating(), is(closeTo(newRatingValue, 0.1f)));
		assertTrue(ratingBarEvent.isFromUser());
	}
}
