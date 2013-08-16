/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
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
	return binderProvider.getItemBinder();
    }

    public ViewBinder createViewBinder() {
	InternalViewBinder internalBinder = binderProvider.getInternalViewBinder();
	return new ViewBinder(internalBinder, this);
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
