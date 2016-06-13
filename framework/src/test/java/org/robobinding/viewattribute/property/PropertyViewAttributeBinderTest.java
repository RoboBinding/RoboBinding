package org.robobinding.viewattribute.property;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.viewattribute.ViewAttributeContractTest;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public final class PropertyViewAttributeBinderTest extends ViewAttributeContractTest<PropertyViewAttributeBinder> {
	@Mock
	BindingContext bindingContext;
	@Mock
	AbstractBindingProperty bindingProperty;
	private PropertyViewAttributeBinder viewAttributeBinder;

	public void whenBindTo_thenBindingIsPerformed() {
		viewAttributeBinder = new PropertyViewAttributeBinder(bindingProperty, null);

		viewAttributeBinder.bindTo(bindingContext);

		verify(bindingProperty).performBind(bindingContext);
	}

	@Test
	public void givenAlwaysPreInitializingView_whenBindTo_thenPreInitializeTheViewToReflectTheValueModel() {
		when(bindingProperty.isAlwaysPreInitializingView()).thenReturn(true);
		viewAttributeBinder = new PropertyViewAttributeBinder(bindingProperty, null);

		viewAttributeBinder.bindTo(bindingContext);

		verify(bindingProperty).preInitializeView(bindingContext);
	}

	@Test
	public void givenAlwaysPreInitializingView_whenPreInitializeViewAfterBindTo_thenPreInitializingViewHappensOnceOnly() {
		when(bindingProperty.isAlwaysPreInitializingView()).thenReturn(true);
		viewAttributeBinder = new PropertyViewAttributeBinder(bindingProperty, null);

		viewAttributeBinder.bindTo(bindingContext);
		viewAttributeBinder.preInitializeView(bindingContext);

		verify(bindingProperty, times(1)).preInitializeView(bindingContext);
	}

	@Override
	protected PropertyViewAttributeBinder throwsExceptionDuringPreInitializingView() {
		doThrow(new RuntimeException()).when(bindingProperty).preInitializeView(any(BindingContext.class));
		return new PropertyViewAttributeBinder(bindingProperty, null);
	}

	@Override
	protected PropertyViewAttributeBinder throwsExceptionDuringBinding() {
		doThrow(new RuntimeException()).when(bindingProperty).performBind(any(BindingContext.class));
		return new PropertyViewAttributeBinder(bindingProperty, null);
	}

}
