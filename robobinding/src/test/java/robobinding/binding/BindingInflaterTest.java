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

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.android.R;
import robobinding.binding.BindingLayoutInflater.BindingFactoryProvider;
import robobinding.binding.BindingLayoutInflater.InflationResult;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class BindingInflaterTest
{
	private Context context = new Activity();
	private LayoutInflater layoutInflater = LayoutInflater.from(context);
	private BindingLayoutInflater binder = new BindingLayoutInflater();
	private int activityLayoutId = R.layout.no_bound_views_activity;
		
	@Test
	public void whenInflatingView_ThenReturnRootViewInResult()
	{
		InflationResult inflationResult = binder.inflateView(context, activityLayoutId);
		
		assertViewsAreEqual(inflationResult.getRootView(), layoutInflater.inflate(activityLayoutId, null, false));
	}
	
	@Test
	public void givenAViewWithTwoBoundChildViews_WhenInflatingView_ThenReturnChildViewsWithBindingsInResult()
	{
		viewWithBoundChildViews(2);
		
		InflationResult inflationResult = binder.inflateView(context, activityLayoutId);
		
		assertThat(inflationResult.getViewAttributeBinders().size(), equalTo(2));
	}
	
	@Test
	public void givenAViewWithNoBoundChildViews_WhenInflatingView_ThenReturnZeroChildViewsWithBindingsInResult()
	{
		viewWithNoBoundChildViews();
		
		InflationResult inflationResult = binder.inflateView(context, activityLayoutId);
		
		assertThat(inflationResult.getViewAttributeBinders().size(), equalTo(0));
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
				List<ViewAttributeBinder> viewsAttributeBinders = Lists.newArrayList();
				
				for (int i = 0; i < boundChildViews; i++)
					viewsAttributeBinders.add(new ViewAttributeBinder(new View(context), MockAttributeSet.withNoBindingAttributes()));
					
				when(bindingFactory.getViewAttributeBinders()).thenReturn(viewsAttributeBinders);
				
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
