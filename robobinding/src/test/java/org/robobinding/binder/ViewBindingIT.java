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
import static org.mockito.Mockito.mock;
import static org.robobinding.binder.PendingAttributesForViewBuilder.aPendingAttributesForView;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.PendingAttributesForView;
import org.robobinding.customview.BindableView;
import org.robobinding.customview.CustomBindingAttributeMappings;
import org.robobinding.presentationmodel.PresentationModel;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AttributeBindingException;

import android.app.Activity;
import android.content.Context;
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
		ResolvedBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttribute("text", "${name}")
					.build());

		resolvedBindingAttributes.bindTo(bindingContext);
	}

	@Test(expected = ViewBindingException.class)
	public void whenBindingInvalidResolvedPropertyAttributes_thenThrowException()
	{
		ResolvedBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttribute("text", "${nonExistentProperties}")
					.build());

		resolvedBindingAttributes.bindTo(bindingContext);
	}

	@Test(expected = ViewBindingException.class)
	public void whenBindingInvalidResolvedCommandAttributes_thenThrowException()
	{
		ResolvedBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
					.withAttribute("onTextChanged", "setName")
					.build());

		resolvedBindingAttributes.bindTo(bindingContext);
	}

	@Test
	public void whenBindingMultipleInvalidResolvedAttributes_thenThrowExceptionReferringToEachOne()
	{
		ResolvedBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForEditText()
				.withAttribute("visibility", "{nonExistentProperty}")
				.withAttribute("onTextChanged", "setName")
				.build());
		
		try
		{
			resolvedBindingAttributes.bindTo(bindingContext);
			fail("Expected exception to be thrown");
		} catch (ViewBindingException e)
		{
			assertHasAttributeError(e, "visibility");
			assertHasAttributeError(e, "onTextChanged");
			assertThat(e.numErrors(), is(2));
		}
	}
	
	@Test
	public void whenBindingMultipleInvalidResolvedGroupChildAttributes_thenThrowExceptionReferringToEachOne()
	{
		ResolvedBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForAdapterView()
				.withAttribute("source", "{nonExistentProperty}")
				.withAttribute("itemLayout", "{nonExistentProperty}")
				.build());
		
		try
		{
			resolvedBindingAttributes.bindTo(bindingContext);
			fail("Expected exception to be thrown");
		} catch (ViewBindingException e)
		{
			assertHasAttributeError(e, "source");
			assertHasAttributeError(e, "itemLayout");
			assertThat(e.numErrors(), is(2));
		}
	}

	@Test (expected = ProgrammingError.class)
	public void whenAnUnexpectedExceptionIsThrownDuringBinding_thenErrorShouldNotBeSuppressed()
	{
		ResolvedBindingAttributes resolvedBindingAttributes = resolveBindingAttributes(
				aPendingAttributesForBuggyCustomView()
				.withAttribute(BuggyCustomView.BUGGY_ATTRIBUTE, "{name}")
				.build());
		
		resolvedBindingAttributes.bindTo(bindingContext);
	}
	
	@Test (expected = ProgrammingError.class)
	public void whenAnUnexpectedExceptionIsThrownDuringGroupChildAttributeBinding_thenErrorShouldNotBeSuppressed()
	{
		fail();
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
	
	private PendingAttributesForViewBuilder aPendingAttributesForBuggyCustomView()
	{
		BuggyCustomView buggyCustomView = new BuggyCustomView(new Activity());
		return aPendingAttributesForView(buggyCustomView);
	}
	
	private ResolvedBindingAttributes resolveBindingAttributes(PendingAttributesForView pendingAttributesForView)
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
		return new BindingContext(mock(BinderImplementorFactoryImpl.class), new Activity(), true, new PresentationModelForTest());
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
	
	private static class BuggyCustomView extends View implements BindableView<BuggyCustomView> {

		static final String BUGGY_ATTRIBUTE = "buggyAttribute";

		public BuggyCustomView(Context context)
		{
			super(context);
		}

		@Override
		public void mapBindingAttributes(CustomBindingAttributeMappings<BuggyCustomView> mappings)
		{
			mappings.mapPropertyAttribute(BuggyPropertyAttribute.class, BUGGY_ATTRIBUTE);
		}
	}
	
	public static class BuggyPropertyAttribute extends AbstractPropertyViewAttribute<BuggyCustomView, String>
	{
		@Override
		public void bindTo(BindingContext bindingContext)
		{
			throw new ProgrammingError();
		}
		
		@Override
		protected void valueModelUpdated(String newValue)
		{
		}

		@Override
		protected void observeChangesOnTheView(ValueModel<String> valueModel)
		{
		}
	}
	
	@SuppressWarnings("serial")
	public static class ProgrammingError extends RuntimeException
	{
	}
}