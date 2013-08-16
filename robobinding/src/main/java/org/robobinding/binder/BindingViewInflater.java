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

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.NonBindingViewInflater;
import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForViewImpl;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewFactory.ViewCreationListener;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingViewInflater implements ViewCreationListener {
    private final NonBindingViewInflater nonBindingViewInflater;
    private final BindingAttributeResolver bindingAttributeResolver;
    private final BindingAttributeParser bindingAttributeParser;

    private ViewHierarchyInflationErrorsException errors;
    private List<ResolvedBindingAttributesForView> resolvedBindingAttributesForChildViews;

    public BindingViewInflater(NonBindingViewInflater nonBindingViewInflater, 
	    BindingAttributeResolver bindingAttributeResolver,
	    BindingAttributeParser bindingAttributeParser) {
	this.nonBindingViewInflater = nonBindingViewInflater;
	this.bindingAttributeResolver = bindingAttributeResolver;
	this.bindingAttributeParser = bindingAttributeParser;
    }

    public InflatedView inflateView(int layoutId) {
	return inflateView(layoutId, (ViewGroup) null);
    }

    public InflatedView inflateView(int layoutId, ViewGroup attachToRoot) {
	resolvedBindingAttributesForChildViews = newArrayList();
	errors = new ViewHierarchyInflationErrorsException();

	View rootView = nonBindingViewInflater.inflate(layoutId, attachToRoot);

	return new InflatedView(rootView, resolvedBindingAttributesForChildViews, errors);
    }

    public InflatedView inflateView(int layoutId, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	resolvedBindingAttributesForChildViews = newArrayList();
	errors = new ViewHierarchyInflationErrorsException();

	View rootView = nonBindingViewInflater.inflate(layoutId);
	addPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup, rootView);

	return new InflatedView(rootView, resolvedBindingAttributesForChildViews, errors);
    }

    private void addPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup, 
	    View rootView) {
	for (PredefinedPendingAttributesForView predefinedPendingAttributesForView : predefinedPendingAttributesForViewGroup) {
	    PendingAttributesForView pendingAttributesForView = predefinedPendingAttributesForView.createPendingAttributesForView(rootView);
	    resolveAndAddViewBindingAttributes(pendingAttributesForView);
	}
    }

    @Override
    public void onViewCreated(View childView, AttributeSet attrs) {
	Map<String, String> pendingAttributeMappings = bindingAttributeParser.parse(attrs);
	if (!pendingAttributeMappings.isEmpty()) {
	    PendingAttributesForView pendingAttributesForView = new PendingAttributesForViewImpl(childView, pendingAttributeMappings);
	    resolveAndAddViewBindingAttributes(pendingAttributesForView);
	}
    }

    private void resolveAndAddViewBindingAttributes(PendingAttributesForView pendingAttributesForView) {
	ViewResolutionResult viewResolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
	viewResolutionResult.addPotentialErrorTo(errors);
	resolvedBindingAttributesForChildViews.add(viewResolutionResult.getResolvedBindingAttributes());
    }
}
