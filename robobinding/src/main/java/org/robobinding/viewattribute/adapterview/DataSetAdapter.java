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

import java.util.Collection;
import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.DataSetValueModelWrapper;
import org.robobinding.property.PresentationModelPropertyChangeListener;
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
	
	private boolean preInitializeViews;
	private boolean propertyChangeEventOccurred = false;
	
	private int itemLayoutId;
	private int dropDownLayoutId;
	
	private final ItemBinder itemBinder;
	private final ItemBinder dropDownBinder;
	
	public DataSetAdapter(BindingContext bindingContext)
	{
		itemBinder = bindingContext.createItemBinder();
		dropDownBinder = bindingContext.createItemBinder();
		this.preInitializeViews = bindingContext.shouldPreinitializeViews();
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
			dataSetValueModel = wrapAsZeroSizeDataSetUntilPropertyChangeEvent(valueModel);
		else
			dataSetValueModel = valueModel;
	}

	private DataSetValueModel<T> wrapAsZeroSizeDataSetUntilPropertyChangeEvent(final DataSetValueModel<T> valueModel)
	{
		return new DataSetValueModelWrapper<T>(valueModel){
			@Override
			public int size()
			{
				if (propertyChangeEventOccurred)
					return valueModel.size();
				
				return 0;
			}
		};
	}
	
	@Override
	public int getCount()
	{
		if (dataSetValueModel == null)
			return 0;
		
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
		View view;
		if(viewType == ViewType.ITEM_LAYOUT)
		{
			view = itemBinder.inflateAndBind(itemLayoutId, itemPresentationModel);
		}
		else
		{
			view = dropDownBinder.inflateAndBind(dropDownLayoutId, itemPresentationModel);
		}
		view.setTag(itemPresentationModel);
		return view;
	}
	
	private void updateItemPresentationModel(View view, int position)
	{
		@SuppressWarnings("unchecked")
		ItemPresentationModel<T> itemPresentationModel = (ItemPresentationModel<T>)view.getTag();
		itemPresentationModel.updateData(position, getItem(position));
	}

	public void setItemLayoutId(int itemLayoutId)
	{
		this.itemLayoutId = itemLayoutId;
	}

	public void setDropDownLayoutId(int dropDownLayoutId)
	{
		this.dropDownLayoutId = dropDownLayoutId;
	}

	public void setItemPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
	{
		itemBinder.setPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup);
	}

	public void setDropdownPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup)
	{
		dropDownBinder.setPredefinedPendingAttributesForViewGroup(predefinedPendingAttributesForViewGroup);
	}
}