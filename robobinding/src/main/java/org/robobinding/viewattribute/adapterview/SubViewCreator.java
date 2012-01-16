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
import org.robobinding.internal.org_apache_commons_lang3.StringUtils;
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
public class SubViewCreator
{
	private String layoutAttribute;
	private String presentationModelAttribute;
	public SubViewCreator(String layoutAttribute)
	{
		this.layoutAttribute = layoutAttribute;
	}
	
	public SubViewCreator(String layoutAttribute, String presentationModelAttribute)
	{
		this(layoutAttribute);
		this.presentationModelAttribute = presentationModelAttribute;
	}
	
	public View bindAndCreate(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		int layoutId = getLayoutId(context);
		if(hasPresentationModelAttribute())
		{
			Object presentationModel = getPresentationModel(presentationModelAdapter);
			return Binder.bindView(context, layoutId, presentationModel);
		}else
		{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(layoutId, null);
		}
	}

	private int getLayoutId(Context context)
	{
		BindingDetailsBuilder bindingDetailsBuilder = new BindingDetailsBuilder(layoutAttribute);
		ResourceBindingDetails resourceBindingDetails = bindingDetailsBuilder.createResourceBindingDetails();
		return resourceBindingDetails.getResourceId(context);
	}

	private boolean hasPresentationModelAttribute()
	{
		return StringUtils.isNotBlank(presentationModelAttribute);
	}

	private Object getPresentationModel(PresentationModelAdapter presentationModelAdapter)
	{
		PropertyBindingDetails propertyBindingDetails = PropertyBindingDetails.createFrom(presentationModelAttribute);
		ValueModel<Object> valueModel = presentationModelAdapter.getReadOnlyPropertyValueModel(propertyBindingDetails.propertyName);
		return valueModel.getValue();
	}

}
