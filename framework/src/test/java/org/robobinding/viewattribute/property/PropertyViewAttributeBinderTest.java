package org.robobinding.viewattribute.property;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.ViewAttributeContractTest;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public final class PropertyViewAttributeBinderTest extends ViewAttributeContractTest<PropertyViewAttributeBinder<View, Object>> {
    @Mock
    BindingContext bindingContext;
    @Mock
    AbstractBindingProperty<View, Integer> bindingProperty;
    private PropertyViewAttributeBinder<View, Integer> viewAttributeBinder;

    public void whenBindTo_thenBindingIsPerformed() {
	viewAttributeBinder = new PropertyViewAttributeBinder<View, Integer>(bindingProperty, false);

	viewAttributeBinder.bindTo(bindingContext);

	verify(bindingProperty).performBind(bindingContext);
    }
    @Test
    public void givenAlwaysPreInitializingView_whenBindTo_thenPreInitializeTheViewToReflectTheValueModel() {
	viewAttributeBinder = new PropertyViewAttributeBinder<View, Integer>(bindingProperty, true);

	viewAttributeBinder.bindTo(bindingContext);

	verify(bindingProperty).preInitializeView(bindingContext);
    }

    @Test
    public void givenAlwaysPreInitializingView_whenPreInitializeViewAfterBindTo_thenPreInitializingViewHappensOnceOnly() {
	viewAttributeBinder = new PropertyViewAttributeBinder<View, Integer>(bindingProperty, true);

	viewAttributeBinder.bindTo(bindingContext);
	viewAttributeBinder.preInitializeView(bindingContext);

	verify(bindingProperty, times(1)).preInitializeView(bindingContext);
    }

    @Override
    protected PropertyViewAttributeBinder<View, Object> throwsExceptionDuringPreInitializingView() {
	@SuppressWarnings("unchecked")
	AbstractBindingProperty<View, Object> bindingProperty = mock(AbstractBindingProperty.class);
	doThrow(new RuntimeException()).when(bindingProperty).preInitializeView(any(PresentationModelAdapter.class));
	PropertyViewAttributeBinder<View, Object> viewAttributeBinder = new PropertyViewAttributeBinder<View, Object>(bindingProperty, false);
	return viewAttributeBinder;
    }

    @Override
    protected PropertyViewAttributeBinder<View, Object> throwsExceptionDuringBinding() {
	@SuppressWarnings("unchecked")
	AbstractBindingProperty<View, Object> bindingProperty = mock(AbstractBindingProperty.class);
	doThrow(new RuntimeException()).when(bindingProperty).performBind(any(PresentationModelAdapter.class));
	PropertyViewAttributeBinder<View, Object> viewAttributeBinder = new PropertyViewAttributeBinder<View, Object>(bindingProperty, false);
	return viewAttributeBinder;
    }

}
