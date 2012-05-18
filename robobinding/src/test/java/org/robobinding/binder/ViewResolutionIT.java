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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.robobinding.binder.PendingAttributesForViewBuilder.aPendingAttributesForView;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.AttributeResolutionException;
import org.robobinding.PendingAttributesForView;
import org.robobinding.ViewResolutionException;
import org.robobinding.binder.BindingAttributeResolver.ViewBindingAttributes;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ViewResolutionIT
{
	private BindingAttributeResolver bindingAttributeResolver;

	@Before
	public void setUp()
	{
		bindingAttributeResolver = new BindingAttributeResolver();
	}

	@Test
	public void givenASingleView_whenResolvingValidBindingAttributes_thenReturnResolvedBindingAttributes()
	{
		ViewBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("text", "${name}")
					.withAttriubte("onTextChanged", "onNameChanged")
					.build());

		assertNotNull(resolvedBindingAttributes);
	}

	@Test(expected = ViewResolutionException.class)
	public void givenASingleView_whenResolvingUnsupportedBindingAttributes_thenThrowException()
	{
		resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("tex", "${name}")
					.build());
	}

	@Test(expected = ViewResolutionException.class)
	public void givenASingleView_whenResolvingMalformedPropertyBindingAttributes_thenThrowException()
	{
		resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("text", "${name")
					.build());
	}

	@Test(expected = ViewResolutionException.class)
	public void givenASingleView_whenResolvingMalformedCommandBindingAttributes_thenThrowException()
	{
		resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttriubte("onTextChanged", "{nameChanged}")
					.build());
	}

	@Test
	public void givenASingleView_whenResolvingMissingGroupBindingAttributes_thenThrowExceptionReferencingView()
	{

		PendingAttributesForView pendingAttributesForAdapterView = aPendingAttributesForAdapterView()
			.withAttriubte("source", "{names}")
			.build();
		try
		{
			resolveBindingAttributes(pendingAttributesForAdapterView);
			fail("Expected exception to be thrown");
		} catch (ViewResolutionException e) 
		{
			assertThat(e.getView(), equalTo((View)pendingAttributesForAdapterView.getView()));
		}
	}
	
	@Test
	public void givenASingleView_whenResolvingMultipleMalformedAndUnsupportedBindingAttributes_thenThrowExceptionReferringToEachOne()
	{
		try
		{
			resolveBindingAttributes(
					aPendingAttributesForEditText()
						.withAttriubte("text", "${name")
						.withAttriubte("onTextChanged", "{nameChanged}")
						.withAttriubte("tex", "${name}")
						.build());
			fail("Expected exception to be thrown");
		} catch (ViewResolutionException e)
		{
			assertThat(e.numErrors(), is(3));
			assertHasAttributeError(e, "text");
			assertHasAttributeError(e, "onTextChanged");
			assertHasAttributeError(e, "tex");
		}
	}

	private void assertHasAttributeError(ViewResolutionException e, String attribute)
	{
		Collection<AttributeResolutionException> attributeErrors = e.getAttributeErrors();
		for(AttributeResolutionException attributeError : attributeErrors)
		{
			if(attributeError.getAttribute().equals(attribute))
			{
				return;
			}
		}
		
		fail("Resolution error for " + attribute + " was not reported");
	}

	private PendingAttributesForViewBuilder aPendingAttributesForEditText()
	{
		EditText editText = new EditText(new Activity());
		return aPendingAttributesForView(editText);
	}
	
	private PendingAttributesForViewBuilder aPendingAttributesForAdapterView()
	{
		AdapterView<?> adapterView = new ListView(new Activity());
		return aPendingAttributesForView(adapterView);
	}

	private ViewBindingAttributes resolveBindingAttributes(PendingAttributesForView pendingAttributesForView)
	{
		ViewResolutionResult resolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
		resolutionResult.assertNoErrors();
		return resolutionResult.getResolvedBindingAttributes();
	}
}
