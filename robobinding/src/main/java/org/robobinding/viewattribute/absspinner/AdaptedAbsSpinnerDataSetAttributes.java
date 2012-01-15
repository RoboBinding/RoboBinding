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

import org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes;
import org.robobinding.viewattribute.adapterview.DropdownLayoutAttribute;
import org.robobinding.viewattribute.adapterview.DropdownMappingAttribute;

import android.widget.AbsSpinner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdaptedAbsSpinnerDataSetAttributes extends AbstractAdaptedDataSetAttributes<AbsSpinner>
{
	public static final String DROPDOWN_LAYOUT = "dropdownLayout";
	public static final String DROPDOWN_MAPPING = "dropdownMapping";
	
	@Override
	protected void initializeChildViewAttributes()
	{
		super.initializeChildViewAttributes();
		
		if (groupedAttributeDetails.hasAttribute(DROPDOWN_LAYOUT))
			childViewAttributes.add(new DropdownLayoutAttribute(view, groupedAttributeDetails.attributeValueFor(DROPDOWN_LAYOUT)));
		
		if (groupedAttributeDetails.hasAttribute(DROPDOWN_MAPPING))
			childViewAttributes.add(new DropdownMappingAttribute(groupedAttributeDetails.attributeValueFor(DROPDOWN_MAPPING)));
	}
	
	protected void validateAttributes()
	{
		super.validateAttributes();
		
		assertAttributesArePresent(DROPDOWN_LAYOUT);
	}
}
