package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.BindingContext;
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
    private final PresentationModelAdapterFactory presentationModelAdapterFactory;
    private BinderProvider binderProvider;

    public BindingContextFactory(Context context, boolean preInitializeViews, 
	    PresentationModelAdapterFactory presentationModelAdapterFactory) {
	this.context = context;
	this.preInitializeViews = preInitializeViews;
	this.presentationModelAdapterFactory = presentationModelAdapterFactory;
    }

    public void setBinderProvider(BinderProvider binderProvider) {
        this.binderProvider = binderProvider;
    }

    public BindingContext create(Object presentationModel) {
	PresentationModelAdapter presentationModelAdapter = presentationModelAdapterFactory.create(presentationModel);
	return new BindingContext(binderProvider, context, presentationModelAdapter, preInitializeViews);
    }
}