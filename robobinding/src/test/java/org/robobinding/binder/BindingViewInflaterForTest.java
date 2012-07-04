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

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingViewInflaterForTest extends BindingViewInflater
{
	private List<OnViewCreatedInvocation> onViewCreatedInvocations;

	public BindingViewInflaterForTest(Context context, List<OnViewCreatedInvocation> onViewCreatedInvocations)
	{
		super(new BindingViewInflater.Builder(context));
		this.onViewCreatedInvocations = onViewCreatedInvocations;
	}
	
	@Override
	ViewInflator createViewInflator(Builder builder)
	{
		return new ViewInflaterWithExtraInvocations(super.createViewInflator(builder));
	}
	
	private class ViewInflaterWithExtraInvocations extends ViewInflator
	{
		private ViewInflator viewInflator;
		public ViewInflaterWithExtraInvocations(ViewInflator viewInflator)
		{
			super((LayoutInflater)null, null);
			this.viewInflator = viewInflator;
		}
		
		@Override
		public View inflateView(int layoutId)
		{
			View view = viewInflator.inflateView(layoutId);
			for(OnViewCreatedInvocation onViewCreatedInvocation : onViewCreatedInvocations)
			{
				onViewCreated(onViewCreatedInvocation.view, onViewCreatedInvocation.bindingAttributeSet);
			}
			return view;
		}
	}
	
	static class OnViewCreatedInvocation
	{
		private View view;
		private AttributeSet bindingAttributeSet;
		public OnViewCreatedInvocation(View view, AttributeSet bindingAttributeSet)
		{
			this.view = view;
			this.bindingAttributeSet = bindingAttributeSet;
		}
	}
	
}
