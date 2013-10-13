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
package org.robobinding.viewattribute;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependentPropertyViewAttribute implements Bindable {
    private final ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>> factory;
    private final ValueModelAttribute attribute;
    private final ChildViewAttributeInitializer viewAttributeInitializer;

    public DependentPropertyViewAttribute(
	    ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>> factory,
	    ValueModelAttribute attribute,
	    ChildViewAttributeInitializer viewAttributeInitializer) {
	this.factory = factory;
	this.attribute = attribute;
	this.viewAttributeInitializer = viewAttributeInitializer;
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	PropertyViewAttribute<? extends View> propertyViewAttribute = factory.create();
	viewAttributeInitializer.initializePropertyViewAttribute(propertyViewAttribute, attribute);
	propertyViewAttribute.bindTo(bindingContext);
    }
}