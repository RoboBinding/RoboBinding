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

import java.util.Collection;
import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.binder.BindingViewInflaterForTest.OnViewCreatedInvocation;

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
public class BinderImplementorForTest extends BinderImplementorImpl
{
	private List<OnViewCreatedInvocation> onViewCreatedInvocations;
	private Context context;
	public BinderImplementorForTest(final Context context)
	{
		super(context, new BindingContextCreator() {
			
			@Override
			public BindingContext create(Object presentationModel)
			{
				return new BindingContext(null, context, false, presentationModel);
			}
		});
		this.context = context;
		onViewCreatedInvocations = Lists.newArrayList();
		errorFormatter = new BindingViewInflationErrorsException.ErrorFormatter() {
			
			@Override
			public String format(BindingViewInflationError error)
			{
				return error.toString();
			}
		};
	}
	
	public void addOnViewCreatedInvocation(View view, AttributeSet bindingAttributeSet)
	{
		onViewCreatedInvocations.add(new OnViewCreatedInvocation(view, bindingAttributeSet));
	}
	
	@Override
	BindingViewInflater createBindingViewInflater(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
	{
		BindingViewInflater viewInflater = new BindingViewInflaterForTest(context, onViewCreatedInvocations);
		onViewCreatedInvocations = Lists.newArrayList();
		return viewInflater;
	}
	
}