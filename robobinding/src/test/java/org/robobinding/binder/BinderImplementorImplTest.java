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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.binder.BinderImplementorImpl;
import org.robobinding.binder.ViewInflater;
import org.robobinding.binder.ViewInflater.InflatedView;

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
	private ViewInflater viewInflater;
	private int layoutId = 0;
	
	@Before
	public void setUp()
	{
		binderImplementor = new BinderImplementorImplForTest();
		viewInflater = mock(ViewInflater.class);
	}
	
	@Test
	public void whenInflateAndBind_thenViewWithBindingShouldBeReturned()
	{
		Object presentationModel = new Object();
		View viewWithBinding = mock(View.class);
		InflatedView inflatedView = mock(InflatedView.class);
		when(inflatedView.getRootView()).thenReturn(viewWithBinding);
		when(viewInflater.inflateBindingView(layoutId)).thenReturn(inflatedView);
		
		View view = binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		assertThat(view, sameInstance(viewWithBinding));
	}
	
	@Test
	public void whenInflateAndBind_thenChildViewsShouldBeBound()
	{
		Object presentationModel = new Object();
		InflatedView inflatedView = mock(InflatedView.class);
		when(viewInflater.inflateBindingView(layoutId)).thenReturn(inflatedView);
		
		binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		verify(inflatedView).bindChildViews(any(BindingContext.class));
	}
	
	@Test
	public void whenInflateOnly_thenViewWithInflatingOnlyShouldBeReturned()
	{
		View viewWithInflatingOnly = mock(View.class);
		when(viewInflater.inflateView(layoutId)).thenReturn(viewWithInflatingOnly);
		
		View view = binderImplementor.inflateOnly(layoutId);
		
		assertThat(view, sameInstance(viewWithInflatingOnly));
	}
	
	public class BinderImplementorImplForTest extends BinderImplementorImpl
	{
		public BinderImplementorImplForTest()
		{
			super(new Activity(), mock(BindingContextCreator.class));
		}
		@Override
		ViewInflater createViewInflater()
		{
			return viewInflater;
		}
	}
}
