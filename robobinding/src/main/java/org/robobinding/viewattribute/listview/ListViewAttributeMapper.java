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
package org.robobinding.viewattribute.listview;

import static org.robobinding.viewattribute.listview.FooterAttributes.FOOTER_LAYOUT;
import static org.robobinding.viewattribute.listview.FooterAttributes.FOOTER_PRESENTATION_MODEL;
import static org.robobinding.viewattribute.listview.FooterAttributes.FOOTER_VISIBILITY;
import static org.robobinding.viewattribute.listview.HeaderAttributes.HEADER_LAYOUT;
import static org.robobinding.viewattribute.listview.HeaderAttributes.HEADER_PRESENTATION_MODEL;
import static org.robobinding.viewattribute.listview.HeaderAttributes.HEADER_VISIBILITY;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ListViewAttributeMapper implements BindingAttributeMapper<ListView> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<ListView> mappings) {
	mappings.mapPropertyAttribute(CheckedItemPositionAttribute.class, "checkedItemPosition");
	mappings.mapPropertyAttribute(CheckedItemPositionsAttribute.class, "checkedItemPositions");
	mappings.mapPropertyAttribute(ChoiceModeAttribute.class, "choiceMode");
	

	mappings.mapGroupedAttribute(new HeaderAttributesFactory(), HEADER_LAYOUT, HEADER_PRESENTATION_MODEL, HEADER_VISIBILITY);
	mappings.mapGroupedAttribute(new FooterAttributesFactory(), FOOTER_LAYOUT, FOOTER_PRESENTATION_MODEL, FOOTER_VISIBILITY);
	
	mappings.mapCommandAttribute(OnScrollAttribute.class, "onScroll");
	mappings.mapCommandAttribute(OnScrollStateChangedAttribute.class, "onScrollStateChanged");
    }
}
