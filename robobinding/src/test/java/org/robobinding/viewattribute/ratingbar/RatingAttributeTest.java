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
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.MockPresentationModelForProperty;

import android.widget.RatingBar;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class RatingAttributeTest extends AbstractPropertyViewAttributeTest<RatingBar, RatingAttribute>
{
	private static final float NEW_RATING_AS_FLOAT = 2.0f;
	private static final int NEW_RATING_AS_INT = 2;
	
	@Before
	public void setUp()
	{
		//TODO Remove this next line once Robolectric auto-initializes this field to 100 as it should do (pull request submitted)
		view.setMax(100);
	}
	
	@Test
	public void givenValueModelIsTypeFloat_WhenUpdatingPresentationModel_ThenSetRatingOnRatingBar()
	{
		MockPresentationModelForProperty<Float> presentationModel = initializeForOneWayBinding(float.class);
		
		presentationModel.updatePropertyValue(NEW_RATING_AS_FLOAT);
		
		assertThat(view.getRating(), is(NEW_RATING_AS_FLOAT));
	}
	
	@Test
	@Ignore //Waiting for Robolectric update
	public void givenValueModelIsTypeFloat_WhenUpdatingView_ThenSetValueOnPresentationModel()
	{
		MockPresentationModelForProperty<Float> presentationModel = initializeForTwoWayBinding(float.class);
		
		view.setRating(NEW_RATING_AS_FLOAT);
		
		assertThat(presentationModel.getPropertyValue(), is(NEW_RATING_AS_FLOAT));
	}
	
	@Test
	public void givenValueModelIsTypeInteger_WhenUpdatingPresentationModel_ThenSetRatingOnRatingBar()
	{
		MockPresentationModelForProperty<Integer> presentationModel = initializeForOneWayBinding(int.class);
		
		presentationModel.updatePropertyValue(NEW_RATING_AS_INT);
		
		assertThat(view.getRating(), is(NEW_RATING_AS_FLOAT));
	}
	
	@Test
	@Ignore //Waiting for Robolectric update
	public void givenValueModelIsTypeInteger_WhenUpdatingView_ThenSetValueOnPresentationModel()
	{
		MockPresentationModelForProperty<Integer> presentationModel = initializeForTwoWayBinding(int.class);
		
		view.setRating(NEW_RATING_AS_FLOAT);
		
		assertThat(presentationModel.getPropertyValue(), is(NEW_RATING_AS_INT));
	}
	
}
