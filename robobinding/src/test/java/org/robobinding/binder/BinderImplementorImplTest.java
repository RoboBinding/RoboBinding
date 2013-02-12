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
import org.robobinding.binder.BinderImplementorImpl.BindingContextCreator;
import org.robobinding.binder.BindingViewInflater.InflatedView;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

import android.app.Activity;
import android.content.Context;
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
	private BindingContextCreator bindingContextCreator;
	private BindingContext bindingContext;
	private BinderImplementorImpl binderImplementor;
	private BindingViewInflater bindingViewInflater;
	private ErrorFormatter errorFormatter;
	private Object presentationModel = new Object();
	private InflatedView inflatedView;
	private int layoutId = 0;
	
	@Before
	public void setUp()
	{
		bindingContext = mock(BindingContext.class);
		bindingContextCreator = mock(BindingContextCreator.class);
		when(bindingContextCreator.create(presentationModel)).thenReturn(bindingContext);
		inflatedView = mock(InflatedView.class);
		bindingViewInflater = mock(BindingViewInflater.class);
		when(bindingViewInflater.inflateView(layoutId)).thenReturn(inflatedView);
		errorFormatter = new PlainTextErrorFormatter();
		binderImplementor = new BinderImplementorImplForTest(new Activity(), bindingContextCreator, errorFormatter);
	}
	
	@Test
	public void whenInflateAndBind_thenViewWithBindingShouldBeReturned()
	{
		View viewWithBinding = mock(View.class);
		when(inflatedView.getRootView()).thenReturn(viewWithBinding);
		
		View view = binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		assertThat(view, sameInstance(viewWithBinding));
	}
	
	@Test
	public void whenInflateAndBind_thenChildViewsShouldBeBound()
	{
		binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		verify(inflatedView).bindChildViews(any(BindingContext.class));
	}
	
	@Test
	public void whenInflateAndBind_thenViewInflationErrorsShouldBeAsserted()
	{
		binderImplementor.inflateAndBind(layoutId, presentationModel);
		
		verify(inflatedView).assertNoErrors(errorFormatter);
	}
	
	public class BinderImplementorImplForTest extends BinderImplementorImpl
	{
		public BinderImplementorImplForTest(Context context, BindingContextCreator bindingContextCreator, ErrorFormatter errorFormatter)
		{
			super(context, bindingContextCreator, errorFormatter);
		}

		@Override
		BindingViewInflater createBindingViewInflater(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
		{
			return bindingViewInflater;
		}
	}
}
