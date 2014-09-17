package org.robobinding.viewattribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class ViewAttributeContractTest<T extends ViewAttributeBinder> {
	@Mock
	BindingContext bindingContext;

	@Test(expected = AttributeBindingException.class)
	public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrowAsBindingException() {
		T attribute = throwsExceptionDuringPreInitializingView();

		attribute.preInitializeView(bindingContext);
	}

	protected abstract T throwsExceptionDuringPreInitializingView();

	@Test(expected = AttributeBindingException.class)
	public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrowAsBindingException() {

		T attribute = throwsExceptionDuringBinding();

		attribute.bindTo(bindingContext);
	}

	protected abstract T throwsExceptionDuringBinding();
}
