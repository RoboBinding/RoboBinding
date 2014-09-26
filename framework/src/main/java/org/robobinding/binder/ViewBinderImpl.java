package org.robobinding.binder;

import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.ViewBinder;

import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBinderImpl implements ViewBinder {
	private final BindingViewInflater bindingViewInflater;
	private final ViewBindingLifecycle viewBindingLifecycle;

	public ViewBinderImpl(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
	}

	@Override
	public View inflateAndBind(int layoutId, Object presentationModel) {
		checkLayoutId(layoutId);
		checkPresentationModel(presentationModel);
	
		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId);
		viewBindingLifecycle.run(inflatedView, presentationModel);
		return inflatedView.getRootView();
	}

	private void checkLayoutId(int layoutId) {
		checkValidResourceId(layoutId, "invalid layoutId '" + layoutId + "'");
	}

	private void checkPresentationModel(Object presentationModel) {
		Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
	}

	@Override
	public View inflateAndBind(int layoutId, Object presentationModel, ViewGroup root) {
		return inflateAndBind(layoutId, presentationModel, root, true);
	}
	
	private View inflateAndBind(int layoutId, Object presentationModel, ViewGroup root, boolean attachToRoot) {
		checkLayoutId(layoutId);
		checkPresentationModel(presentationModel);
		checkRoot(root);

		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId, root, attachToRoot);
		viewBindingLifecycle.run(inflatedView, presentationModel);
		return inflatedView.getRootView();
	}
	
	private void checkRoot(ViewGroup attachToRoot) {
		Preconditions.checkNotNull(attachToRoot, "Root must not be null");
	}

	@Override
	public View inflateAndBindWithoutAttachingToRoot(int layoutId, Object presentationModel, ViewGroup root) {
		return inflateAndBind(layoutId, presentationModel, root, false);
	}
}
