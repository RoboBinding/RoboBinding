package org.robobinding.viewattribute;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappings<ViewType> {
	void mapProperty(Class<? extends PropertyViewAttribute<ViewType, ?>> propertyViewAttributeClass, String attributeName);

	void mapProperty(PropertyViewAttributeFactory<ViewType> propertyViewAttributeFactory, String attributeName);

	void mapMultiTypeProperty(Class<? extends MultiTypePropertyViewAttribute<ViewType>> multiTypePropertyViewAttributeClass, String attributeName);

	void mapMultiTypeProperty(MultiTypePropertyViewAttributeFactory<ViewType> multiTypePropertyViewAttributeFactory, String attributeName);

	void mapEvent(Class<? extends EventViewAttribute<ViewType>> eventViewAttributeClass, String attributeName);

	void mapEvent(EventViewAttributeFactory<ViewType> eventViewAttributeFactory, String attributeName);

	void mapGroupedAttribute(Class<? extends GroupedViewAttribute<ViewType>> groupedViewAttributeClass, String... attributeGroup);

	void mapGroupedAttribute(GroupedViewAttributeFactory<ViewType> groupedViewAttributeFactory, String... attributeGroup);
}
