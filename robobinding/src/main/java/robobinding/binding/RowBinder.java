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
import robobinding.binding.viewattribute.ItemMappingAttribute;
import robobinding.presentationmodel.PresentationModelAdapter;
import robobinding.presentationmodel.PresentationModelAdapterImpl;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RowBinder extends AbstractBinder
{
	public enum ViewType {ITEM_LAYOUT, DROPDOWN_LAYOUT}
	
	private int itemLayoutId;
	private ItemMappingAttribute itemMappingAttribute;
	private int dropdownLayoutId;

	public RowBinder(Context context)
	{
		super(context);
	}
	
	public View inflateAndBindTo(ViewType viewType, Object presentationModel)
	{
		int layoutId = viewType == ViewType.ITEM_LAYOUT ? itemLayoutId : dropdownLayoutId;
		PresentationModelAdapter presentationModelAdapter = new PresentationModelAdapterImpl(presentationModel);
		
		if (itemMappingAttribute != null)
		{
			//Calling this every time is going to be expensive. We're already doing this
			//Can we support ViewHolders?
			
			//Insider the RowBinder I want a bindingattributeprocessor
			//This contains the LayoutInflater and the BindingAttributesReader.
			//BindingViewFactory will get one of these
			//Then from here I call lateBindTo(BindingAttributesProcessor, presentationModel, context)
			
			//I need a way to reuse binding attributes. Otherwise everytime we look up the providers, and create a new set of attributes for each view as it is reused
			
			BindingAttributesReader bindingAttributesReader = new BindingAttributesReader(new ProvidersResolver(), new AttributeSetParser(), false);
			itemMappingAttribute.lateBindTo(bindingAttributesReader, inflatedView, presentationModelAdapter, context);
		}
		
		InflatedView inflatedView = inflateAndBind(context, layoutId, presentationModelAdapter, bindingViewFactory);
		
		if (itemMappingAttribute != null)
		{
			itemMappingAttribute.lateBindTo(bindingViewFactory, inflatedView.getRootView(), presentationModelAdapter, context);
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
}
