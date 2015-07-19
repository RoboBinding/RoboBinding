package org.robobinding.widget.adapterview;

import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.SOURCE;
import static org.robobinding.widget.adapterview.EmptyViewAttributes.EMPTY_VIEW_LAYOUT;
import static org.robobinding.widget.adapterview.EmptyViewAttributes.EMPTY_VIEW_PRESENTATION_MODEL;
import static org.robobinding.widget.adapterview.EmptyViewAttributes.EMPTY_VIEW_VISIBILITY;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdapterViewBinding implements ViewBinding<AdapterView<?>> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<AdapterView<?>> mappings) {
		mappings.mapTwoWayProperty(SelectedItemPositionAttribute.class, "selectedItemPosition");

		mappings.mapEvent(OnItemClickAttribute.class, "onItemClick");
		mappings.mapEvent(OnItemSelectedAttribute.class, "onItemSelected");

		mappings.mapGroupedAttribute(AdaptedDataSetAttributes.class, SOURCE, ITEM_LAYOUT, ITEM_MAPPING);
		mappings.mapGroupedAttribute(new EmptyViewAttributesFactory(), EMPTY_VIEW_LAYOUT, EMPTY_VIEW_PRESENTATION_MODEL, EMPTY_VIEW_VISIBILITY);
	}
}
