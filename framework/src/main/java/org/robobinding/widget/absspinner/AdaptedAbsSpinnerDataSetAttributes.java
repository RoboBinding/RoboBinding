package org.robobinding.widget.absspinner;

import static org.robobinding.attribute.ChildAttributeResolvers.predefinedMappingsAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.propertyAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.viewattribute.grouped.ChildViewAttributesBuilder;
import org.robobinding.widget.adapterview.AbstractAdaptedDataSetAttributes;
import org.robobinding.widget.adapterview.DropdownLayoutAttributeFactory;
import org.robobinding.widget.adapterview.DropdownMappingUpdater;
import org.robobinding.widget.adapterview.ItemMappingAttribute;
import org.robobinding.widget.adapterview.RowLayoutAttributeAdapter;

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
    public void setupChildViewAttributes(AbsSpinner view, ChildViewAttributesBuilder<AbsSpinner> childViewAttributes, BindingContext bindingContext) {
	super.setupChildViewAttributes(view, childViewAttributes, bindingContext);

	if (childViewAttributes.hasAttribute(DROPDOWN_LAYOUT))
	    childViewAttributes.add(DROPDOWN_LAYOUT, new RowLayoutAttributeAdapter(new DropdownLayoutAttributeFactory(view, dataSetAdapterBuilder)));

	if (childViewAttributes.hasAttribute(DROPDOWN_MAPPING))
	    childViewAttributes.add(DROPDOWN_MAPPING, new ItemMappingAttribute(new DropdownMappingUpdater(dataSetAdapterBuilder)));
    }
}
