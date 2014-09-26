package org.robobinding;

import java.util.Collection;

import org.robobinding.binder.BindingViewInflater;
import org.robobinding.binder.InflatedViewWithRoot;
import org.robobinding.binder.ViewBindingLifecycle;

import android.view.View;
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

	public ItemBinder(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
	}

	public View inflateAndBindWithoutAttachingToRoot(int layoutId, Object presentationModel, 
			Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup, ViewGroup root) {
		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId, predefinedPendingAttributesForViewGroup, 
				root, false);

		viewBindingLifecycle.run(inflatedView, presentationModel);
		return inflatedView.getRootView();
	}
}
