package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.BindingContextFactory;
import org.robobinding.ItemBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.SubViewBinder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class BinderProviderImpl implements BinderProvider {
	private final BindingViewInflater bindingViewInflater;
	private final ViewBindingLifecycle viewBindingLifecycle;
	private final NonBindingViewInflater nonBindingViewInflater;
	private final ViewBinderImpl viewBinder;
	private final BindingContextFactoryAdapters bindingContextFactoryAdapters;

	public BinderProviderImpl(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle, 
			NonBindingViewInflater nonBindingViewInflater, ViewBinderImpl viewBinder, 
			BindingContextFactoryAdapters bindingContextFactoryAdapters) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.nonBindingViewInflater = nonBindingViewInflater;
		this.viewBinder = viewBinder;
		this.bindingContextFactoryAdapters = bindingContextFactoryAdapters;
	}

	@Override
	public ItemBinder createItemBinder(BindingContextFactory factory) {
		return new ItemBinder(bindingViewInflater, viewBindingLifecycle, bindingContextFactoryAdapters.adaptA(factory));
	}

	@Override
	public SubViewBinder createSubViewBinder(BindingContextFactory factory) {
		return new SubViewBinder(nonBindingViewInflater, 
				viewBinder.with(bindingContextFactoryAdapters.adaptB(factory)));
	}
}
