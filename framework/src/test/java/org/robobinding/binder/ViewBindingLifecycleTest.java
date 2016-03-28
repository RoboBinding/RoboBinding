package org.robobinding.binder;

import static org.mockito.Mockito.never;
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
	private BindingContext bindingContext;

	@Test
	public void whenRunBindingLifeCycle_thenChildViewsShouldBeBound() {
		ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(errorFormatter);
		lifecycle.run(inflatedView, bindingContext);

		verify(inflatedView).bindChildViews(bindingContext);
	}

	@Test
	public void whenRunBindingLifeCycle_thenViewInflationErrorsShouldBeAsserted() {
		ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(errorFormatter);
		lifecycle.run(inflatedView, bindingContext);

		verify(inflatedView).assertNoErrors(errorFormatter);
	}

	@Test
	public void whenInflateAndBindWithPreInitalizingViews_thenChildViewsShouldBePreInitialized() {
		when(bindingContext.shouldPreInitializeViews()).thenReturn(true);
		
		ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(errorFormatter);
		lifecycle.run(inflatedView, bindingContext);

		verify(inflatedView).preinitializeViews(bindingContext);
	}

	@Test
	public void whenInflateAndBindWithoutPreInitalizingViews_thenChildViewsShouldBePreInitialized() {
		when(bindingContext.shouldPreInitializeViews()).thenReturn(false);
		
		ViewBindingLifecycle lifecycle = new ViewBindingLifecycle(errorFormatter);
		lifecycle.run(inflatedView, bindingContext);

		verify(inflatedView, never()).preinitializeViews(bindingContext);
	}
}
