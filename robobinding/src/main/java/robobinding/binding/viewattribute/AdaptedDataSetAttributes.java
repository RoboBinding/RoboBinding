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
package robobinding.binding.viewattribute;


import robobinding.binding.ViewAttribute;
import robobinding.presentationmodel.DataSetAdapter;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.content.Context;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("rawtypes")
public class AdaptedDataSetAttributes implements ViewAttribute
{
	private final AdapterView adapterView;
	private final SourceAttribute sourceAttribute;
	private final ItemLayoutAttribute itemLayoutAttribute;
	private final ItemMappingAttribute itemMappingAttribute;
	private final DropdownLayoutAttribute dropdownLayoutAttribute;
	private final DropdownMappingAttribute dropdownMappingAttribute;
	
	public AdaptedDataSetAttributes(AdapterView adapterView, SourceAttribute sourceAttribute, ItemLayoutAttribute itemLayoutAttribute, ItemMappingAttribute itemMappingAttribute, DropdownLayoutAttribute dropdownLayoutAttribute, DropdownMappingAttribute dropdownMappingAttribute)
	{
		this.adapterView = adapterView;
		this.sourceAttribute = sourceAttribute;
		this.itemLayoutAttribute  = itemLayoutAttribute;
		this.itemMappingAttribute = itemMappingAttribute;
		this.dropdownLayoutAttribute = dropdownLayoutAttribute;
		this.dropdownMappingAttribute = dropdownMappingAttribute;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		DataSetAdapter<?> dataSetAdapter = new DataSetAdapter(context);
		
		sourceAttribute.bind(dataSetAdapter, presentationModelAdapter, context);
		itemLayoutAttribute.bind(dataSetAdapter, presentationModelAdapter, context);
		
		if (hasDropdownLayoutAttribute())
			dropdownLayoutAttribute.bind(dataSetAdapter, presentationModelAdapter, context);
		
		dataSetAdapter.observeChangesOnTheValueModel();
		adapterView.setAdapter(dataSetAdapter);
	}

	public boolean hasDropdownLayoutAttribute()
	{
		return dropdownLayoutAttribute != null;
	}
}
