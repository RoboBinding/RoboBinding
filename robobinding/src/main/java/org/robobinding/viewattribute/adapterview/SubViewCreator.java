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
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewCreator
{
	public View create(int layoutId, BindingContext bindingContext)
	{
		ViewBinder viewBinder = bindingContext.createViewBinder();
		return viewBinder.inflate(layoutId);
	}
	
	public View createAndBindTo(ValueModelAttribute presentationModelAttribute, int layoutId, BindingContext bindingContext)
	{
		ViewBinder viewBinder = bindingContext.createViewBinder();
		Object presentationModel = getPresentationModel(presentationModelAttribute, bindingContext);
		return viewBinder.inflateAndBind(layoutId, presentationModel);
	}

	private Object getPresentationModel(ValueModelAttribute presentationModelAttributeValue, BindingContext bindingContext)
	{
		PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(presentationModelAttributeValue.getPropertyName());
		return valueModel.getValue();
	}
}
