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

import static org.robobinding.binder.MockBindingAttributeSetBuilder.aBindingAttributeSet;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.R;
import org.robobinding.binder.BindingViewInflaterForTest.OnViewCreatedInvocation;
import org.robobinding.presentationmodel.PresentationModel;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class FrameworkErrorReportingIT
{
	private Context context = new Activity();
	
	@Test(expected=BindingViewInflationErrors.class)
	public void test()
	{
		inflateAndBindSample1();
	}
	
	private View inflateAndBindSample1()
	{
		BinderImplementorForTest binder = new BinderImplementorForTest();
		binder.addOnViewCreatedInvocation(
				new Button(context), 
				aBindingAttributeSet()
					//resolution phase.
					.withAttribute("text", "{invalidSyntax")
					.withAttribute("nonExistentAttribute", "{property}")
					//binding phase.
					.withAttribute("visibility", "{nonExistentProperty}")
					.build());
		
		binder.addOnViewCreatedInvocation(
				new Spinner(context), 
				aBindingAttributeSet()
					//resolution phase.
					.withAttribute("visibility", "${invalidSyntax")
					//binding phase.
					.withAttribute("itemLayout", "{propertyPointingToNoExistentLayout}")
					.build());
		
		return binder.inflateAndBind(R.layout.framework_error_reporting_it_sample1, new PresentationModelForTest());
	}
	
	private class BinderImplementorForTest extends BinderImplementorImpl
	{
		private List<OnViewCreatedInvocation> onViewCreatedInvocations;
		public BinderImplementorForTest()
		{
			super(context, new BindingContextCreator() {
				
				@Override
				public BindingContext create(Object presentationModel)
				{
					return new BindingContext(null, context, false, presentationModel);
				}
			});
			onViewCreatedInvocations = Lists.newArrayList();
			errorFormatter = new BindingViewInflationErrors.ErrorFormatter() {
				
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
	
	
	@PresentationModel
	static class PresentationModelForTest
	{
		private boolean property;

		public boolean isProperty()
		{
			return property;
		}

		public void setProperty(boolean property)
		{
			this.property = property;
		}
	}
}
