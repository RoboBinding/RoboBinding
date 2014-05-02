package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
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

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class InternalBinderTest {
    @Mock
    private BindingViewInflater bindingViewInflater;
    @Mock
    private InflatedView inflatedView;

    private int layoutId = 0;
    
    @Test
    public void whenInflateAndBind_thenViewWithBindingShouldBeReturned() {
	View viewWithBinding = mock(View.class);
	when(inflatedView.getRootView()).thenReturn(viewWithBinding);

	when(bindingViewInflater.inflateView(layoutId)).thenReturn(inflatedView);
	
	InternalBinder binder = new InternalBinder(bindingViewInflater, bindingContextFactoryWithoutPreInitializingViews(), null);

	View view = binder.inflateAndBind(layoutId, null);

	assertThat(view, sameInstance(viewWithBinding));
    }

    private BindingContextFactory bindingContextFactoryWithoutPreInitializingViews() {
	return createBindingContextFactory(false);
    }

    private BindingContextFactory createBindingContextFactory(boolean preInitializeViews) {
	BindingContextFactory factory = mock(BindingContextFactory.class);
	BindingContext bindingContext = new BindingContext(null, null, null, preInitializeViews);
	when(factory.create(any(InternalBinder.class), anyObject())).thenReturn(bindingContext);
	return factory;
    }

    @Test
    public void whenInflateAndBind_thenChildViewsShouldBeBound() {
	when(bindingViewInflater.inflateView(layoutId)).thenReturn(inflatedView);
	
	InternalBinder binder = new InternalBinder(bindingViewInflater, bindingContextFactoryWithoutPreInitializingViews(), null);
	
	binder.inflateAndBind(layoutId, null);

	verify(inflatedView).bindChildViews(any(BindingContext.class));
    }

    @Test
    public void whenInflateAndBind_thenViewInflationErrorsShouldBeAsserted() {
	when(bindingViewInflater.inflateView(layoutId)).thenReturn(inflatedView);
	
	ErrorFormatter errorFormatter = mock(ErrorFormatter.class);
	InternalBinder binder = new InternalBinder(bindingViewInflater, bindingContextFactoryWithoutPreInitializingViews(), errorFormatter);
	
	binder.inflateAndBind(layoutId, null);

	verify(inflatedView).assertNoErrors(errorFormatter);
    }

    @Test
    public void whenInflateAndBind_thenChildViewsShouldBePreInitialized() {
	when(bindingViewInflater.inflateView(layoutId)).thenReturn(inflatedView);
	
	InternalBinder binder = new InternalBinder(bindingViewInflater, bindingContextFactoryWithPreInitializingViews(), null);
	
	binder.inflateAndBind(layoutId, null);

	verify(inflatedView).preinitializeViews(any(BindingContext.class));
    }
    
    private BindingContextFactory bindingContextFactoryWithPreInitializingViews() {
	return createBindingContextFactory(true);
    }
}
