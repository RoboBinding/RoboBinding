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
package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class EmptyViewVisibilityTest
{
	private View view;
	private AdapterView<?> adapterView;
	private ViewGroup parent;

	@Before
	public void setUp()
	{
		view = mock(View.class);
		adapterView = mock(AdapterView.class);
		parent = mock(ViewGroup.class);
		when(adapterView.getParent()).thenReturn(parent);
	}
	
	@Test
	public void givenViewIsNotEmptyView_whenMakingVisible_thenViewShouldBecomeSiblingOfAdapterView()
	{
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);
		
		emptyViewVisibility.makeVisible();
		
		verify(parent).addView(view);
	}
	
	@Test
	public void givenViewIsNotEmptyView_whenMakingVisible_thenViewShouldBecomeEmptyView()
	{
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);
		
		emptyViewVisibility.makeVisible();
		
		verify(adapterView).setEmptyView(view);
	}
	
	@Test
	public void givenViewIsEmptyView_whenMakingGone_thenViewShouldBeRemovedAsSiblingOfAdapterView()
	{
		when(adapterView.getEmptyView()).thenReturn(view);
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);
	
		emptyViewVisibility.makeGone();
		
		verify(parent).removeView(view);
	}
	
	@Test
	public void givenViewIsEmptyView_whenMakingGone_thenEmptyViewShouldBeSetToNull()
	{
		when(adapterView.getEmptyView()).thenReturn(view);
		EmptyViewVisibility emptyViewVisibility = new EmptyViewVisibility(adapterView, view);
	
		emptyViewVisibility.makeGone();
		
		verify(adapterView).setEmptyView(null);
	}
}
