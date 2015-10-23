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
import org.robobinding.ViewResolutionErrorsException;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.content.Context;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ResolutionTest {
	private BindingAttributeResolver bindingAttributeResolver;
	private Context context = RuntimeEnvironment.application;

	@Before
	public void setUp() {
		bindingAttributeResolver = new BindingAttributeResolverBuilder().build();
	}

	@Test
	public void givenASingleView_whenResolvingValidBindingAttributes_thenReturnResolvedBindingAttributes() {
		ResolvedBindingAttributesForView resolvedBindingAttributes = resolveBindingAttributes(aPendingAttributesForEditText().withAttribute("text", "${name}")
				.withAttribute("onTextChanged", "onNameChanged").build());

		assertNotNull(resolvedBindingAttributes);
	}

	@Test(expected = ViewResolutionErrorsException.class)
	public void givenASingleView_whenResolvingUnsupportedBindingAttributes_thenThrowException() {
		resolveBindingAttributes(aPendingAttributesForEditText().withAttribute("tex", "${name}").build());
	}

	@Test(expected = ViewResolutionErrorsException.class)
	public void givenASingleView_whenResolvingMalformedPropertyBindingAttributes_thenThrowException() {
		resolveBindingAttributes(aPendingAttributesForEditText().withAttribute("text", "${name").build());
	}

	@Test(expected = ViewResolutionErrorsException.class)
	public void givenASingleView_whenResolvingMalformedCommandBindingAttributes_thenThrowException() {
		resolveBindingAttributes(aPendingAttributesForEditText().withAttribute("onTextChanged", "{nameChanged}").build());
	}

	@Test
	public void givenASingleView_whenResolvingMissingGroupBindingAttributes_thenThrowExceptionReferencingView() {
		PendingAttributesForView pendingAttributesForAdapterView = aPendingAttributesForAdapterView().withAttribute("source", "{names}").build();
		try {
			resolveBindingAttributes(pendingAttributesForAdapterView);
			fail("Expected exception to be thrown");
		} catch (ViewResolutionErrorsException e) {
			assertThat(e.getView(), equalTo(pendingAttributesForAdapterView.getView()));
			assertThat(e.getMissingRequiredAttributeErrors().size(), is(1));
		}
	}

	@Test
	public void givenASingleView_whenResolvingMultipleMalformedAndUnsupportedBindingAttributes_thenThrowExceptionReferringToEachOne() {
		try {
			resolveBindingAttributes(aPendingAttributesForEditText().withAttribute("text", "${name").withAttribute("onTextChanged", "{nameChanged}")
					.withAttribute("tex", "${name}").build());
			fail("Expected exception to be thrown");
		} catch (ViewResolutionErrorsException e) {
			assertHasAttributeError(e, "text");
			assertHasAttributeError(e, "onTextChanged");
			assertHasAttributeError(e, "tex");
			assertThat(e.numErrors(), is(3));
		}
	}

	@Test
	public void givenASingleView_whenResolvingMultipleMalformedAndUnsupportedBindingAttributesFromTheSameAttributeGroup_thenThrowExceptionReferringToEachOne() {
		try {
			resolveBindingAttributes(aPendingAttributesForAdapterView().withAttribute("itemLayout", "invalid").withAttribute("source", "{invalid").build());
			fail("Expected exception to be thrown");
		} catch (ViewResolutionErrorsException e) {
			assertHasAttributeError(e, "itemLayout");
			assertHasAttributeError(e, "source");
			assertThat(e.numErrors(), is(2));
		}
	}

	@Test
	public void givenAViewWithSubViewAttributes_whenResolvingMultipleMalformedBindingAttributes_thenThrowExceptionReferringToEachOne() {
		try {
			resolveBindingAttributes(aPendingAttributesForAdapterView().withAttribute("itemLayout", "@layout/some_resource")
					.withAttribute("source", "{dateSource}").withAttribute("footerLayout", "invalid").withAttribute("footerVisibility", "{invalid")
					.withAttribute("footerPresentationModel", "{invalid").build());
			fail("Expected exception to be thrown");
		} catch (ViewResolutionErrorsException e) {
			assertHasAttributeError(e, "footerLayout");
			assertHasAttributeError(e, "footerVisibility");
			assertHasAttributeError(e, "footerPresentationModel");
			assertThat(e.numErrors(), is(3));
		}
	}

	private void assertHasAttributeError(ViewResolutionErrorsException e, String attribute) {
		Collection<AttributeResolutionException> attributeErrors = e.getAttributeErrors();
		for (AttributeResolutionException attributeError : attributeErrors) {
			if (attributeError.getAttributeName().equals(attribute)) {
				return;
			}
		}

		fail("Resolution error for " + attribute + " was not reported");
	}

	private PendingAttributesForViewBuilder aPendingAttributesForEditText() {
		EditText editText = new EditText(context);
		return aPendingAttributesForView(editText);
	}

	private PendingAttributesForViewBuilder aPendingAttributesForAdapterView() {
		AdapterView<?> adapterView = new ListView(context);
		return aPendingAttributesForView(adapterView);
	}

	private ResolvedBindingAttributesForView resolveBindingAttributes(PendingAttributesForView pendingAttributesForView) {
		ViewResolutionResult resolutionResult = bindingAttributeResolver.resolve(pendingAttributesForView);
		resolutionResult.assertNoErrors();
		return resolutionResult.getResolvedBindingAttributes();
	}
}
