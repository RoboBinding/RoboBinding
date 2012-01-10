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
package org.robobinding.viewattribute.listview;

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
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractHeaderOrFooterAttributes extends AbstractGroupedViewAttribute<ListView>
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
		initializeFooterView(presentationModelAdapter, context);
		
		addFooterVisibilityIfPresent(presentationModelAdapter, context);
	}

	private void initializeFooterView(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		int footerLayoutId = getFooterLayoutId(context);
		if(groupedAttributeDetails.hasAttribute(sourceAttribute()))
		{
			Object footerSourcePresentationModel = getFooterSourcePresentationModel(presentationModelAdapter);
			subView = Binder.bindView(context, footerLayoutId, footerSourcePresentationModel);
		}else
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			subView = inflater.inflate(footerLayoutId, null);
		}
		addSubView(subView);
	}

	private int getFooterLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(groupedAttributeDetails.attributeValueFor(layoutAttribute()));
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		return resourceBindingDetails.getResourceId(context);
	}

	private Object getFooterSourcePresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(sourceAttribute()));
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
		return valueModel.getValue();
	}

	private void addFooterVisibilityIfPresent(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		if(groupedAttributeDetails.hasAttribute(visibilityAttribute()))
		{
			HeaderOrFooterVisibilityAttribute footerVisibilityAttribute = HeaderOrFooterVisibilityAttribute.create(subView);
			footerVisibilityAttribute.setPreInitializeView(preInitializeViews);
			footerVisibilityAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(groupedAttributeDetails.attributeValueFor(visibilityAttribute())));
			footerVisibilityAttribute.bind(presentationModelAdapter, context);
		}
	}
	
	protected abstract String layoutAttribute();
	protected abstract String sourceAttribute();
	protected abstract String visibilityAttribute();
	protected abstract void addSubView(View subView);
}