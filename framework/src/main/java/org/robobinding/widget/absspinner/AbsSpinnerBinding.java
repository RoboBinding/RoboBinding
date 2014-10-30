package org.robobinding.widget.absspinner;

import static org.robobinding.widget.absspinner.AdaptedAbsSpinnerDataSetAttributes.DROPDOWN_LAYOUT;
import static org.robobinding.widget.absspinner.AdaptedAbsSpinnerDataSetAttributes.DROPDOWN_MAPPING;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_LAYOUT;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.ITEM_MAPPING;
import static org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes.SOURCE;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.AbsSpinner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AbsSpinnerBinding implements ViewBinding<AbsSpinner> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<AbsSpinner> mappings) {
		mappings.mapGroupedAttribute(AdaptedAbsSpinnerDataSetAttributes.class, SOURCE, ITEM_LAYOUT, ITEM_MAPPING, DROPDOWN_LAYOUT, DROPDOWN_MAPPING);
	}
}
