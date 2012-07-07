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
package org.robobinding.integrationtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.robobinding.binder.MockBindingAttributeSetBuilder.aBindingAttributeSet;
import static org.robobinding.binder.BindingViewInflaterForTest.aBindingViewInflater;
import static org.robobinding.integrationtest.BindingViewInflationErrorExpectation.aBindingViewInflationErrorExpectationOf;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.R;
import org.robobinding.binder.BinderImplementorForTest;
import org.robobinding.binder.BindingViewInflaterForTest;
import org.robobinding.binder.BindingViewInflationError;
import org.robobinding.binder.BindingViewInflationErrorsException;
import org.robobinding.presentationmodel.PresentationModel;

import android.app.Activity;
import android.content.Context;
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
	@Test
	public void test()
	{
		Sample1 sample1 = new Sample1();
		try
		{
			sample1.inflateAndBind();
			fail("Expect an exception thrown");
		}catch(BindingViewInflationErrorsException bindingViewInflationErrors)
		{
			Collection<BindingViewInflationError> errors = bindingViewInflationErrors.getErrors();
			assertThat(errors.size(), is(2));
			
			BindingViewInflationErrorExpectation errorExpectationOfView1 = aBindingViewInflationErrorExpectationOf(sample1.getView(0))
						.withAttributeResolutionErrorOf("text")
						.withAttributeResolutionErrorOf("nonExistentAttribute")
						.withAttributeBindingErrorOf("visibility")
						.build();
			errorExpectationOfView1.meet((BindingViewInflationError)CollectionUtils.get(errors, 0));
			
			BindingViewInflationErrorExpectation errorExpectationOfView2 = aBindingViewInflationErrorExpectationOf(sample1.getView(1))
						.withAttributeResolutionErrorOf("visibility")
						.withMissingRequiredAttributesResolutionErrorOf("source", "dropdownLayout")
						.withAttributeBindingErrorOf("itemLayout")
						.build();
			errorExpectationOfView2.meet((BindingViewInflationError)CollectionUtils.get(errors, 1));
		}
	}

	private static class Sample1
	{
		private List<View> views;
		private Context context;
		
		public Sample1()
		{
			context = new Activity();
		}
		
		public View inflateAndBind()
		{
			views = Lists.newArrayList();
			
			BindingViewInflaterForTest.Builder bindingViewInflaterBuilder = aBindingViewInflater(context);

			Button button = new Button(context);
			views.add(button);
			bindingViewInflaterBuilder.withOnViewCreatedInvocation(
					button, 
					aBindingAttributeSet()
						//resolution phase.
						.withAttribute("text", "{invalidSyntax")
						.withAttribute("nonExistentAttribute", "{property}")
						//binding phase.
						.withAttribute("visibility", "{nonExistentProperty}")
						.build());
			
			Spinner spinner = new Spinner(context);
			views.add(spinner);
			bindingViewInflaterBuilder.withOnViewCreatedInvocation(
					spinner, 
					aBindingAttributeSet()
						//resolution phase.
						.withAttribute("visibility", "${invalidSyntax")
						//binding phase.
						.withAttribute("itemLayout", "{propertyPointingToNoExistentLayout}")
						.build());
			
			
			BinderImplementorForTest binder = new BinderImplementorForTest(context, bindingViewInflaterBuilder.build());
			return binder.inflateAndBind(R.layout.framework_error_reporting_it_sample1, new PresentationModelForTest());
		}
		
		public View getView(int index)
		{
			return views.get(index);
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
