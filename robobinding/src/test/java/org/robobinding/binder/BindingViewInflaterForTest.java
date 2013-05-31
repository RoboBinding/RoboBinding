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

import java.util.List;

import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.ViewInflater;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingViewInflaterForTest extends BindingViewInflater {
    private List<OnViewCreatedInvocation> onViewCreatedInvocations;

    private BindingViewInflaterForTest(BindingViewInflater.Builder bindingViewInflaterBuilder, Builder builder) {
	super(bindingViewInflaterBuilder);
	this.bindingAttributeParser = builder.bindingAttributesParser;
	this.bindingAttributeResolver = builder.bindingAttributeResolver;
	this.viewInflator = createViewInflator(builder);
	this.onViewCreatedInvocations = Lists.newArrayList(builder.onViewCreatedInvocations);
    }

    private ViewInflater createViewInflator(Builder builder) {
	if (builder.isViewInflaterSet()) {
	    return new ViewInflaterWithExtraInvocations(builder.viewInflator);
	} else {
	    return new ViewInflaterWithExtraInvocations(this.viewInflator);
	}
    }

    public static Builder aBindingViewInflater(Context context) {
	return new Builder(context);
    }

    public static class Builder {
	private List<OnViewCreatedInvocation> onViewCreatedInvocations;
	private Context context;
	private ViewInflater viewInflator;
	private BindingAttributeParser bindingAttributesParser;
	private BindingAttributeResolver bindingAttributeResolver;
	private List<PredefinedPendingAttributesForView> predefinedPendingAttributesForViews;

	private Builder(Context context) {
	    this.context = context;
	    this.onViewCreatedInvocations = Lists.newArrayList();
	    this.predefinedPendingAttributesForViews = Lists.newArrayList();
	    bindingAttributesParser = new BindingAttributeParser();

	    bindingAttributeResolver = new BindingAttributeResolver();
	}

	public boolean isViewInflaterSet() {
	    return viewInflator != null;
	}

	public Builder withOnViewCreatedInvocation(View view, AttributeSet bindingAttributeSet) {
	    this.onViewCreatedInvocations.add(new OnViewCreatedInvocation(view, bindingAttributeSet));
	    return this;
	}

	public Builder withViewInflator(ViewInflater viewInflator) {
	    this.viewInflator = viewInflator;
	    return this;
	}

	public Builder withBindingAttributeParser(BindingAttributeParser bindingAttributesParser) {
	    this.bindingAttributesParser = bindingAttributesParser;
	    return this;
	}

	public Builder withBindingAttributeResolver(BindingAttributeResolver bindingAttributeResolver) {
	    this.bindingAttributeResolver = bindingAttributeResolver;
	    return this;
	}

	public Builder withPredefinedPendingAttributesForView(PredefinedPendingAttributesForView predefinedPendingAttributesForView) {
	    this.predefinedPendingAttributesForViews.add(predefinedPendingAttributesForView);
	    return this;
	}

	public BindingViewInflater build() {
	    BindingViewInflater.Builder buildingViewInflaterBuilder = new BindingViewInflater.Builder(context);
	    buildingViewInflaterBuilder.setPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViews);
	    return new BindingViewInflaterForTest(buildingViewInflaterBuilder, this);
	}

    }

    private class ViewInflaterWithExtraInvocations implements ViewInflater {
	private ViewInflater forwardingViewInflator;

	public ViewInflaterWithExtraInvocations(ViewInflater viewInflator) {
	    this.forwardingViewInflator = viewInflator;
	}

	@Override
	public View inflateView(int layoutId) {
	    View view = forwardingViewInflator.inflateView(layoutId);
	    for (OnViewCreatedInvocation onViewCreatedInvocation : onViewCreatedInvocations) {
		onViewCreated(onViewCreatedInvocation.view, onViewCreatedInvocation.bindingAttributeSet);
	    }
	    return view;
	}
    }

    private static class OnViewCreatedInvocation {
	private View view;
	private AttributeSet bindingAttributeSet;

	public OnViewCreatedInvocation(View view, AttributeSet bindingAttributeSet) {
	    this.view = view;
	    this.bindingAttributeSet = bindingAttributeSet;
	}
    }

}
