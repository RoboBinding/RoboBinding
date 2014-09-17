package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.ItemBinder;
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
	private final ViewBinder viewBinder;

	private ItemBinder itemBinder;

	public BinderProviderImpl(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle, ViewBinder viewBinder) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.viewBinder = viewBinder;
	}

	@Override
	public ItemBinder getItemBinder() {
		if (itemBinder == null) {
			itemBinder = new ItemBinder(bindingViewInflater, viewBindingLifecycle);
		}

		return itemBinder;
	}

	@Override
	public ViewBinder getViewBinder() {
		return viewBinder;
	}

}
