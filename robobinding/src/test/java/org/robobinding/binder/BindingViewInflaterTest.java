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
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.binder.BindingViewInflaterForTest.aBindingViewInflater;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewInflater;
import org.robobinding.ViewResolutionErrors;
import org.robobinding.binder.BindingViewInflater.InflatedView;

import android.app.Activity;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.collect.Maps;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class BindingViewInflaterTest {
    private BindingAttributeParser bindingAttributesParser;
    private BindingViewInflaterForTest.Builder bindingViewInflatorBuilder;

    @Before
    public void setUp() {
	ViewInflater viewInflator = mock(ViewInflater.class);
	View view = mock(View.class);
	when(viewInflator.inflateView(anyInt())).thenReturn(view);
	bindingAttributesParser = mock(BindingAttributeParser.class);

	BindingAttributeResolver bindingAttributeResolver = mock(BindingAttributeResolver.class);
	ViewResolutionResult viewResolutionResult = emptyViewResolutionResult();
	when(bindingAttributeResolver.resolve(any(PendingAttributesForView.class))).thenReturn(viewResolutionResult);

	bindingViewInflatorBuilder = aBindingViewInflater(new Activity()).withViewInflator(viewInflator)
		.withBindingAttributeParser(bindingAttributesParser).withBindingAttributeResolver(bindingAttributeResolver);
    }

    private ViewResolutionResult emptyViewResolutionResult() {
	ResolvedBindingAttributesForView viewBindingAttributes = mock(ResolvedBindingAttributesForView.class);
	ViewResolutionErrors errors = mock(ViewResolutionErrors.class);
	return new ViewResolutionResult(viewBindingAttributes, errors);
    }

    @Test
    public void givenAChildViewWithBindingAttributes_whenInflateView_thenAChildViewBindingAttributesShouldBeAdded() {
	declareChildView(true);

	InflatedView inflatedView = inflateView();

	assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(1));
    }

    @Test
    public void givenAChildViewWithoutBindingAttributes_whenInflateBindingView_thenNoChildViewBindingAttributesShouldBeAdded() {
	declareChildView(false);

	InflatedView inflatedView = inflateView();

	assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(0));
    }

    private void declareChildView(boolean withBindingAttributes) {
	Map<String, String> pendingAttributeMappings;
	if (withBindingAttributes) {
	    pendingAttributeMappings = Maps.newHashMap();
	    pendingAttributeMappings.put("attribute", "attributeValue");
	} else {
	    pendingAttributeMappings = Collections.emptyMap();
	}
	when(bindingAttributesParser.parse(any(AttributeSet.class))).thenReturn(pendingAttributeMappings);

	bindingViewInflatorBuilder.withOnViewCreatedInvocation(mock(View.class), mock(AttributeSet.class));
    }

    @Test
    public void givenAPredefinedPendingAttributesForView_whenInflateView_thenChildViewBindingAttributesIsAdded() {
	declarePredefinedPendingAttributesForView();

	InflatedView inflatedView = inflateView();

	assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(1));
    }

    private InflatedView inflateView() {
	BindingViewInflater bindingViewInflater = bindingViewInflatorBuilder.build();
	int layoutId = 0;
	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId);
	return inflatedView;
    }

    private int numberOfChildViewBindingAttributes(InflatedView inflatedView) {
	List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup = inflatedView.childViewBindingAttributesGroup;
	return childViewBindingAttributesGroup.size();
    }

    private void declarePredefinedPendingAttributesForView() {
	PredefinedPendingAttributesForView predefinedPendingAttributesForView = mock(PredefinedPendingAttributesForView.class);
	PendingAttributesForView pendingAttributesForView = mock(PendingAttributesForView.class);
	when(predefinedPendingAttributesForView.createPendingAttributesForView(any(View.class))).thenReturn(pendingAttributesForView);

	bindingViewInflatorBuilder.withPredefinedPendingAttributesForView(predefinedPendingAttributesForView);
    }
}
