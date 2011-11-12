/**
 * DataSetAdapter.java
 * 11 Oct 2011 Copyright Cheng Wei and Robert Taylor
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import robobinding.binding.Binder;
import robobinding.itempresentationmodel.ItemPresentationModel;
import robobinding.property.AbstractDataSetProperty;
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
	private AbstractDataSetProperty<T> dataSetValueModel;
	private int itemLayoutId;
	private int dropdownLayoutId;
	private final Context context;
	private Binder binder;
	
	public DataSetAdapter(Context context)
	{
		this.context = context;
		binder = new Binder();
	}

	public void observeChangesOnTheValueModel()
	{
		dataSetValueModel.addValueChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt)
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
		this.itemLayoutId = itemLayoutId;
	}
	
	public void setDropdownLayoutId(int dropdownLayoutId)
	{
		this.dropdownLayoutId = dropdownLayoutId;
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
		return createViewFromResource(position, convertView, parent, itemLayoutId);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, dropdownLayoutId);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int layoutId)
	{
		View view;

		if (convertView == null)
		{
			view = newView(position, parent, layoutId);
		} 
		else
		{
			view = convertView;
		}

		updateItemPresentationModel(view, position);

		return view;
	}

	private void updateItemPresentationModel(View view, int position)
	{
		@SuppressWarnings("unchecked")
		ItemPresentationModel<T> itemPresentationModel = (ItemPresentationModel<T>)view.getTag();
		dataSetValueModel.updateItemPresentationModel(itemPresentationModel, position);
	}

	private View newView(int position, ViewGroup parent, int layoutId)
	{
		ItemPresentationModel<T> itemPresentationModel = dataSetValueModel.newItemPresentationModel();
		View view = binder.inflateAndBindView(context, layoutId, itemPresentationModel);
		view.setTag(itemPresentationModel);
		return view;
	}
}