package org.robobinding;

import java.util.Collection;

import org.robobinding.binder.BindingViewInflater;
import org.robobinding.binder.InflatedViewWithRoot;
import org.robobinding.binder.ViewBindingLifecycle;

import android.view.View;

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

	public View inflateAndBind(int layoutId, Object presentationModel, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId, predefinedPendingAttributesForViewGroup);

		viewBindingLifecycle.run(inflatedView, presentationModel);
		return inflatedView.getRootView();
	}
}
