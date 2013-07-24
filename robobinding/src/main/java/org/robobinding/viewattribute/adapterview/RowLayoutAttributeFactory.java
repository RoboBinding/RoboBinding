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
 * @author Cheng Wei
 */
public abstract class RowLayoutAttributeFactory {
    private final AdapterView<?> adapterView;
    private final DataSetAdapterBuilder dataSetAdapterBuilder;

    protected RowLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
	this.adapterView = adapterView;
	this.dataSetAdapterBuilder = dataSetAdapterBuilder;
    }

    public ViewAttribute createRowLayoutAttribute(AbstractPropertyAttribute attribute) {
	RowLayoutUpdater rowLayoutUpdater = createRowLayoutUpdater(dataSetAdapterBuilder);
	if (attribute.isStaticResource()) {
	    return createStaticLayoutAttribute(attribute, rowLayoutUpdater);
	} else {
	    return createDynamicLayoutAttribute(attribute, rowLayoutUpdater);
	}
    }

    private ViewAttribute createStaticLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	return new StaticLayoutAttribute(attribute.asStaticResourceAttribute(), rowLayoutUpdater);
    }

    private ViewAttribute createDynamicLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	PropertyViewAttributeConfig<AdapterView<?>> attributeConfig = new PropertyViewAttributeConfig<AdapterView<?>>(adapterView,
		attribute.asValueModelAttribute());
	DataSetAdapterUpdater dataSetAdapterUpdater = new DataSetAdapterUpdater(dataSetAdapterBuilder, adapterView);
	return new DynamicLayoutAttribute(attributeConfig, rowLayoutUpdater, dataSetAdapterUpdater);
    }

    protected abstract RowLayoutUpdater createRowLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder);
}
