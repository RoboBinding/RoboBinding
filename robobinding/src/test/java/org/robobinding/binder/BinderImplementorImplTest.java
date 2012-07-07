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

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.binder.BinderImplementorImpl;
import org.robobinding.binder.BindingViewInflater;
import org.robobinding.binder.BindingViewInflater.InflatedView;
import org.robobinding.binder.BindingViewInflationErrorsException.ErrorFormatter;

import android.app.Activity;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BinderImplementorImplTest
{
	private BinderImplementorImplForTest binderImplementor;
	private BindingViewInflater viewInflater;
	private int layoutId = 0;
	
	@Before
	public void setUp()
	{
		binderImplementor = new BinderImplementorImplForTest();
		viewInflater = mock(BindingViewInflater.class);
	}
	
	@Test
	public void whenInflateAndBind_thenViewWithBindingShouldBeReturned()
	{
		Object presentationModel = new Object();
		View viewWithBinding = mock(View.class);
		InflatedView inflatedView = mock(InflatedView.class);
		when(inflatedView.getRootView()).thenReturn(viewWithBinding);
		when(viewInflater.inflateView(layoutId)).thenReturn(inflatedView);
		
		View view = binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		assertThat(view, sameInstance(viewWithBinding));
	}
	
	@Test
	public void whenInflateAndBind_thenChildViewsShouldBeBound()
	{
		Object presentationModel = new Object();
		InflatedView inflatedView = mock(InflatedView.class);
		when(viewInflater.inflateView(layoutId)).thenReturn(inflatedView);
		
		binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		verify(inflatedView).bindChildViews(any(BindingContext.class));
		verify(inflatedView).assertNoErrors(any(ErrorFormatter.class));
	}
	
	@Test
	public void whenInflateAndBind_thenViewInflationErrorsShouldBeAsserted()
	{
		Object presentationModel = new Object();
		InflatedView inflatedView = mock(InflatedView.class);
		when(viewInflater.inflateView(layoutId)).thenReturn(inflatedView);
		
		binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		verify(inflatedView).assertNoErrors(any(ErrorFormatter.class));
	}
	
	public class BinderImplementorImplForTest extends BinderImplementorImpl
	{
		public BinderImplementorImplForTest()
		{
			super(new Activity(), mock(BindingContextCreator.class));
		}
		@Override
		BindingViewInflater createBindingViewInflater(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
		{
			return viewInflater;
		}
	}
}
