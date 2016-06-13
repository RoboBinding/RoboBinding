package org.robobinding.binder;

import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.BindingContext;
import org.robobinding.BindingContextFactoryB;
import org.robobinding.ViewBinder;
import org.robobinding.util.Preconditions;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewBinderImpl implements ViewBinder {
	private final BindingViewInflater bindingViewInflater;
	private final ViewBindingLifecycle viewBindingLifecycle;
	private final BindingContextFactoryB bindingContextFactory;

	public ViewBinderImpl(BindingViewInflater bindingViewInflater, ViewBindingLifecycle viewBindingLifecycle,
			BindingContextFactoryB bindingContextFactory) {
		this.bindingViewInflater = bindingViewInflater;
		this.viewBindingLifecycle = viewBindingLifecycle;
		this.bindingContextFactory = bindingContextFactory;
	}

	@Override
	public View inflateAndBind(int layoutId, Object presentationModel) {
		checkLayoutId(layoutId);
		checkPresentationModel(presentationModel);
		BindingContext bindingContext = bindingContextFactory.create(presentationModel);
	
		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId);
		viewBindingLifecycle.run(inflatedView, bindingContext);
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
		BindingContext bindingContext = bindingContextFactory.create(presentationModel);

		InflatedViewWithRoot inflatedView = bindingViewInflater.inflateView(layoutId, root, attachToRoot);
		viewBindingLifecycle.run(inflatedView, bindingContext);
		return inflatedView.getRootView();
	}
	
	private void checkRoot(ViewGroup root) {
		Preconditions.checkNotNull(root, "Root must not be null");
	}

	@Override
	public View inflateAndBindWithoutAttachingToRoot(int layoutId, Object presentationModel, ViewGroup root) {
		return inflateAndBind(layoutId, presentationModel, root, false);
	}
	
	public ViewBinder with(BindingContextFactoryB bindingContextFactory) {
		return new ViewBinderImpl(bindingViewInflater, viewBindingLifecycle, bindingContextFactory);
	}
}
