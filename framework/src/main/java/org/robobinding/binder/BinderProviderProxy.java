package org.robobinding.binder;

import org.robobinding.BinderProvider;
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
	public ItemBinder createItemBinder() {
		return delegate.createItemBinder();
	}

	@Override
	public SubViewBinder createSubViewBinder() {
		return delegate.createSubViewBinder();
	}
	
	public void setProvider(BinderProvider provider) {
		this.delegate = provider;
	}

	private static final BinderProvider NULL = new BinderProvider() {
		
		@Override
		public SubViewBinder createSubViewBinder() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public ItemBinder createItemBinder() {
			throw new UnsupportedOperationException();
		}
	};
}
