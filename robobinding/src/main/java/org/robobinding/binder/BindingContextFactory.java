package org.robobinding.binder;

import org.robobinding.BinderImplementor;
import org.robobinding.BinderProvider;
import org.robobinding.BindingContext;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterFactory;

import android.content.Context;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContextFactory {
    private final Context context;
    private final boolean preInitializeViews;
    private final NonBindingViewInflater nonBindingViewInflater;
    private final PresentationModelAdapterFactory presentationModelAdapterFactory;

    public BindingContextFactory(Context context, boolean preInitializeViews, 
	    NonBindingViewInflater nonBindingViewInflater, 
	    PresentationModelAdapterFactory presentationModelAdapterFactory) {
	this.context = context;
	this.preInitializeViews = preInitializeViews;
	this.nonBindingViewInflater = nonBindingViewInflater;
	this.presentationModelAdapterFactory = presentationModelAdapterFactory;
    }

    public BindingContext create(BinderImplementor binderImplementor, Object presentationModel) {
	PresentationModelAdapter presentationModelAdapter = presentationModelAdapterFactory.create(presentationModel);
	BinderProvider binderFactory = new BinderProviderImpl(binderImplementor, nonBindingViewInflater);
	return new BindingContext(binderFactory, context, presentationModelAdapter, preInitializeViews);
    }
}