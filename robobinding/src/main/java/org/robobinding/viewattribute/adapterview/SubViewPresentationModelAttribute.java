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
import org.robobinding.ViewBinder;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.ChildViewAttributeWithAttribute;

import android.view.View;
/**
*
* @since 1.0
* @version $Revision: 1.0 $
* @author Robert Taylor
* @author Cheng Wei
*/
class SubViewPresentationModelAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
    private final int layoutId;
    private final SubViewHolder subViewHolder;
    private ValueModelAttribute attribute;

    public SubViewPresentationModelAttribute(int layoutId, SubViewHolder subViewHolder) {
	this.layoutId = layoutId;
	this.subViewHolder = subViewHolder;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
        ViewBinder viewBinder = bindingContext.createViewBinder();
        View subView = viewBinder.inflateAndBind(layoutId, attribute.getPropertyName());
        subViewHolder.setSubView(subView);
    }

    @Override
    public void setAttribute(ValueModelAttribute attribute) {
        this.attribute = attribute;
    }
}