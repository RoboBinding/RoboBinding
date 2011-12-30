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
import org.robobinding.viewattribute.MockPresentationModelAdapterForProperty;
import org.robobinding.viewattribute.PropertyBindingDetails;

import android.app.Activity;
import android.widget.RatingBar;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class RatingAttributeTest
{
	private static final float NEW_RATING_AS_FLOAT = 2.0f;
	private static final int NEW_RATING_AS_INT = 2;
	private RatingBar ratingBar;
	
	@Before
	public void setUp()
	{
		ratingBar = new RatingBar(new Activity());
		//TODO Remove this next line once Robolectric autoinitializes this field to 100 as it should do (pull request submitted)
		ratingBar.setMax(100);
	}
	
	@Test
	public void givenValueModelIsTypeFloat_WhenUpdatingPresentationModel_ThenSetRatingOnRatingBar()
	{
		RatingAttribute ratingAttribute = new RatingAttribute();
		ratingAttribute.setView(ratingBar);
		ratingAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(MockPresentationModelAdapterForProperty.ONE_WAY_BINDING_PROPERTY_NAME, true));
		MockPresentationModelAdapterForProperty<Float> presentationModelAdapter = MockPresentationModelAdapterForProperty.bindToProperty(ratingAttribute, float.class);
		
		presentationModelAdapter.updatePropertyValue(NEW_RATING_AS_FLOAT);
		
		assertThat(ratingBar.getRating(), is(NEW_RATING_AS_FLOAT));
	}
	
	@Test
	@Ignore //Waiting for Robolectric update
	public void givenValueModelIsTypeFloat_WhenUpdatingView_ThenSetValueOnPresentationModel()
	{
		RatingAttribute ratingAttribute = new RatingAttribute();
		ratingAttribute.setView(ratingBar);
		ratingAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(MockPresentationModelAdapterForProperty.TWO_WAY_BINDING_PROPERTY_NAME, true));
		MockPresentationModelAdapterForProperty<Float> presentationModelAdapter = MockPresentationModelAdapterForProperty.bindToProperty(ratingAttribute, float.class);
		
		ratingBar.setRating(NEW_RATING_AS_FLOAT);
		
		assertThat(presentationModelAdapter.getPropertyValue(), is(NEW_RATING_AS_FLOAT));
	}
	
	@Test
	public void givenValueModelIsTypeInteger_WhenUpdatingPresentationModel_ThenSetRatingOnRatingBar()
	{
		RatingAttribute ratingAttribute = new RatingAttribute();
		ratingAttribute.setView(ratingBar);
		ratingAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(MockPresentationModelAdapterForProperty.ONE_WAY_BINDING_PROPERTY_NAME, true));
		MockPresentationModelAdapterForProperty<Integer> presentationModelAdapter = MockPresentationModelAdapterForProperty.bindToProperty(ratingAttribute, int.class);
		
		presentationModelAdapter.updatePropertyValue(NEW_RATING_AS_INT);
		
		assertThat(ratingBar.getRating(), is(NEW_RATING_AS_FLOAT));
	}
	
	@Test
	@Ignore //Waiting for Robolectric update
	public void givenValueModelIsTypeInteger_WhenUpdatingView_ThenSetValueOnPresentationModel()
	{
		RatingAttribute ratingAttribute = new RatingAttribute();
		ratingAttribute.setView(ratingBar);
		ratingAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(MockPresentationModelAdapterForProperty.TWO_WAY_BINDING_PROPERTY_NAME, true));
		MockPresentationModelAdapterForProperty<Integer> presentationModelAdapter = MockPresentationModelAdapterForProperty.bindToProperty(ratingAttribute, int.class);
		
		ratingBar.setRating(NEW_RATING_AS_FLOAT);
		
		assertThat(presentationModelAdapter.getPropertyValue(), is(NEW_RATING_AS_INT));
	}
}
