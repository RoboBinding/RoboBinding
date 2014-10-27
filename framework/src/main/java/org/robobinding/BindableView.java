package org.robobinding;

import org.robobinding.binder.InflatedViewWithRoot;
import org.robobinding.binder.ViewBindingLifecycle;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;

import android.view.View;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class BindableView {
	private final ViewBindingLifecycle viewBindingLifecycle;
	private final InflatedViewWithRoot inflatedView;
	
	public BindableView(ViewBindingLifecycle viewBindingLifecycle, InflatedViewWithRoot inflatedView) {
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.inflatedView = inflatedView;
	}
	
	public void bindTo(AbstractPresentationModelObject presentationModel) {
		viewBindingLifecycle.run(inflatedView, presentationModel);
	}
	
	public View getRootView() {
		return inflatedView.getRootView();
	}
}
