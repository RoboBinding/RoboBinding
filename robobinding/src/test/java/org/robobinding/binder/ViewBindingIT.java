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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.robobinding.binder.PendingAttributesForViewBuilder.aPendingAttributesForView;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.PendingAttributesForView;
import org.robobinding.binder.BindingAttributeResolver.ViewBindingAttributes;
import org.robobinding.presentationmodel.PresentationModel;
import org.robobinding.viewattribute.AttributeBindingException;

import android.app.Activity;
import android.widget.EditText;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ViewBindingIT
{
	private BindingAttributeResolver bindingAttributeResolver;
	private BindingContext bindingContext;

	@Before
	public void setUp()
	{
		bindingAttributeResolver = new BindingAttributeResolver();
		bindingContext = newBindingContext();
	}

	@Test
	public void whenBindingValidResolvedAttributes_thenShouldNotThrowException()
	{
		ViewBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("text", "${name}")
					.build());

		resolvedBindingAttributes.bindTo(bindingContext);
	}

	@Test(expected = ViewBindingException.class)
	public void whenBindingInvalidResolvedPropertyAttributes_thenThrowException()
	{
		ViewBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("text", "${nonExistentProperties}")
					.build());

		resolvedBindingAttributes.bindTo(bindingContext);
	}

	@Test(expected = ViewBindingException.class)
	public void whenBindingInvalidResolvedCommandAttributes_thenThrowException()
	{
		ViewBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("onTextChanged", "setName")
					.build());

		resolvedBindingAttributes.bindTo(bindingContext);
	}

	@Test
	public void whenBindingMultipleInvalidResolvedAttributes_thenThrowExceptionReferringToEachOne()
	{
		try
		{
			ViewBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
					aPendingAttributesForEditText()
						.withAttriubte("text", "${nonExistentProperties}")
						.withAttriubte("onTextChanged", "setName")
						.build());

			resolvedBindingAttributes.bindTo(bindingContext);
			fail("Expected exception to be thrown");
		} catch (ViewBindingException e)
		{
			assertThat(e.numErrors(), is(2));
			//assertHasAttributeError(e, "text");
			assertHasAttributeError(e, "onTextChanged");
		}
	}

	private PendingAttributesForViewBuilder aPendingAttributesForEditText()
	{
		EditText editText = new EditText(new Activity());
		return aPendingAttributesForView(editText);
	}

	private ViewBindingAttributes resolveBindingAttributes(PendingAttributesForView pendingAttributesForView)
	{
		ViewResolutionResult resolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
		resolutionResult.assertNoErrors();
		return resolutionResult.getResolvedBindingAttributes();
	}

	private void assertHasAttributeError(ViewBindingException e, String attribute)
	{
		Collection<AttributeBindingException> attributeErrors = e.getAttributeErrors();
		for(AttributeBindingException attributeError : attributeErrors)
		{
			if(attributeError.getName().equals(attribute))
			{
				return;
			}
		}

		fail("Binding error for " + attribute + " was not reported");
	}
	
	private BindingContext newBindingContext()
	{
		return new BindingContext(null, new Activity(), true, new PresentationModelForTest());
	}

	@PresentationModel
	public static class PresentationModelForTest
	{
		private String name;

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}
}
