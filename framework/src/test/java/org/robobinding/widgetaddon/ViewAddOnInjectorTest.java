package org.robobinding.widgetaddon;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewAddOnInjectorTest {
	@Mock
	private View view;

	@Test
	public void whenInjectNonViewListenersAwareAttribute_thenSafelyIgnored() {
		ViewAddOnInjector injector = new ViewAddOnInjector(null);
		injector.injectIfRequired(new NonViewListenersAwareAttribute(), view);
	}

	@Test
	public void whenInjectViewAddOnAwareAttribute_thenViewAddOnIsInjected() {
		ViewAddOns viewAddOns = mock(ViewAddOns.class);
		ViewAddOnForView viewAddOn = mock(ViewAddOnForView.class);
		when(viewAddOns.getMostSuitable(view)).thenReturn(viewAddOn);
		ViewAddOnInjector injector = new ViewAddOnInjector(viewAddOns);

		ViewAddOnAwareAttribute viewAttribute = new ViewAddOnAwareAttribute();
		injector.injectIfRequired(viewAttribute, view);

		assertThat(viewAttribute.viewAddOn, sameInstance(viewAddOn));
	}

	@Test(expected = RuntimeException.class)
	public void whenIncompatibleViewAddOnIsFoundAndInject_thenThrowExceptionWithDetailedMessage() {
		ViewAddOns viewAddOns = mock(ViewAddOns.class);
		ViewAddOnForView viewAddOn = mock(ViewAddOnForView.class);
		when(viewAddOns.getMostSuitable(view)).thenReturn(viewAddOn);
		ViewAddOnInjector injector = new ViewAddOnInjector(viewAddOns);

		ViewAddOnSubclassAwareAttribute viewAttributeSubclass = new ViewAddOnSubclassAwareAttribute();
		injector.injectIfRequired(viewAttributeSubclass, view);
	}

	private static class NonViewListenersAwareAttribute {
	}

	private static class ViewAddOnAwareAttribute implements ViewAddOnAware<ViewAddOnForView> {
		public ViewAddOnForView viewAddOn;

		@Override
		public void setViewAddOn(ViewAddOnForView viewAddOn) {
			this.viewAddOn = viewAddOn;
		}
	}

	private static class ViewAddOnSubclassAwareAttribute implements ViewAddOnAware<ViewAddOnSubclass> {
		@Override
		public void setViewAddOn(ViewAddOnSubclass viewAddOn) {
		}
	}

	private static class ViewAddOnForView implements ViewAddOn {
		public ViewAddOnForView(View view) {
		}
	}

	private static class ViewAddOnSubclass extends ViewAddOnForView {
		public ViewAddOnSubclass(ViewSubclass view) {
			super(view);
		}
	}

	private static class ViewSubclass extends View {
		public ViewSubclass(Context context) {
			super(context);
		}
	}

}
