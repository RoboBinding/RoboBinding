package org.robobinding.viewattribute;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.function.Function;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class ViewAttributeContractTest<T extends ViewAttribute> {
    @Test(expected = AttributeBindingException.class)
    public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow() {
	T attribute = throwsExceptionDuringPreInitializingView();

	attribute.preInitializeView(bindingContextOfThrowingExceptionDuringPreInitializingView());
    }

    protected abstract T throwsExceptionDuringPreInitializingView();

    protected BindingContext bindingContextOfThrowingExceptionDuringPreInitializingView() {
	return newDefaultBindingContext();
    }

    @SuppressWarnings("unchecked")
    private BindingContext newDefaultBindingContext() {
	BindingContext bindingContext = mock(BindingContext.class);

	when(bindingContext.getPropertyValueModel(anyString())).thenReturn(mock(ValueModel.class));
	when(bindingContext.getReadOnlyPropertyValueModel(anyString())).thenReturn(mock(ValueModel.class));
	when(bindingContext.getDataSetPropertyValueModel(anyString())).thenReturn(mock(DataSetValueModel.class));
	when(bindingContext.findFunction(anyString(), (Class<?>) any())).thenReturn(mock(Function.class));

	return bindingContext;
    }

    @Test(expected = AttributeBindingException.class)
    public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow() {

	T attribute = throwsExceptionDuringBinding();

	attribute.bindTo(bindingContextOfThrowingExceptionDuringBinding());
    }

    protected abstract T throwsExceptionDuringBinding();

    protected BindingContext bindingContextOfThrowingExceptionDuringBinding() {
	return newDefaultBindingContext();
    }
}
