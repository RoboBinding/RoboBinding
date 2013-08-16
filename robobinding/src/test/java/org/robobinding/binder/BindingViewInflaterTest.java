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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewResolutionErrors;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class BindingViewInflaterTest {
    @Mock
    private BindingAttributeResolver bindingAttributeResolver;
    @Mock
    private BindingAttributeParser bindingAttributeParser;
    private BindingViewInflater bindingViewInflater;
    
    private int layoutId = 0;

    @Before
    public void setUp() {
	ViewResolutionResult viewResolutionResult = emptyViewResolutionResult();
	when(bindingAttributeResolver.resolve(any(PendingAttributesForView.class))).thenReturn(viewResolutionResult);
	
	bindingViewInflater = new BindingViewInflater(
		new NonBindingViewInflaterWithOnViewCreationCall(mock(NonBindingViewInflater.class)),
		bindingAttributeResolver, 
		bindingAttributeParser);
    }

    private ViewResolutionResult emptyViewResolutionResult() {
	ResolvedBindingAttributesForView viewBindingAttributes = mock(ResolvedBindingAttributesForView.class);
	ViewResolutionErrors errors = mock(ViewResolutionErrors.class);
	return new ViewResolutionResult(viewBindingAttributes, errors);
    }

    @Test
    public void givenAChildViewWithBindingAttributes_whenInflateView_thenAChildViewBindingAttributesShouldBeAdded() {
	declareAChildView();

	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId);

	assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(1));
    }

    private void declareAChildView() {
        Map<String, String> pendingAttributeMappings = Maps.newHashMap();
        pendingAttributeMappings.put("attribute", "attributeValue");
        when(bindingAttributeParser.parse(any(AttributeSet.class))).thenReturn(pendingAttributeMappings);
    }

    @Test
    public void givenAChildViewWithoutBindingAttributes_whenInflateBindingView_thenNoChildViewBindingAttributesShouldBeAdded() {
	declareEmptyChildViews();

	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId);

	assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(0));
    }

    private void declareEmptyChildViews() {
	when(bindingAttributeParser.parse(any(AttributeSet.class))).thenReturn(Collections.<String, String>emptyMap());
    }

    @Test
    public void givenAPredefinedPendingAttributesForView_whenInflateView_thenChildViewBindingAttributesIsAdded() {
	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId, createAPredefinedPendingAttributesForView());

	assertThat(numberOfChildViewBindingAttributes(inflatedView), equalTo(1));
    }

    private int numberOfChildViewBindingAttributes(InflatedView inflatedView) {
	List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup = inflatedView.childViewBindingAttributesGroup;
	return childViewBindingAttributesGroup.size();
    }

    private Collection<PredefinedPendingAttributesForView> createAPredefinedPendingAttributesForView() {
	PredefinedPendingAttributesForView predefinedPendingAttributesForView = mock(PredefinedPendingAttributesForView.class);
	PendingAttributesForView pendingAttributesForView = mock(PendingAttributesForView.class);
	when(predefinedPendingAttributesForView.createPendingAttributesForView(any(View.class))).thenReturn(pendingAttributesForView);

	return Lists.newArrayList(predefinedPendingAttributesForView);
    }
    
    private class NonBindingViewInflaterWithOnViewCreationCall extends NonBindingViewInflater
    {
	private final NonBindingViewInflater forwardingViewInflater;
	public NonBindingViewInflaterWithOnViewCreationCall(NonBindingViewInflater nonBindingViewInflater) {
	    super(null);
	    this.forwardingViewInflater = nonBindingViewInflater;
	}
	
	@Override
	public View inflate(int layoutId) {
	    View resultView = forwardingViewInflater.inflate(layoutId);
	    performOnViewCreationCall(resultView);
	    return resultView;
	}
	
	private void performOnViewCreationCall(View view) {
	    bindingViewInflater.onViewCreated(view, mock(AttributeSet.class));
	}

	@Override
	public View inflate(int layoutId, ViewGroup attachToRoot) {
	    View resultView = forwardingViewInflater.inflate(layoutId, attachToRoot);
	    performOnViewCreationCall(resultView);
	    return resultView;
	}
    }
}
