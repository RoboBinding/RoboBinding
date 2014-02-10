/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.absspinner;

import static org.robobinding.attribute.ChildAttributeResolvers.predefinedMappingsAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.propertyAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.viewattribute.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes;
import org.robobinding.viewattribute.adapterview.DropdownLayoutAttributeFactory;
import org.robobinding.viewattribute.adapterview.DropdownMappingUpdater;
import org.robobinding.viewattribute.adapterview.ItemMappingAttribute;
import org.robobinding.viewattribute.adapterview.RowLayoutAttributeAdapter;

import android.widget.AbsSpinner;

import com.google.common.collect.ObjectArrays;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdaptedAbsSpinnerDataSetAttributes extends AbstractAdaptedDataSetAttributes<AbsSpinner> {
    public static final String DROPDOWN_LAYOUT = "dropdownLayout";
    public static final String DROPDOWN_MAPPING = "dropdownMapping";

    @Override
    public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
	super.mapChildAttributeResolvers(resolverMappings);
	resolverMappings.map(propertyAttributeResolver(), DROPDOWN_LAYOUT);
	resolverMappings.map(predefinedMappingsAttributeResolver(), DROPDOWN_MAPPING);
    }

    @Override
    public String[] getCompulsoryAttributes() {
	return ObjectArrays.concat(super.getCompulsoryAttributes(), DROPDOWN_LAYOUT);
    }

    @Override
    protected void setupChildViewAttributes(ChildViewAttributesBuilder<AbsSpinner> childViewAttributes, BindingContext bindingContext) {
	super.setupChildViewAttributes(childViewAttributes, bindingContext);

	if (childViewAttributes.hasAttribute(DROPDOWN_LAYOUT))
	    childViewAttributes.add(DROPDOWN_LAYOUT, new RowLayoutAttributeAdapter(new DropdownLayoutAttributeFactory(view, dataSetAdapterBuilder)));

	if (childViewAttributes.hasAttribute(DROPDOWN_MAPPING))
	    childViewAttributes.add(DROPDOWN_MAPPING, new ItemMappingAttribute(new DropdownMappingUpdater(dataSetAdapterBuilder)));
    }
}
