package org.robobinding.binder;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewBindingLifecycleTest {
    @Mock
    private ErrorFormatter errorFormatter;
    @Mock
    private InflatedView inflatedView;
    @Mock
    private Object presentationModel;

    @Test
    public void whenRunBindingLifeCycle_thenChildViewsShouldBeBound() {
	ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(bindingContextFactoryWithoutPreInitializingViews(), errorFormatter);
	lifecycle.run(inflatedView, presentationModel);

	verify(inflatedView).bindChildViews(any(BindingContext.class));
    }

    private BindingContextFactory bindingContextFactoryWithoutPreInitializingViews() {
        return createBindingContextFactory(false);
    }

    private BindingContextFactory createBindingContextFactory(boolean preInitializeViews) {
        BindingContextFactory factory = mock(BindingContextFactory.class);
        BindingContext bindingContext = new BindingContext(null, null, null, preInitializeViews);
        when(factory.create(anyObject())).thenReturn(bindingContext);
        return factory;
    }

    @Test
    public void whenRunBindingLifeCycle_thenViewInflationErrorsShouldBeAsserted() {
	ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(bindingContextFactoryWithoutPreInitializingViews(), errorFormatter);
	lifecycle.run(inflatedView, presentationModel);

	verify(inflatedView).assertNoErrors(errorFormatter);
    }

    @Test
    public void whenInflateAndBind_thenChildViewsShouldBePreInitialized() {
	ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(bindingContextFactoryWithPreInitializingViews(), errorFormatter);
	lifecycle.run(inflatedView, presentationModel);

	verify(inflatedView).preinitializeViews(any(BindingContext.class));
    }
    
    private BindingContextFactory bindingContextFactoryWithPreInitializingViews() {
	return createBindingContextFactory(true);
    }
}
