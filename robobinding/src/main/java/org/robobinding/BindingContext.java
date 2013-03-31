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

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapterImpl;
import org.robobinding.property.ValueModel;

import android.content.Context;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingContext
{
	private final BinderImplementorFactory binderImplementorFactory;
	private final Context context;
	private final PresentationModelAdapter presentationModelAdapter;
	private final boolean preInitializeViews;

	public BindingContext(BinderImplementorFactory factory, Context context, Object presentationModel, boolean preInitializeViews)
	{
		this.binderImplementorFactory = factory;
		this.context = context;
		this.presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		this.preInitializeViews = preInitializeViews;
	}

	public PresentationModelAdapter getPresentationModelAdapter()
	{
		return presentationModelAdapter;
	}

	public Context getContext()
	{
		return context;
	}

	public ItemBinder createItemBinder()
	{
		return new ItemBinder(binderImplementorFactory.create());
	}

	public View inflateView(int layoutId)
	{
		ViewBinder viewBinder = createViewBinder();
		return viewBinder.inflate(layoutId);
	}
	
	private ViewBinder createViewBinder()
	{
		return new ViewBinder(binderImplementorFactory.create());
	}

	public View inflateViewAndBindTo(int layoutId, ValueModelAttribute presentationModelAttribute)
	{
		ViewBinder viewBinder = createViewBinder();
		Object presentationModel = getPresentationModel(presentationModelAttribute);
		return viewBinder.inflateAndBind(layoutId, presentationModel);
	}

	private Object getPresentationModel(ValueModelAttribute presentationModelAttributeValue)
	{
		PresentationModelAdapter presentationModelAdapter = getPresentationModelAdapter();
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(presentationModelAttributeValue.getPropertyName());
		return valueModel.getValue();
	}

	public boolean shouldPreInitializeViews()
	{
		return preInitializeViews;
	}
}
