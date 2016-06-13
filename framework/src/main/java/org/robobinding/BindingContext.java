package org.robobinding;

import org.robobinding.function.Function;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContext {
	private final BinderProvider binderProvider;
	private final Context context;
	private final PresentationModelAdapter presentationModelAdapter;
	private final PreInitializingViews preInitializeViews;

	public BindingContext(BinderProvider binderProvider, Context context, 
			PresentationModelAdapter presentationModelAdapter, 
			PreInitializingViews preInitializeViews) {
		this.binderProvider = binderProvider;
		this.context = context;
		this.presentationModelAdapter = presentationModelAdapter;
		this.preInitializeViews = preInitializeViews;
	}
	
	public Class<?> getPropertyType(String propertyName) {
		return presentationModelAdapter.getPropertyType(propertyName);
	}

	public <T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName) {
		return presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
	}

	public <T> ValueModel<T> getPropertyValueModel(String propertyName) {
		return presentationModelAdapter.getPropertyValueModel(propertyName);
	}

	public Function findFunction(String functionName, Class<?>... parameterTypes) {
		return presentationModelAdapter.findFunction(functionName, parameterTypes);
	}

	public String getPresentationModelClassName() {
		return presentationModelAdapter.getPresentationModelClassName();
	}

	public boolean shouldPreInitializeViews() {
		return preInitializeViews.value;
	}

	public Context getContext() {
		return context;
	}

	public View inflateWithoutAttachingToRoot(int layoutId, ViewGroup parent) {
		SubViewBinderFactory factory = createSubViewBinderFactory();
		SubViewBinder viewBinder = factory.createSubViewBinder();
		return viewBinder.inflateWithoutAttachingToRoot(layoutId, parent);
	}
	
	private SubViewBinderFactory createSubViewBinderFactory() {
		return new SubViewBinderFactory(binderProvider, 
				createBindingContextFactory(preInitializeViews.withValue(preInitializeViews.defaultValue)));
	}
	
	private BindingContextFactory createBindingContextFactory(PreInitializingViews preInitializingViews) {
		return new BindingContextFactoryImpl(binderProvider, context, preInitializingViews);
	}

	public SubBindingContext navigateToSubContext(String propertyName) {
		Object subPresentationModel = presentationModelAdapter.getSubPresentationModelProperty(propertyName);
		return new SubBindingContext(createSubViewBinderFactory(), subPresentationModel);
	}

	public ItemBindingContext navigateToItemContext(String propertyName) {
		DataSetValueModel valueModel = presentationModelAdapter.getDataSetPropertyValueModel(propertyName);
		boolean itemPreInitializeViews = valueModel.preInitializingViewsWithDefault(preInitializeViews.defaultValue);
		return new ItemBindingContext(
				new ItemBinderFactory(binderProvider, createBindingContextFactory(preInitializeViews.withValue(itemPreInitializeViews))), 
				valueModel, 
				itemPreInitializeViews);
	}
	
	
	static class ItemBinderFactory {
		private final BinderProvider binderProvider;
		private final BindingContextFactory factory;
		
		public ItemBinderFactory(BinderProvider binderProvider, BindingContextFactory factory) {
			this.binderProvider = binderProvider;
			this.factory = factory;
		}
		
		public ItemBinder createItemBinder() {
			return binderProvider.createItemBinder(factory);
		}
	}
	
	static class SubViewBinderFactory {
		private final BinderProvider binderProvider;
		private final BindingContextFactory factory;
		
		public SubViewBinderFactory(BinderProvider binderProvider, BindingContextFactory factory) {
			this.binderProvider = binderProvider;
			this.factory = factory;
		}
		
		public SubViewBinder createSubViewBinder() {
			return binderProvider.createSubViewBinder(factory);
		}
	}
}
