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
package robobinding.presentationmodel;

import robobinding.binding.RowBinder;
import robobinding.binding.viewattribute.DropdownMappingAttribute;
import robobinding.binding.viewattribute.ItemMappingAttribute;
import robobinding.itempresentationmodel.ItemPresentationModel;
import robobinding.property.AbstractDataSetProperty;
import robobinding.property.PresentationModelPropertyChangeListener;
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
	
	private AbstractDataSetProperty<T> dataSetValueModel;
	private final RowBinder rowBinder;
	
	public DataSetAdapter(Context context)
	{
		rowBinder = new RowBinder(context);
	}

	public void observeChangesOnTheValueModel()
	{
		dataSetValueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener() {
			@Override
			public void propertyChanged()
			{
				notifyDataSetChanged();
			}
		});
	}

	public void setValueModel(AbstractDataSetProperty<T> valueModel)
	{
		this.dataSetValueModel = valueModel;
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
		dataSetValueModel.updateItemPresentationModel(itemPresentationModel, position);
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