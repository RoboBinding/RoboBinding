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
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ViewListenersProvider;
import org.robobinding.viewattribute.compoundbutton.CompoundButtonListeners;
import org.robobinding.viewattribute.seekbar.SeekBarListeners;
import org.robobinding.viewattribute.view.ViewListeners;

import android.view.View;
import android.widget.CheckBox;
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
	public static ViewAndViewListenersType[] viewAndViewListenersTypeArray = new ViewAndViewListenersType[]{
		new ViewAndViewListenersType(mock(View.class), ViewListeners.class),
		new ViewAndViewListenersType(mock(CheckBox.class), CompoundButtonListeners.class), 
		new ViewAndViewListenersType(mock(SeekBar.class), SeekBarListeners.class)};
	
	private ViewListenersProvider viewListenersProvider;
	
	@Before
	public void setUp()
	{
		viewListenersProvider = new ViewListenersProviderImpl();
	}
	

	@Theory
	public void whenAskViewListenersForView_thenReturnViewListenerOfCorrectType(ViewAndViewListenersType viewAndViewListenersType)
	{
		ViewListeners viewListeners = viewListenersProvider.forView(viewAndViewListenersType.view);
		
		assertThat(viewListeners, instanceOf(viewAndViewListenersType.viewListenersType));
	}
	
	@Theory
	public void whenAskViewListenersForViewAgain_thenReturnTheSameInstance(ViewAndViewListenersType viewAndViewListenersType)
	{
		ViewListeners viewListeners1 = viewListenersProvider.forView(viewAndViewListenersType.view);
		
		ViewListeners viewListeners2 = viewListenersProvider.forView(viewAndViewListenersType.view);
		
		assertThat(viewListeners1, sameInstance(viewListeners2));
	}
	
	private static class ViewAndViewListenersType
	{
		private View view;
		private Class<? extends ViewListeners> viewListenersType;
		public ViewAndViewListenersType(View view, Class<? extends ViewListeners> viewListenersType)
		{
			this.view = view;
			this.viewListenersType = viewListenersType;
		}
	}
}
