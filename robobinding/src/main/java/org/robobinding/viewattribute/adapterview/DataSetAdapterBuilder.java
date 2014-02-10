/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;

import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.property.DataSetValueModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetAdapterBuilder {
    private final BindingContext bindingContext;

    private int itemLayoutId;
    private int dropDownLayoutId;
    private Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup;
    private Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup;
    private DataSetValueModel<?> valueModel;

    public DataSetAdapterBuilder(BindingContext bindingContext) {
	this.bindingContext = bindingContext;
	this.itemPredefinedPendingAttributesForViewGroup = newArrayList();
	this.dropdownPredefinedPendingAttributesForViewGroup = newArrayList();
    }

    public void setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }

    public void setDropDownLayoutId(int dropDownLayoutId) {
        this.dropDownLayoutId = dropDownLayoutId;
    }

    public void setItemPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup) {
	if (itemPredefinedPendingAttributesForViewGroup != null) {
	    this.itemPredefinedPendingAttributesForViewGroup = itemPredefinedPendingAttributesForViewGroup;
	}
    }

    public void setDropdownPredefinedPendingAttributesForViewGroup(
    	Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup) {
	if (dropdownPredefinedPendingAttributesForViewGroup != null) {
	    this.dropdownPredefinedPendingAttributesForViewGroup = dropdownPredefinedPendingAttributesForViewGroup;
	}
    }

    public void setValueModel(DataSetValueModel<?> valueModel) {
        this.valueModel = valueModel;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DataSetAdapter<?> build() {
	ItemBinder itemBinder = bindingContext.createItemBinder();
	ItemLayoutBinder itemLayoutBinder = new ItemLayoutBinder(itemBinder, itemLayoutId, itemPredefinedPendingAttributesForViewGroup);
	ItemLayoutBinder dropdownLayoutBinder = new ItemLayoutBinder(itemBinder, dropDownLayoutId, dropdownPredefinedPendingAttributesForViewGroup);
	DataSetAdapter<?> dataSetAdapter = new DataSetAdapter(valueModel, itemLayoutBinder, dropdownLayoutBinder, bindingContext.shouldPreInitializeViews());

	dataSetAdapter.observeChangesOnTheValueModel();
	return dataSetAdapter;
    }
}
