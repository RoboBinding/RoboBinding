package org.robobinding;

import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;

public class BindingContextFactoryImpl implements BindingContextFactory {
	private final BinderProvider binderProvider;
	private final Context context;
	private final PreInitializingViews preInitializeViews;
	
	public BindingContextFactoryImpl(BinderProvider binderProvider, Context context, PreInitializingViews preInitializeViews) {
		this.binderProvider = binderProvider;
		this.context = context;
		this.preInitializeViews = preInitializeViews;
	}
	
	@Override
	public BindingContext create(PresentationModelAdapter presentationModel) {
		return new BindingContext(binderProvider, context, presentationModel, preInitializeViews);
	}
}