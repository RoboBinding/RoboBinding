package org.robobinding.binder;

import org.robobinding.BinderProvider;
import org.robobinding.BindingContext;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
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
	private final PresentationModelAdapterFactory presentationModelAdapterFactory;
	private final BinderProvider binderProvider;

	public BindingContextFactory(Context context,PresentationModelAdapterFactory presentationModelAdapterFactory,
								 BinderProvider binderProvider) {
		this.context = context;
		this.presentationModelAdapterFactory = presentationModelAdapterFactory;
		this.binderProvider = binderProvider;
	}

	public BindingContext create(AbstractPresentationModelObject presentationModel) {
		PresentationModelAdapter presentationModelAdapter = presentationModelAdapterFactory.create(presentationModel);
		return new BindingContext(binderProvider, context, presentationModelAdapter);
	}
}