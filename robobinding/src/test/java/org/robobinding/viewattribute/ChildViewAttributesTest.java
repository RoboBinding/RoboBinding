package org.robobinding.viewattribute;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.robobinding.viewattribute.ChildViewAttributesTest.AttributeGroupBindingExceptionMatcher.hasChildAttributeError;
import static org.robobinding.viewattribute.RandomValues.trueOrFalse;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.MalformedAttributeException;

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
    private ViewAttribute viewAttribute1, viewAttribute2;
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

    private void throwAttributeBindingExceptionWhenBinding(ViewAttribute viewAttribute) {
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

    @Test@Ignore
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

    static class AttributeGroupBindingExceptionMatcher extends TypeSafeMatcher<AttributeGroupBindingException> {
	public static AttributeGroupBindingExceptionMatcher hasChildAttributeError(String attributeName) {
	    return new AttributeGroupBindingExceptionMatcher(attributeName);
	}

	private final String attributeName;

	private AttributeGroupBindingExceptionMatcher(String attributeName) {
	    this.attributeName = attributeName;
	}

	@Override
	protected boolean matchesSafely(final AttributeGroupBindingException exception) {
	    for (AttributeBindingException e : exception.getChildAttributeErrors()) {
		if (e.getAttributeName().equals(attributeName))
		    return true;
	    }

	    return false;
	}

	@Override
	public void describeTo(Description description) {
	    description.appendText("Error for attribute '").appendValue(attributeName).appendText("' was not thrown.");
	}
    }

    @SuppressWarnings("serial")
    private static class ProgrammingError extends RuntimeException {
    }
}
