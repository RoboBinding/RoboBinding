package org.robobinding;

import java.util.Collection;

import org.robobinding.binder.BindingViewInflater;
import org.robobinding.binder.InflatedViewWithRoot;
import org.robobinding.binder.ViewBindingLifecycle;

import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ItemBinder {
	private final BindingViewInflater bindingViewInflater;
	private final ViewBindingLifecycle viewBindingLifecycle;
	private final BindingContextFactoryA bindingContextFactory;

	public ItemBinder(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle,
			BindingContextFactoryA bindingContextFactory) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.bindingContextFactory = bindingContextFactory;
	}

	public BindableView inflateWithoutAttachingToRoot(int layoutId, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup, ViewGroup root) {
		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId, predefinedPendingAttributesForViewGroup, 
				root, false);

		return new BindableView(viewBindingLifecycle, inflatedView, bindingContextFactory);
	}
}
