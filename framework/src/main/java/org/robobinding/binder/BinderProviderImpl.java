package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.ItemBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.SubViewBinder;
import org.robobinding.ViewBinder;

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
	private final ViewBinder viewBinder;

	public BinderProviderImpl(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle, 
			NonBindingViewInflater nonBindingViewInflater, ViewBinder viewBinder) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.nonBindingViewInflater = nonBindingViewInflater;
		this.viewBinder = viewBinder;
	}

	@Override
	public ItemBinder createItemBinder() {
		return new ItemBinder(bindingViewInflater, viewBindingLifecycle);
	}

	@Override
	public SubViewBinder createSubViewBinder() {
		return new SubViewBinder(nonBindingViewInflater, viewBinder);
	}

}
