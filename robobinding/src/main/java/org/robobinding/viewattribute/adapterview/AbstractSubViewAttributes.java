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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.binder.Binder;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingDetailsBuilder;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.ResourceBindingDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractSubViewAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T>
{
	private View subView;
	
	@Override
	protected void initializeChildViewAttributes()
	{
		validateAttributes();
	}
	
	private void validateAttributes()
	{
		assertAttributesArePresent(layoutAttribute());
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		initializeSubView(presentationModelAdapter, context);
		
		addVisibilityIfPresent(presentationModelAdapter, context);
	}

	private void initializeSubView(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		int layoutId = getLayoutId(context);
		if(groupedAttributeDetails.hasAttribute(subViewPresentationModelAttribute()))
		{
			Object subViewPresentationModel = getSubViewPresentationModel(presentationModelAdapter);
			subView = Binder.bindView(context, layoutId, subViewPresentationModel);
		}else
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			subView = inflater.inflate(layoutId, null);
		}
		addSubView(subView);
	}

	private int getLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(groupedAttributeDetails.attributeValueFor(layoutAttribute()));
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		return resourceBindingDetails.getResourceId(context);
	}

	private Object getSubViewPresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(subViewPresentationModelAttribute()));
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
		return valueModel.getValue();
	}

	private void addVisibilityIfPresent(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		if(groupedAttributeDetails.hasAttribute(visibilityAttribute()))
		{
			SubViewVisibilityAttribute visibilityAttribute = SubViewVisibilityAttribute.create(subView);
			visibilityAttribute.setPreInitializeView(preInitializeViews);
			visibilityAttribute.setAttributeValue(groupedAttributeDetails.attributeValueFor(visibilityAttribute()));
			visibilityAttribute.bind(presentationModelAdapter, context);
		}
	}
	
	protected abstract String layoutAttribute();
	protected abstract String subViewPresentationModelAttribute();
	protected abstract String visibilityAttribute();
	protected abstract void addSubView(View subView);
}