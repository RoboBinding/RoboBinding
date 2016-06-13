package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.BindingContextFactory;
import org.robobinding.ItemBinder;
import org.robobinding.SubViewBinder;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
class BinderProviderProxy implements BinderProvider {
	private BinderProvider delegate = NULL;
	
	@Override
	public ItemBinder createItemBinder(BindingContextFactory factory) {
		return delegate.createItemBinder(factory);
	}

	@Override
	public SubViewBinder createSubViewBinder(BindingContextFactory factory) {
		return delegate.createSubViewBinder(factory);
	}
	
	public void setProvider(BinderProvider provider) {
		this.delegate = provider;
	}

	private static final BinderProvider NULL = new BinderProvider() {
		
		@Override
		public SubViewBinder createSubViewBinder(BindingContextFactory factory) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public ItemBinder createItemBinder(BindingContextFactory factory) {
			throw new UnsupportedOperationException();
		}
	};
}
