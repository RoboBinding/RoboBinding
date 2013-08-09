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

import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.viewattribute.ViewAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class StaticLayoutAttribute implements ViewAttribute {
    private final StaticResourceAttribute attributeValue;
    private final RowLayoutUpdater rowLayoutUpdater;

    public StaticLayoutAttribute(StaticResourceAttribute attributeValue, RowLayoutUpdater rowLayoutUpdater) {
	this.attributeValue = attributeValue;
	this.rowLayoutUpdater = rowLayoutUpdater;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	int itemLayoutId = attributeValue.getResourceId(bindingContext.getContext());
	rowLayoutUpdater.updateRowLayout(itemLayoutId);
    }

    @Override
    public void preInitializeView(BindingContext bindingContext) {
    }
}