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


import java.util.Collections;
import java.util.List;

import robobinding.binding.ViewAttribute;
import robobinding.internal.com_google_common.collect.Lists;
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
	private final List<AdapterViewAttribute> adapterViewAttributes;
	
	public AdaptedDataSetAttributes(AdapterView adapterView, SourceAttribute sourceAttribute, ItemLayoutAttribute itemLayoutAttribute, ItemMappingAttribute itemMappingAttribute, DropdownLayoutAttribute dropdownLayoutAttribute, DropdownMappingAttribute dropdownMappingAttribute)
	{
		this.adapterView = adapterView;
		
		adapterViewAttributes = Lists.newArrayList();
		addAdapterViewAttribute(sourceAttribute);
		addAdapterViewAttribute(itemLayoutAttribute);
		addAdapterViewAttribute(itemMappingAttribute);
		addAdapterViewAttribute(dropdownLayoutAttribute);
		addAdapterViewAttribute(dropdownMappingAttribute);
	}

	private void addAdapterViewAttribute(AdapterViewAttribute adapterViewAttribute)
	{
		if (adapterViewAttribute != null)
			adapterViewAttributes.add(adapterViewAttribute);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		DataSetAdapter<?> dataSetAdapter = new DataSetAdapter(context);
		
		for (AdapterViewAttribute adapterViewAttribute : adapterViewAttributes)
			adapterViewAttribute.bind(dataSetAdapter, presentationModelAdapter, context);
		
		dataSetAdapter.observeChangesOnTheValueModel();
		adapterView.setAdapter(dataSetAdapter);
	}
	
	public List<AdapterViewAttribute> getAdapterViewAttributes()
	{
		return Collections.unmodifiableList(adapterViewAttributes);
	}
}
