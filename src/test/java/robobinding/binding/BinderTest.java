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
package robobinding.binding;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.android.R;
import robobinding.binding.Binder.BindingFactoryProvider;
import robobinding.binding.Binder.InflationResult;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class BinderTest
{	
	private Context context = new Activity();
	private LayoutInflater layoutInflater = LayoutInflater.from(context);
	private Binder binder = new Binder();
	private int activityLayoutId = R.layout.no_bound_views_activity;
	private ViewGroup rootViewGroup = null;
	private boolean attachToRoot = false;
	
	@Test
	public void whenInflatingView_ThenReturnRootViewInResult()
	{
		InflationResult inflationResult = binder.inflateView(context, activityLayoutId, rootViewGroup, attachToRoot);
		assertViewsAreEqual(inflationResult.getRootView(), layoutInflater.inflate(activityLayoutId, rootViewGroup, attachToRoot));
	}
	
	@Test
	public void givenAViewWithTwoBoundChildViews_WhenInflatingView_ThenReturnChildViewsWithBindingsInResult()
	{
		viewWithBoundChildViews(2);
		
		InflationResult inflationResult = binder.inflateView(context, activityLayoutId, rootViewGroup, attachToRoot);
		assertThat(inflationResult.getChildViewBindingsMap().size(), equalTo(2));
	}
	
	@Test
	public void givenAViewWithNoBoundChildViews_WhenInflatingView_ThenReturnZeroChildViewsWithBindingsInResult()
	{
		viewWithNoBoundChildViews();
		
		InflationResult inflationResult = binder.inflateView(context, activityLayoutId, rootViewGroup, attachToRoot);
		assertThat(inflationResult.getChildViewBindingsMap().size(), equalTo(0));
	}
	
	private void viewWithNoBoundChildViews()
	{
		viewWithBoundChildViews(0);
	}

	private void viewWithBoundChildViews(final int boundChildViews)
	{
		binder.setBindingFactoryProvider(new BindingFactoryProvider(){
			BindingFactory getBindingFactory(LayoutInflater layoutInflater)
			{
				BindingFactory bindingFactory = mock(BindingFactory.class);
				HashMap<View, BindingAttributeMap> viewsAndBindings = new HashMap<View, BindingAttributeMap>();
				
				for (int i = 0; i < boundChildViews; i++)
					viewsAndBindings.put(new View(context), new BindingAttributeMap());
					
				when(bindingFactory.getViewBindingsMap()).thenReturn(viewsAndBindings);
				
				return bindingFactory;
			}
		});
	}
	
	private void assertViewsAreEqual(View viewOne, View viewTwo)
	{
		assertThat(viewOne, instanceOf(viewTwo.getClass()));
		
		if (viewOne instanceof ViewGroup)
		{
			ViewGroup viewGroupOne = (ViewGroup)viewOne;
			ViewGroup viewGroupTwo = (ViewGroup)viewTwo;
			
			assertThat(viewGroupOne.getChildCount(), equalTo(viewGroupTwo.getChildCount()));
			
			for (int i = 0; i < viewGroupOne.getChildCount(); i++)
				assertThat(viewGroupOne.getChildAt(i), instanceOf(viewGroupTwo.getChildAt(i).getClass()));
		}
	}
}
