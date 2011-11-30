/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package robobinding.binding;

import robobinding.binding.BindingViewFactory.InflatedView;
import robobinding.binding.viewattribute.DropdownMappingAttribute;
import robobinding.binding.viewattribute.ItemMappingAttribute;
import robobinding.presentationmodel.PresentationModelAdapter;
import robobinding.presentationmodel.PresentationModelAdapterImpl;
import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RowBinder extends AbstractBinder
{
	private int itemLayoutId;
	private ItemMappingAttribute itemMappingAttribute;
	private int dropdownLayoutId;
	private DropdownMappingAttribute dropdownMappingAttribute;

	public RowBinder(Context context)
	{
		super(context);
	}
	
	public View inflateItemAndBindTo(Object presentationModel)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		InflatedView inflatedView = inflateAndBind(itemLayoutId, presentationModelAdapter);
		
		if (itemMappingAttribute != null)
		{
			itemMappingAttribute.bindToPredefined(bindingAttributesProcessor, inflatedView.getRootView(), presentationModelAdapter, context);
		}
		
		return inflatedView.getRootView();
	}

	public View inflateDropdownAndBindTo(Object presentationModel)
	{
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		InflatedView inflatedView = inflateAndBind(dropdownLayoutId, presentationModelAdapter);
		
		if (dropdownMappingAttribute != null)
		{
			dropdownMappingAttribute.bindToPredefined(bindingAttributesProcessor, inflatedView.getRootView(), presentationModelAdapter, context);
		}
		
		return inflatedView.getRootView();
	}
	
	public void setItemLayoutId(int itemLayoutId)
	{
		this.itemLayoutId = itemLayoutId;
	}

	public void setDropdownLayoutId(int dropdownLayoutId)
	{
		this.dropdownLayoutId = dropdownLayoutId;
	}

	public void setItemMappingAttribute(ItemMappingAttribute itemMappingAttribute)
	{
		this.itemMappingAttribute = itemMappingAttribute;
	}

	public void setDropdownMappingAttribute(DropdownMappingAttribute dropdownMappingAttribute)
	{
		this.dropdownMappingAttribute = dropdownMappingAttribute;
	}
}
