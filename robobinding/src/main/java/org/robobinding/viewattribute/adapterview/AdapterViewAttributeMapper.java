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
public class AdapterViewAttributeMapper implements BindingAttributeMapper<AdapterView<?>> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<AdapterView<?>> mappings) {
	mappings.mapGroupedAttribute(AdaptedDataSetAttributes.class, SOURCE, ITEM_LAYOUT, ITEM_MAPPING);

	mappings.mapGroupedAttribute(new EmptyViewAttributesFactory(), EMPTY_VIEW_LAYOUT, EMPTY_VIEW_PRESENTATION_MODEL, EMPTY_VIEW_VISIBILITY);

	mappings.mapCommandAttribute(OnItemClickAttribute.class, "onItemClick");
	mappings.mapCommandAttribute(OnItemSelectedAttribute.class, "onItemSelected");

	mappings.mapPropertyAttribute(SelectedItemPositionAttribute.class, "selectedItemPosition");
    }
}
