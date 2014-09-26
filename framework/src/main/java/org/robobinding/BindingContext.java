package org.robobinding;

import org.robobinding.function.Function;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModel;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContext implements PresentationModelAdapter {
	private final BinderProvider binderProvider;
	private final Context context;
	private final PresentationModelAdapter presentationModelAdapter;
	private final boolean preInitializeViews;

	public BindingContext(BinderProvider binderProvider, Context context, PresentationModelAdapter presentationModelAdapter, boolean preInitializeViews) {
		this.binderProvider = binderProvider;
		this.context = context;
		this.presentationModelAdapter = presentationModelAdapter;
		this.preInitializeViews = preInitializeViews;
	}

	public Context getContext() {
		return context;
	}

	public ItemBinder createItemBinder() {
		return binderProvider.createItemBinder();
	}

	public SubViewBinder createSubViewBinder() {
		return binderProvider.createSubViewBinder();
	}

	public boolean shouldPreInitializeViews() {
		return preInitializeViews;
	}

	@Override
	public DataSetValueModel<?> getDataSetPropertyValueModel(String propertyName) {
		return presentationModelAdapter.getDataSetPropertyValueModel(propertyName);
	}

	@Override
	public Class<?> getPropertyType(String propertyName) {
		return presentationModelAdapter.getPropertyType(propertyName);
	}

	@Override
	public <T> ValueModel<T> getReadOnlyPropertyValueModel(String propertyName) {
		return presentationModelAdapter.getReadOnlyPropertyValueModel(propertyName);
	}

	@Override
	public <T> ValueModel<T> getPropertyValueModel(String propertyName) {
		return presentationModelAdapter.getPropertyValueModel(propertyName);
	}

	@Override
	public Function findFunction(String functionName, Class<?>... parameterTypes) {
		return presentationModelAdapter.findFunction(functionName, parameterTypes);
	}

	@Override
	public String getPresentationModelClassName() {
		return presentationModelAdapter.getPresentationModelClassName();
	}
}
