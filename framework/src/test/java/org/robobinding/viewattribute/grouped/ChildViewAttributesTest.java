package org.robobinding.viewattribute.grouped;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.robobinding.util.RandomValues.trueOrFalse;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.MalformedAttributeException;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.Bindable;
import org.robobinding.viewattribute.ViewAttributeBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ChildViewAttributesTest {
	@Mock
	private BindingContext bindingContext;
	@Mock
	private ViewAttributeBinder viewAttribute1, viewAttribute2;
	private String viewAttributeName1 = "viewAttribute1";
	private String viewAttributeName2 = "viewAttribute2";
	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	@Test
	public void whenBindTo_thenTheMethodInEachChildViewAttributeIsCalled() {
		ChildViewAttributes childViewAttributes = childViewAttributes();
		childViewAttributes.bindTo(bindingContext);

		verify(viewAttribute1).bindTo(bindingContext);
		verify(viewAttribute2).bindTo(bindingContext);
	}

	private ChildViewAttributes childViewAttributes() {
		return new ChildViewAttributes(childAttributeMap(), trueOrFalse());
	}

	private Map<String, Bindable> childAttributeMap() {
		Map<String, Bindable> childAttributeMap = newLinkedHashMap();
		childAttributeMap.put(viewAttributeName1, viewAttribute1);
		childAttributeMap.put(viewAttributeName2, viewAttribute2);
		return childAttributeMap;
	}

	@Test
	public void whenPreInitializeView_thenTheMethodOfChildPropertyViewAttributeIsCalled() {
		ChildViewAttributes childViewAttributes = childViewAttributes();
		childViewAttributes.preInitializeView(bindingContext);

		verify(viewAttribute1).preInitializeView(bindingContext);
		verify(viewAttribute2).preInitializeView(bindingContext);
	}

	@Test
	public void whenErrorsOccurDuringBindingWithReportAllErrors_thenAllErrorsAreReported() {
		throwAttributeBindingExceptionWhenBinding(viewAttribute1);
		throwAttributeBindingExceptionWhenBinding(viewAttribute2);

		thrownException.expect(AttributeGroupBindingException.class);
		thrownException.expect(hasChildAttributeError(viewAttributeName1));
		thrownException.expect(hasChildAttributeError(viewAttributeName2));

		ChildViewAttributes childViewAttributes = childViewAttributesWithReportAllErrors();
		childViewAttributes.bindTo(bindingContext);
	}

	public static AttributeGroupBindingExceptionMatcher hasChildAttributeError(String attributeName) {
		return new AttributeGroupBindingExceptionMatcher(attributeName);
	}

	private void throwAttributeBindingExceptionWhenBinding(ViewAttributeBinder viewAttribute) {
		doThrow(new AttributeBindingException("", new MalformedAttributeException("", ""))).when(viewAttribute).bindTo(bindingContext);
	}

	private ChildViewAttributes childViewAttributesWithReportAllErrors() {
		return new ChildViewAttributes(childAttributeMap(), false);
	}

	@Test
	public void whenErrorsOccurDuringBindingWithFailOnFirstBindingError_thenOnlyTheFirstErrorIsReported() {
		throwAttributeBindingExceptionWhenBinding(viewAttribute1);
		throwAttributeBindingExceptionWhenBinding(viewAttribute2);

		thrownException.expect(AttributeGroupBindingException.class);
		thrownException.expect(hasChildAttributeError(viewAttributeName1));
		thrownException.expect(not(hasChildAttributeError(viewAttributeName2)));

		ChildViewAttributes childViewAttributes = childViewAttributesWithFailOnFirstError();
		childViewAttributes.bindTo(bindingContext);
	}

	private ChildViewAttributes childViewAttributesWithFailOnFirstError() {
		return new ChildViewAttributes(childAttributeMap(), true);
	}

	@Test
	@Ignore
	public void whenAProgrammingErrorOccursDuringBinding_thenRethrow() {
		doThrow(new ProgrammingError()).when(viewAttribute1).bindTo(bindingContext);

		thrownException.expect(ProgrammingError.class);

		ChildViewAttributes childViewAttributes = childViewAttributes();
		childViewAttributes.bindTo(bindingContext);
	}

	@Test(expected = AttributeGroupBindingException.class)
	public void whenErrorsOccurDuringBindingWithFailOnFirstBindingError_thenOnlyTheMethodInTheFirstChildViewAttributeIsCalled() {
		throwAttributeBindingExceptionWhenBinding(viewAttribute1);

		ChildViewAttributes childViewAttributes = childViewAttributesWithFailOnFirstError();
		childViewAttributes.bindTo(bindingContext);

		verify(viewAttribute1).bindTo(bindingContext);
		verify(viewAttribute2, never()).bindTo(bindingContext);
	}

	@Test(expected = AttributeGroupBindingException.class)
	public void whenErrorsOccurDuringBindingWithReportAllErrors_thenAllTheMethodInEachChildViewAttributeIsCalled() {
		throwAttributeBindingExceptionWhenBinding(viewAttribute1);

		ChildViewAttributes childViewAttributes = childViewAttributesWithReportAllErrors();
		childViewAttributes.bindTo(bindingContext);

		verify(viewAttribute1).bindTo(bindingContext);
		verify(viewAttribute2).bindTo(bindingContext);
	}

	@SuppressWarnings("serial")
	private static class ProgrammingError extends RuntimeException {
	}
}
