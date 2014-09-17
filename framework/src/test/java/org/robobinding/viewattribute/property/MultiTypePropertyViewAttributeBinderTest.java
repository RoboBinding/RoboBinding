package org.robobinding.viewattribute.property;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.ViewAttributeContractTest;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeBinderTest extends ViewAttributeContractTest<MultiTypePropertyViewAttributeBinder<View>> {
	@Mock
	PropertyViewAttributeBinderProvider<View> viewAttributeBinderProvider;
	@Mock
	ValueModelAttribute attribute;
	@Mock
	PropertyViewAttributeBinder<View, Object> viewAttributeBinder;
	@Mock
	BindingContext bindingContext;

	@Test
	public void givenAMatchingViewAttributeBinder_whenBind_thenTheViewAttributeBinderIsBoundToContext() {

		Mockito.<PropertyViewAttributeBinder<View, ?>> when(viewAttributeBinderProvider.create(any(Class.class))).thenReturn(viewAttributeBinder);
		MultiTypePropertyViewAttributeBinder<View> multiTypeViewAttributeBinder = new MultiTypePropertyViewAttributeBinder<View>(viewAttributeBinderProvider,
				attribute);

		multiTypeViewAttributeBinder.bindTo(bindingContext);

		verify(viewAttributeBinder).bindTo(bindingContext);
	}

	@Test(expected = AttributeBindingException.class)
	public void givenNoMatchingViewAttributeBinder_whenBind_thenExceptionIsThrown() {
		Mockito.<PropertyViewAttributeBinder<View, ?>> when(viewAttributeBinderProvider.create(any(Class.class))).thenReturn(null);
		MultiTypePropertyViewAttributeBinder<View> multiTypeViewAttributeBinder = new MultiTypePropertyViewAttributeBinder<View>(viewAttributeBinderProvider,
				attribute);

		multiTypeViewAttributeBinder.bindTo(bindingContext);
	}

	@Override
	protected MultiTypePropertyViewAttributeBinder<View> throwsExceptionDuringPreInitializingView() {
		doThrow(new RuntimeException()).when(viewAttributeBinder).preInitializeView(any(BindingContext.class));
		Mockito.<PropertyViewAttributeBinder<View, ?>> when(viewAttributeBinderProvider.create(any(Class.class))).thenReturn(viewAttributeBinder);

		MultiTypePropertyViewAttributeBinder<View> multiTypeViewAttributeBinder = new MultiTypePropertyViewAttributeBinder<View>(viewAttributeBinderProvider,
				attribute);
		return multiTypeViewAttributeBinder;
	}

	@Override
	protected MultiTypePropertyViewAttributeBinder<View> throwsExceptionDuringBinding() {
		doThrow(new RuntimeException()).when(viewAttributeBinder).bindTo(any(BindingContext.class));
		Mockito.<PropertyViewAttributeBinder<View, ?>> when(viewAttributeBinderProvider.create(any(Class.class))).thenReturn(viewAttributeBinder);

		MultiTypePropertyViewAttributeBinder<View> multiTypeViewAttributeBinder = new MultiTypePropertyViewAttributeBinder<View>(viewAttributeBinderProvider,
				attribute);
		return multiTypeViewAttributeBinder;
	}
}
