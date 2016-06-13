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
	private final BindingContextFactoryA bindingContextFactory;
	
	public BindableView(ViewBindingLifecycle viewBindingLifecycle, 
			InflatedViewWithRoot inflatedView, BindingContextFactoryA bindingContextFactory) {
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.inflatedView = inflatedView;
		this.bindingContextFactory = bindingContextFactory;
	}
	
	public void bindTo(AbstractPresentationModelObject presentationModel) {
		BindingContext bindingContext = bindingContextFactory.create(presentationModel);
		viewBindingLifecycle.run(inflatedView, bindingContext);
	}
	
	public View getRootView() {
		return inflatedView.getRootView();
	}
}
