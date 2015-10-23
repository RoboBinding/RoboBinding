package org.robobinding.supportwidget.recyclerview;

import static org.robobinding.supportwidget.recyclerview.AdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.supportwidget.recyclerview.AdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.supportwidget.recyclerview.AdaptedDataSetAttributes.SOURCE;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.support.v7.widget.RecyclerView;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 */
public class RecyclerViewBinding implements ViewBinding<RecyclerView> {
	
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<RecyclerView> mappings) {
		mappings.mapGroupedAttribute(AdaptedDataSetAttributes.class, SOURCE, ITEM_LAYOUT, ITEM_MAPPING);
	}
}
