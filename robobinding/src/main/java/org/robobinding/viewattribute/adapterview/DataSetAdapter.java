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
public class DataSetAdapter<T> extends BaseAdapter {
    private enum ViewType {
	ITEM_LAYOUT, DROPDOWN_LAYOUT
    }

    private final boolean preInitializeViews;
    private final DataSetValueModel<T> dataSetValueModel;

    private final ItemLayoutBinder itemLayoutBinder;
    private final ItemLayoutBinder dropdownLayoutBinder;
    
    private boolean propertyChangeEventOccurred;


    public DataSetAdapter(DataSetValueModel<T> dataSetValueModel, ItemLayoutBinder itemLayoutBinder, 
	    ItemLayoutBinder dropdownLayoutBinder, boolean preInitializeViews) {
        this.preInitializeViews = preInitializeViews;

        this.dataSetValueModel = createValueModelFrom(dataSetValueModel);
        this.itemLayoutBinder = itemLayoutBinder;
        this.dropdownLayoutBinder = dropdownLayoutBinder;
        
	propertyChangeEventOccurred = false;
    }

    private DataSetValueModel<T> createValueModelFrom(DataSetValueModel<T> valueModel) {
        if (!preInitializeViews) {
            return wrapAsZeroSizeDataSetUntilPropertyChangeEvent(valueModel);
        } else {
            return valueModel;
        }
    }

    public void observeChangesOnTheValueModel() {
	dataSetValueModel.addPropertyChangeListener(new PresentationModelPropertyChangeListener() {
	    @Override
	    public void propertyChanged() {
		propertyChangeEventOccurred = true;
		notifyDataSetChanged();
	    }
	});
    }

    private DataSetValueModel<T> wrapAsZeroSizeDataSetUntilPropertyChangeEvent(final DataSetValueModel<T> valueModel) {
	return new DataSetValueModelWrapper<T>(valueModel) {
	    @Override
	    public int size() {
		if (propertyChangeEventOccurred)
		    return valueModel.size();

		return 0;
	    }
	};
    }

    @Override
    public int getCount() {
	if (dataSetValueModel == null)
	    return 0;

	return dataSetValueModel.size();
    }

    @Override
    public T getItem(int position) {
	return dataSetValueModel.getItem(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	return createViewFromResource(position, convertView, parent, ViewType.ITEM_LAYOUT);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
	return createViewFromResource(position, convertView, parent, ViewType.DROPDOWN_LAYOUT);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, ViewType viewType) {
	View view;
	if (convertView == null) {
	    view = newView(position, parent, viewType);
	} else {
	    view = convertView;
	}
	updateItemPresentationModel(view, position);

	return view;
    }

    private View newView(int position, ViewGroup parent, ViewType viewType) {
	ItemPresentationModel<T> itemPresentationModel = dataSetValueModel.newItemPresentationModel();
	View view;
	if (viewType == ViewType.ITEM_LAYOUT) {
	    view = itemLayoutBinder.inflateAndBindTo(itemPresentationModel);
	} else {
	    view = dropdownLayoutBinder.inflateAndBindTo(itemPresentationModel);
	}
	view.setTag(itemPresentationModel);
	return view;
    }

    private void updateItemPresentationModel(View view, int position) {
	@SuppressWarnings("unchecked")
	ItemPresentationModel<T> itemPresentationModel = (ItemPresentationModel<T>) view.getTag();
	itemPresentationModel.updateData(position, getItem(position));
    }
}