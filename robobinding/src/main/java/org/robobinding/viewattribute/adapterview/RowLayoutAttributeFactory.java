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

import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;
import org.robobinding.viewattribute.ViewAttribute;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class RowLayoutAttributeFactory {
    private final AdapterView<?> adapterView;
    private final DataSetAdapter<?> dataSetAdapter;

    public RowLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapter<?> dataSetAdapter) {
	this.adapterView = adapterView;
	this.dataSetAdapter = dataSetAdapter;
    }

    public ViewAttribute createItemLayoutAttribute(AbstractPropertyAttribute attribute) {
	RowLayoutUpdater rowLayoutUpdater = new ItemLayoutUpdater(dataSetAdapter);
	return attribute.isStaticResource() ? createStaticLayoutAttribute(attribute, rowLayoutUpdater) : createDynamicLayoutAttribute(attribute,
		new ItemLayoutUpdater(dataSetAdapter));
    }

    public ViewAttribute createDropdownLayoutAttribute(AbstractPropertyAttribute attribute) {
	RowLayoutUpdater rowLayoutUpdater = new DropdownLayoutUpdater(dataSetAdapter);
	return attribute.isStaticResource() ? createStaticLayoutAttribute(attribute, rowLayoutUpdater) : createDynamicLayoutAttribute(attribute,
		rowLayoutUpdater);
    }

    private ViewAttribute createStaticLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	return new StaticLayoutAttribute(attribute.asStaticResourceAttribute(), rowLayoutUpdater);
    }

    private ViewAttribute createDynamicLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	@SuppressWarnings("rawtypes")
	PropertyViewAttributeConfig<AdapterView> attributeConfig = new PropertyViewAttributeConfig<AdapterView>(adapterView,
		attribute.asValueModelAttribute());
	return new DynamicLayoutAttribute(attributeConfig, dataSetAdapter, rowLayoutUpdater);
    }
}
