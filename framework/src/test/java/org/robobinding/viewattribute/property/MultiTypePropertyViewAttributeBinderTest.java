package org.robobinding.viewattribute.property;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeContractTest;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeBinderTest extends ViewAttributeContractTest<MultiTypePropertyViewAttributeBinder> {
	@Mock
	PropertyViewAttributeBinderProvider viewAttributeBinderProvider;
	@Mock
	ValueModelAttribute attribute;
	@Mock
	PropertyViewAttributeBinder viewAttributeBinder;
	@Mock
	BindingContext bindingContext;

	@Test
	public void givenAMatchingViewAttributeBinder_whenBind_thenTheViewAttributeBinderIsBoundToContext() {

		when(viewAttributeBinderProvider.create(any(Class.class))).thenReturn(viewAttributeBinder);
		MultiTypePropertyViewAttributeBinder multiTypeViewAttributeBinder = new MultiTypePropertyViewAttributeBinder(
				viewAttributeBinderProvider, attribute);

		multiTypeViewAttributeBinder.bindTo(bindingContext);

		verify(viewAttributeBinder).bindTo(bindingContext);
	}

	@Test(expected = AttributeBindingException.class)
	public void givenNoMatchingViewAttributeBinder_whenBind_thenExceptionIsThrown() {
		when(viewAttributeBinderProvider.create(any(Class.class))).thenReturn(null);
		MultiTypePropertyViewAttributeBinder multiTypeViewAttributeBinder = new MultiTypePropertyViewAttributeBinder(
				viewAttributeBinderProvider, attribute);

		multiTypeViewAttributeBinder.bindTo(bindingContext);
	}
	

	@Test@Ignore("It always delegates to PropertyViewAttributeBinder, which wraps exception as BindingException")
	@Override
	public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrowAsBindingException() {
	}

	@Override
	protected MultiTypePropertyViewAttributeBinder throwsExceptionDuringPreInitializingView() {
		throw new UnsupportedOperationException();
	}
	
	@Test@Ignore("It always delegates to PropertyViewAttributeBinder, which wraps exception as BindingException")
	@Override
	public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrowAsBindingException() {
	}

	@Override
	protected MultiTypePropertyViewAttributeBinder throwsExceptionDuringBinding() {
		throw new UnsupportedOperationException();
	}
}
