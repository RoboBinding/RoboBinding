package org.robobinding.binder;

import java.util.Collection;

import org.robobinding.BinderImplementor;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
class InternalBinder implements BinderImplementor {
    protected final BindingViewInflater bindingViewInflater;
    private final ErrorFormatter errorFormatter;
    private final BindingContextFactory bindingContextFactory;

    public InternalBinder(BindingViewInflater bindingViewInflater, 
	    BindingContextFactory bindingContextFactory, 
	    ErrorFormatter errorFormatter) {
	this.bindingViewInflater = bindingViewInflater;
	this.bindingContextFactory = bindingContextFactory;
	this.errorFormatter = errorFormatter;
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel) {
	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId);

	return bind(inflatedView, presentationModel);
    }

    protected View bind(InflatedView inflatedView, Object presentationModel) {
        BindingContext bindingContext = bindingContextFactory.create(this, presentationModel);
        
        inflatedView.bindChildViews(bindingContext);
        inflatedView.assertNoErrors(errorFormatter);
    
        if (bindingContext.shouldPreInitializeViews()) {
            inflatedView.preinitializeViews(bindingContext);
        }
    
        return inflatedView.getRootView();
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot) {
        InflatedView inflatedView = bindingViewInflater.inflateView(layoutId, attachToRoot);
        
        return bind(inflatedView, presentationModel);
    }

    @Override
    public View inflateAndBind(int layoutId, Object presentationModel,
	    Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	InflatedView inflatedView = bindingViewInflater.inflateView(layoutId, predefinedPendingAttributesForViewGroup);

	return bind(inflatedView, presentationModel);
    }
}
