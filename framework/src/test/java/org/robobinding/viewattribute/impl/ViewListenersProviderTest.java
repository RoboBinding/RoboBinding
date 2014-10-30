package org.robobinding.viewattribute.impl;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.ViewListeners;
import org.robobinding.viewattribute.ViewListenersMap;
import org.robobinding.viewbinding.ViewListenersProvider;
import org.robobinding.widgetaddon.ViewAddOnAware;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewListenersProviderTest {
	@Mock
	private View view;
	@Mock
	private ViewSubclass viewSubclass;

	@Test
	public void whenInjectNonViewListenersAwareAttribute_thenSafelyIgnored() {
		ViewListenersProvider viewListenersProvider = new ViewListenersProvider(null);
		viewListenersProvider.injectIfRequired(new NonViewListenersAwareAttribute(), view);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void whenInjectViewListenersAwareAttributeAgain_thenTheSameViewListenersInstanceIsInjected() {
		ViewListenersMap viewListenersMap = mock(ViewListenersMap.class);
		when(viewListenersMap.getMostSuitable(view.getClass())).thenReturn((Class) ViewListenersForView.class);
		ViewListenersProvider viewListenersProvider = new ViewListenersProvider(viewListenersMap);

		ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
		viewListenersProvider.injectIfRequired(viewAttribute, view);
		ViewListeners viewListeners1 = viewAttribute.viewListeners;

		viewListenersProvider.injectIfRequired(viewAttribute, view);
		ViewListeners viewListeners2 = viewAttribute.viewListeners;

		assertThat(viewListeners1, sameInstance(viewListeners2));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void whenInjectTwoDifferentViewListenersAwareAttributesThroughTheSameViewSubclass_thenTheSameViewSubclassListenersInstanceIsInjectedForBoth() {
		ViewListenersMap viewListenersMap = mock(ViewListenersMap.class);
		Mockito.when(viewListenersMap.getMostSuitable(viewSubclass.getClass())).thenReturn((Class) ViewListenersSubclass.class);
		ViewListenersProvider viewListenersProvider = new ViewListenersProvider(viewListenersMap);

		ViewListenersAwareAttribute viewAttribute = new ViewListenersAwareAttribute();
		viewListenersProvider.injectIfRequired(viewAttribute, viewSubclass);

		ViewListenersSubclassAwareAttribute viewAttributeSubclass = new ViewListenersSubclassAwareAttribute();
		viewListenersProvider.injectIfRequired(viewAttributeSubclass, viewSubclass);

		assertThat(viewAttribute.viewListeners, sameInstance(viewAttributeSubclass.viewListeners));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = RuntimeException.class)
	public void whenInjectViewListenersSubclassAwareAttributeThroughIncorrectView_thenThrowExceptionWithDetailedMessage() {
		ViewListenersMap viewListenersMap = mock(ViewListenersMap.class);
		when(viewListenersMap.getMostSuitable(view.getClass())).thenReturn((Class) ViewListenersForView.class);
		ViewListenersProvider viewListenersProvider = new ViewListenersProvider(viewListenersMap);

		ViewListenersSubclassAwareAttribute viewAttributeSubclass = new ViewListenersSubclassAwareAttribute();
		viewListenersProvider.injectIfRequired(viewAttributeSubclass, view);
	}

	private static class NonViewListenersAwareAttribute {
	}

	private static class ViewListenersAwareAttribute implements ViewAddOnAware<ViewListeners> {
		public ViewListeners viewListeners;

		@Override
		public void setViewAddOn(ViewListeners viewListeners) {
			this.viewListeners = viewListeners;
		}
	}

	private static class ViewListenersSubclassAwareAttribute implements ViewAddOnAware<ViewListenersSubclass> {
		public ViewListeners viewListeners;

		@Override
		public void setViewAddOn(ViewListenersSubclass viewListeners) {
			this.viewListeners = viewListeners;
		}
	}

	public static class ViewListenersForView implements ViewListeners {
		public ViewListenersForView(Object view) {
		}
	}

	public static class ViewListenersSubclass extends ViewListenersForView {
		public ViewListenersSubclass(Object view) {
			super(view);
		}
	}

	public static class ViewSubclass extends View {
		public ViewSubclass(Context context) {
			super(context);
		}
	}

}
