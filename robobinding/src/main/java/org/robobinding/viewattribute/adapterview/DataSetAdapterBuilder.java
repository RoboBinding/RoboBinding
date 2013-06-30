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
    private int itemLayoutId;
    private int dropDownLayoutId;
    private Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup;
    private Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup;
    private DataSetValueModel<?> dataSetValueModel;
    
    public void setItemLayoutId(int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }
    
    public void setDropDownLayoutId(int dropDownLayoutId) {
        this.dropDownLayoutId = dropDownLayoutId;
    }
    
    public void setItemPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup) {
        this.itemPredefinedPendingAttributesForViewGroup = itemPredefinedPendingAttributesForViewGroup;
    }
    
    public void setDropdownPredefinedPendingAttributesForViewGroup(
    	Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup) {
        this.dropdownPredefinedPendingAttributesForViewGroup = dropdownPredefinedPendingAttributesForViewGroup;
    }
    
    public void setDataSetValueModel(DataSetValueModel<?> dataSetValueModel) {
        this.dataSetValueModel = dataSetValueModel;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public DataSetAdapter<?> build(BindingContext bindingContext) {
	ItemBinder itemBinder = bindingContext.createItemBinder();
	ItemLayoutBinder itemLayoutBinder = new ItemLayoutBinder(itemBinder, itemLayoutId, itemPredefinedPendingAttributesForViewGroup);
	ItemLayoutBinder dropdownLayoutBinder = new ItemLayoutBinder(itemBinder, dropDownLayoutId, dropdownPredefinedPendingAttributesForViewGroup);
	return new DataSetAdapter(dataSetValueModel, itemLayoutBinder, dropdownLayoutBinder, bindingContext.shouldPreInitializeViews());
    }
}
