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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.binder.ItemBinder;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.DataSetValueModelWrapper;
import org.robobinding.property.PresentationModelPropertyChangeListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class DataSetAdapter<T> extends BaseAdapter
{
	private enum ViewType {ITEM_LAYOUT, DROPDOWN_LAYOUT}
	
	private DataSetValueModel<T> dataSetValueModel;
	private final ItemBinder rowBinder;
	
	private boolean preInitializeViews;
	private boolean propertyChangeEventOccurred = false;
	
	public DataSetAdapter(Context context, boolean preInitializeViews)
	{
		this.rowBinder = new ItemBinder(context);
		this.preInitializeViews = preInitializeViews;
	}

	public void observeChangesOnTheValueModel()
	{
		dataSetValueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener() {
			@Override
			public void propertyChanged()
			{
				propertyChangeEventOccurred = true;
				notifyDataSetChanged();
			}
		});
	}

	public void setValueModel(DataSetValueModel<T> valueModel)
	{
		if (!preInitializeViews)
			returnZeroSizeDataSetUntilPropertyChangeEvent(valueModel);
		else
			this.dataSetValueModel = valueModel;
	}

	private void returnZeroSizeDataSetUntilPropertyChangeEvent(final DataSetValueModel<T> valueModel)
	{
		this.dataSetValueModel = new DataSetValueModelWrapper<T>(valueModel){
			@Override
			public int size()
			{
				if (propertyChangeEventOccurred)
					return valueModel.size();
				
				return 0;
			}
		};
	}
	
	public void setItemLayoutId(int itemLayoutId)
	{
		rowBinder.setItemLayoutId(itemLayoutId);
	}
	
	public void setDropdownLayoutId(int dropdownLayoutId)
	{
		rowBinder.setDropdownLayoutId(dropdownLayoutId);
	}
	
	@Override
	public int getCount()
	{
		return dataSetValueModel.size();
	}

	@Override
	public T getItem(int position)
	{
		return dataSetValueModel.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, ViewType.ITEM_LAYOUT);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, ViewType.DROPDOWN_LAYOUT);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, ViewType viewType)
	{
		View view;

		if (convertView == null)
		{
			view = newView(position, parent, viewType);
		} 
		else
		{
			view = convertView;
		}

		updateItemPresentationModel(view, position);

		return view;
	}

	private View newView(int position, ViewGroup parent, ViewType viewType)
	{
		ItemPresentationModel<T> itemPresentationModel = dataSetValueModel.newItemPresentationModel();
		View view = viewType == ViewType.ITEM_LAYOUT ? rowBinder.inflateItemAndBindTo(itemPresentationModel) : rowBinder.inflateDropdownAndBindTo(itemPresentationModel);
		view.setTag(itemPresentationModel);
		return view;
	}
	
	private void updateItemPresentationModel(View view, int position)
	{
		@SuppressWarnings("unchecked")
		ItemPresentationModel<T> itemPresentationModel = (ItemPresentationModel<T>)view.getTag();
		if(itemPresentationModel != null)
		{
			itemPresentationModel.updateData(position, getItem(position));
		}else
		{
			System.out.println("null itemPresentationModel");
		}
	}

	public void setItemMappingAttribute(ItemMappingAttribute itemMappingAttribute)
	{
		rowBinder.setItemMappingAttribute(itemMappingAttribute);
	}

	public void setDropdownMappingAttribute(DropdownMappingAttribute dropdownMappingAttribute)
	{
		rowBinder.setDropdownMappingAttribute(dropdownMappingAttribute);
	}
}