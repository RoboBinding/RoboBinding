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

import java.util.List;
import java.util.Map;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.PropertyViewAttribute;
import robobinding.presentationmodel.DataSetAdapter;
import robobinding.presentationmodel.ListValueModel;
import android.content.Context;
import android.widget.ListView;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class ListViewAttributes<T> implements GroupedViewAttributes
{
	//binding:source="albums"
	//binding:itemLayout="@layout/album_row"
	//binding:dropdownLayout="@layout/album_row"

	private final ListView listView;
	private List<PropertyViewAttribute> viewAttributes;

	DataSetAdapter<List<T>, T> dataSetAdapter;
	
	public ListViewAttributes(ListView listView, SourceAttribute<T> sourceAttribute, ItemLayoutAttribute<T> itemLayoutAttribute, 
									DropdownLayoutAttribute<T> dropdownLayoutAttribute)
	{
		this.listView = listView;
		viewAttributes = Lists.newArrayList();
		dataSetAdapter = new DataSetAdapter<List<T>, T>();
		
		sourceAttribute.setDataSetAdapter(dataSetAdapter);
		itemLayoutAttribute.setDataSetAdapter(dataSetAdapter);
			
		addAttribute(sourceAttribute);
		addAttribute(itemLayoutAttribute);
		
		if (dropdownLayoutAttribute != null)
		{
			addAttribute(dropdownLayoutAttribute);
			dropdownLayoutAttribute.setDataSetAdapter(dataSetAdapter);
		}
	}

	private void addAttribute(PropertyViewAttribute viewAttribute)
	{
		viewAttributes.add(viewAttribute);
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		for (PropertyViewAttribute propertyViewAttribute : viewAttributes)
			propertyViewAttribute.bind(presentationModelAdapter);
	
		listView.setAdapter(dataSetAdapter);
	}

}
