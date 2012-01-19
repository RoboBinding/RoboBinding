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
import org.robobinding.viewattribute.BindingDetailsBuilder;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.ResourceBindingDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class SubViewCreator
{
	private Context context;
	private String layoutResource;
	public SubViewCreator(Context context, String layoutResource)
	{
		this.context = context;
		this.layoutResource = layoutResource;
	}
	
	public View create()
	{
		int layoutId = getLayoutId();
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(layoutId, null);
	}
	
	public View createAndBindTo(String presentationModelAttributeValue, PresentationModelAdapter presentationModelAdapter)
	{
		int layoutId = getLayoutId();
		
		Object presentationModel = getPresentationModel(presentationModelAdapter, presentationModelAttributeValue);
		return Binder.bindView(context, layoutId, presentationModel);
	}

	int getLayoutId()
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(layoutResource);
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		return resourceBindingDetails.getResourceId(context);
	}

	Object getPresentationModel(PresentationModelAdapter presentationModelAdapter, String presentationModelAttributeValue)
	{
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(presentationModelAttributeValue);
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
		return valueModel.getValue();
	}

}
