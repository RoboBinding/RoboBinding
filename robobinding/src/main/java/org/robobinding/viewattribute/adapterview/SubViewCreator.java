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

import org.robobinding.BindingContext;
import org.robobinding.ViewBinder;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.PropertyAttributeValue;
import org.robobinding.viewattribute.ResourceAttributeValue;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class SubViewCreator
{
	private final BindingContext bindingContext;
	private final String layoutResource;
	public SubViewCreator(BindingContext bindingContext, String layoutResource)
	{
		this.bindingContext = bindingContext;
		this.layoutResource = layoutResource;
	}
	
	public View create()
	{
		int layoutId = getLayoutId();
		ViewBinder viewBinder = bindingContext.createViewBinder();
		return viewBinder.inflate(layoutId);
	}
	
	public View createAndBindTo(String presentationModelAttributeValue)
	{
		int layoutId = getLayoutId();
		
		ViewBinder viewBinder = bindingContext.createViewBinder();
		Object presentationModel = getPresentationModel(presentationModelAttributeValue);
		return viewBinder.inflateAndBind(layoutId, presentationModel);
	}

	int getLayoutId()
	{
		ResourceAttributeValue attributeValue = new ResourceAttributeValue(layoutResource);
		return attributeValue.getResourceId(bindingContext.getContext());
	}

	Object getPresentationModel(String presentationModelAttributeValue)
	{
		PropertyAttributeValue attributeValue = new PropertyAttributeValue(presentationModelAttributeValue);
		PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(attributeValue.getPropertyName());
		return valueModel.getValue();
	}

}
