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
package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersProvider;
import org.robobinding.viewattribute.compoundbutton.CheckedAttribute;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonListeners;
import org.robobinding.viewattribute.ratingbar.OnRatingBarChangeAttribute;
import org.robobinding.viewattribute.ratingbar.RatingBarListeners;
import org.robobinding.viewattribute.seekbar.OnSeekBarChangeAttribute;
import org.robobinding.viewattribute.seekbar.SeekBarListeners;
import org.robobinding.viewattribute.view.OnFocusAttribute;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SeekBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ViewListenersProviderImplTest
{
	@DataPoints
	public static ViewListenersAttributeViewAndViewListenersClass[] sampleData = new ViewListenersAttributeViewAndViewListenersClass[]{
		new ViewListenersAttributeViewAndViewListenersClass(mock(OnFocusAttribute.class), mock(View.class), ViewListeners.class),
		new ViewListenersAttributeViewAndViewListenersClass(mock(CheckedAttribute.class), mock(CheckBox.class), CompoundButtonListeners.class),
		new ViewListenersAttributeViewAndViewListenersClass(mock(OnSeekBarChangeAttribute.class), mock(SeekBar.class), SeekBarListeners.class)};
	
	private ViewListenersProvider viewListenersProvider;
	
	@Before
	public void setUp()
	{
		viewListenersProvider = new ViewListenersProviderImpl();
	}

	@Theory
	public void whenAskingForView_thenReturnViewListenerOfCorrectType(ViewListenersAttributeViewAndViewListenersClass viewAndViewListenersType)
	{
		ViewListeners viewListeners = viewListenersProvider.forViewAndAttribute(viewAndViewListenersType.view, viewAndViewListenersType.viewListenersAware);

		assertThat(viewListeners, instanceOf(viewAndViewListenersType.viewListenersType));
	}
	
	@Theory
	public void whenAskingForViewListenersAgain_thenReturnTheSameInstance(ViewListenersAttributeViewAndViewListenersClass viewAndViewListenersType)
	{
		ViewListeners viewListeners1 = viewListenersProvider.forViewAndAttribute(viewAndViewListenersType.view, viewAndViewListenersType.viewListenersAware);
		ViewListeners viewListeners2 = viewListenersProvider.forViewAndAttribute(viewAndViewListenersType.view, viewAndViewListenersType.viewListenersAware);

		assertThat(viewListeners1, sameInstance(viewListeners2));
	}
	
	@Test
	public void whenAskingForTwoDifferentViewListenersForTheSameView_thenReturnTheCorrectInstances()
	{
		RatingBar ratingBar = mock(RatingBar.class);
		ViewListeners viewListeners1 = viewListenersProvider.forViewAndAttribute(ratingBar, mock(OnRatingBarChangeAttribute.class));
		ViewListeners viewListeners2 = viewListenersProvider.forViewAndAttribute(ratingBar, mock(OnFocusAttribute.class));
	
		assertThat(viewListeners1, instanceOf(RatingBarListeners.class));
		assertThat(viewListeners2, instanceOf(ViewListeners.class));
	}
	
	private static class ViewListenersAttributeViewAndViewListenersClass
	{
		private final View view;
		private final Class<? extends ViewListeners> viewListenersType;
		private final ViewListenersAware<?> viewListenersAware;
		public ViewListenersAttributeViewAndViewListenersClass(ViewListenersAware<?> viewListenersAware, View view, Class<? extends ViewListeners> viewListenersType)
		{
			this.viewListenersAware = viewListenersAware;
			this.view = view;
			this.viewListenersType = viewListenersType;
		}
	}
	
}
