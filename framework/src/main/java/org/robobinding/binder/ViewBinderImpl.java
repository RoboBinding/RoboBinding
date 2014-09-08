package org.robobinding.binder;

import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewBinder;
import org.robobinding.internal.guava.Preconditions;

import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBinderImpl implements ViewBinder {
    private final NonBindingViewInflater nonBindingViewInflater;
    private final BindingViewInflater bindingViewInflater;
    private final ViewBindingLifecycle viewBindingLifecycle;

    public ViewBinderImpl(NonBindingViewInflater nonBindingViewInflater, BindingViewInflater bindingViewInflater,
	    ViewBindingLifecycle viewBindingLifecycle) {
        this.nonBindingViewInflater = nonBindingViewInflater;
        this.bindingViewInflater = bindingViewInflater;
        this.viewBindingLifecycle = viewBindingLifecycle;
    }

    @Override
    public View inflate(int layoutId) {
	checkLayoutId(layoutId);
	
        return nonBindingViewInflater.inflate(layoutId);
    }
    
    private void checkLayoutId(int layoutId) {
	checkValidResourceId(layoutId, "invalid layoutId '" + layoutId + "'");
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel) {
	checkLayoutId(layoutId);
	checkPresentationModel(presentationModel);
	
	InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId);
	viewBindingLifecycle.run(inflatedView, presentationModel);
	return inflatedView.getRootView();
    }

    private void checkPresentationModel(Object presentationModel) {
        Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
    }
    
    @Override
    public View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot) {
	checkLayoutId(layoutId);
	checkPresentationModel(presentationModel);
	checkAttachToRoot(attachToRoot);

	InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId, attachToRoot);
	viewBindingLifecycle.run(inflatedView, presentationModel);
	return inflatedView.getRootView();
    }
    
    private void checkAttachToRoot(ViewGroup attachToRoot) {
	Preconditions.checkNotNull(attachToRoot, "attachToRoot must not be null");
    }
}
