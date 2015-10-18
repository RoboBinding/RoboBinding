package org.robobinding.supportwidget.recyclerView;

import static org.robobinding.supportwidget.recyclerView.AdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.supportwidget.recyclerView.AdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.supportwidget.recyclerView.AdaptedDataSetAttributes.SOURCE;

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
