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

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class DynamicLayoutAttribute extends AbstractReadOnlyPropertyViewAttribute<AdapterView, Integer> {
    private DataSetAdapter<?> dataSetAdapter;
    private RowLayoutUpdater rowLayoutUpdater;

    public DynamicLayoutAttribute(PropertyViewAttributeConfig<AdapterView> config, DataSetAdapter<?> dataSetAdapter, 
	    RowLayoutUpdater rowLayoutUpdater) {
	super.initialize(config);
	this.dataSetAdapter = dataSetAdapter;
	this.rowLayoutUpdater = rowLayoutUpdater;
    }

    @Override
    protected void valueModelUpdated(Integer newItemLayoutId) {
	rowLayoutUpdater.updateRowLayout(newItemLayoutId);
	((AdapterView) view).setAdapter(dataSetAdapter);
    }
}