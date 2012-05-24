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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.binder.BindingAttributeParser;
import org.robobinding.binder.BindingAttributeResolver;
import org.robobinding.binder.ViewInflater;
import org.robobinding.binder.BindingAttributeResolver.ViewBindingAttributes;
import org.robobinding.binder.ViewInflater.InflatedView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewInflaterTest
{
	private LayoutInflater layoutInflater;
	private ViewInflater.Builder viewInflaterBuilder;
	private BindingAttributeParser bindingAttributesParser;
	private List<OnViewCreatedInvocation> onViewCreatedInvocations;
	private int layoutId = 0;
	
	@Before
	public void setUp()
	{
		layoutInflater = mock(LayoutInflater.class);
		viewInflaterBuilder = new ViewInflater.Builder(null);
		bindingAttributesParser = mock(BindingAttributeParser.class);
		onViewCreatedInvocations = Lists.newArrayList();
	}
	
	@Test
	public void whenInflateView_thenResultViewShouldBeReturned()
	{
		View resultView = mock(View.class);
		when(layoutInflater.inflate(layoutId, null)).thenReturn(resultView);
		
		View view = inflateView();
		
		assertThat(view, sameInstance(resultView));
	}
	
	@Test
	public void givenAttachToParentView_whenInflateView_thenResultViewWithAttachingToParentViewShouldBeReturned()
	{
		ViewGroup parentViewToAttach = attachToParentView();
		View resultViewWithAttachingToParentView = mock(View.class);
		when(layoutInflater.inflate(layoutId, parentViewToAttach, true)).thenReturn(resultViewWithAttachingToParentView);
		
		View view = inflateView();
		
		assertThat(view, sameInstance(resultViewWithAttachingToParentView));
	}

	private View inflateView()
	{
		ViewInflater viewInflater = new ViewInflaterForTest(viewInflaterBuilder, bindingAttributesParser);
		View view = viewInflater.inflateView(layoutId);
		return view;
	}

	@Test
	public void whenInflateBindingView_thenInflatedViewShouldBeReturned()
	{
		View resultView = mock(View.class);
		when(layoutInflater.inflate(layoutId, null)).thenReturn(resultView);
		
		InflatedView inflatedView = inflateBindingView();
		
		assertThat(inflatedView.getRootView(), sameInstance(resultView));
	}
	
	@Test
	public void givenAttachToParentView_whenInflateBindingView_thenInflatedViewWithAttachingToParentViewShouldReturned()
	{
		ViewGroup parentViewToAttach = attachToParentView();
		View resultViewWithAttachingToParentView = mock(View.class);
		when(layoutInflater.inflate(layoutId, parentViewToAttach, true)).thenReturn(resultViewWithAttachingToParentView);
		
		InflatedView inflatedView = inflateBindingView();
		
		assertThat(inflatedView.getRootView(), sameInstance(resultViewWithAttachingToParentView));
	}
	
	@Test
	public void givenAChildViewWithBindingAttributes_whenInflateBindingView_thenAChildViewBindingAttributesShouldBeAdded()
	{
		declareChildView(true);
		
		InflatedView inflatedView = inflateBindingView();
		
		assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(1));
	}

	@Test
	public void givenAChildViewWithoutBindingAttributes_whenInflateBindingView_thenNoChildViewBindingAttributesShouldBeAdded()
	{
		declareChildView(false);
		
		InflatedView inflatedView = inflateBindingView();
		
		assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(0));
	}
	
	private ViewGroup attachToParentView()
	{
		ViewGroup parentView = mock(ViewGroup.class);
		viewInflaterBuilder.setParentViewToAttach(parentView);
		return parentView;
	}

	private void declareChildView(boolean withBindingAttributes)
	{
		Map<String, String> pendingAttributeMappings;
		if(withBindingAttributes)
		{
			pendingAttributeMappings = Maps.newHashMap();
			pendingAttributeMappings.put("attribute", "attributeValue");
		}else
		{
			pendingAttributeMappings = Collections.emptyMap();
		}
		when(bindingAttributesParser.parse(any(AttributeSet.class))).thenReturn(pendingAttributeMappings);
		
		onViewCreatedInvocations.add(new OnViewCreatedInvocation());
	}

	@Test
	public void givenAPredefinedViewPendingAttributes_whenInflateBindingView_thenChildViewBindingAttributesIsAdded()
	{
		declarePredefinedViewPendingAttributes();
		
		InflatedView inflatedView = inflateBindingView();
		
		assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(1));
	}
	
	private InflatedView inflateBindingView()
	{
		ViewInflater viewInflater = new ViewInflaterForTest(viewInflaterBuilder, bindingAttributesParser);
		InflatedView inflatedView = viewInflater.inflateBindingView(layoutId);
		return inflatedView;
	}

	private int numberOfChildViewBindingAttributes(InflatedView inflatedView)
	{
		List<ViewBindingAttributes> childViewBindingAttributesGroup = inflatedView.childViewBindingAttributesGroup;
		return childViewBindingAttributesGroup.size();
	}
	
	private void declarePredefinedViewPendingAttributes()
	{
		PredefinedPendingAttributesForView predefinedViewPendingAttributes = mock(PredefinedPendingAttributesForView.class);
		PendingAttributesForView viewPendingAttributes = mock(PendingAttributesForView.class);
		when(predefinedViewPendingAttributes.createViewPendingAttributes(any(View.class))).thenReturn(viewPendingAttributes);
		
		viewInflaterBuilder.addPredefinedPendingAttributesForView(predefinedViewPendingAttributes);
	}

	private class ViewInflaterForTest extends ViewInflater
	{
		public ViewInflaterForTest(Builder viewInflaterBuilder, BindingAttributeParser bindingAttributeParser)
		{
			super(viewInflaterBuilder);
			this.bindingAttributeParser = bindingAttributeParser;
			
			bindingAttributeResolver = mock(BindingAttributeResolver.class);
			ViewResolutionResult viewResolutionResult = emptyViewResolutionResult();
			when(bindingAttributeResolver.resolve(any(PendingAttributesForView.class))).thenReturn(viewResolutionResult);
		}
		
		private ViewResolutionResult emptyViewResolutionResult()
		{
			ViewBindingAttributes viewBindingAttributes = mock(ViewBindingAttributes.class);
			ViewResolutionErrors errors = mock(ViewResolutionErrors.class);
			return new ViewResolutionResult(viewBindingAttributes, errors);
		}
		
		@Override
		LayoutInflater createLayoutInflaterWithCustomViewFactory(Context context)
		{
			return layoutInflater;
		}
		
		@Override
		View inflate(int layoutId)
		{
			View view = super.inflate(layoutId);
			for(OnViewCreatedInvocation invocation : onViewCreatedInvocations)
			{
				invocation.execute(this);
			}
			return view;
		}
		
	}
	
	private static class OnViewCreatedInvocation
	{
		public void execute(ViewInflater viewInflater)
		{
			viewInflater.onViewCreated(null, null);
		}
	}
}
