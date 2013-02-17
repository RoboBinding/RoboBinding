/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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

import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.viewattribute.adapterview.AbstractAdaptedDataSetAttributes.SOURCE;
import static org.robobinding.viewattribute.adapterview.EmptyViewAttributes.EMPTY_VIEW_LAYOUT;
import static org.robobinding.viewattribute.adapterview.EmptyViewAttributes.EMPTY_VIEW_PRESENTATION_MODEL;
import static org.robobinding.viewattribute.adapterview.EmptyViewAttributes.EMPTY_VIEW_VISIBILITY;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.AdapterView;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdapterViewAttributeMapper implements BindingAttributeMapper<AdapterView<?>>
{
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<AdapterView<?>> mappings)
	{
		mappings.mapGroupedAttribute(AdaptedDataSetAttributes.class, SOURCE, 
				ITEM_LAYOUT, ITEM_MAPPING);
		
		mappings.mapGroupedAttribute(EmptyViewAttributes.class, EMPTY_VIEW_LAYOUT, EMPTY_VIEW_PRESENTATION_MODEL,
				EMPTY_VIEW_VISIBILITY);
		
		mappings.mapCommandAttribute(OnItemClickAttribute.class, "onItemClick");
		mappings.mapCommandAttribute(OnItemSelectedAttribute.class, "onItemSelected");
		
		mappings.mapPropertyAttribute(SelectedItemPositionAttribute.class, "selectedItemPosition");
	}
}
