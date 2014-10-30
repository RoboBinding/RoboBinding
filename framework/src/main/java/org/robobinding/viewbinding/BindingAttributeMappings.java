package org.robobinding.viewbinding;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappings<ViewType> {
	void mapOneWayProperty(Class<? extends OneWayPropertyViewAttribute<ViewType, ?>> viewAttributeClass, String attributeName);

	void mapOneWayProperty(OneWayPropertyViewAttributeFactory<ViewType> factory, String attributeName);
	
	void mapTwoWayProperty(Class<? extends TwoWayPropertyViewAttribute<ViewType, ?, ?>> viewAttributeClass, String attributeName);

	void mapTwoWayProperty(TwoWayPropertyViewAttributeFactory<ViewType> factory, String attributeName);

	void mapOneWayMultiTypeProperty(Class<? extends OneWayMultiTypePropertyViewAttribute<ViewType>> viewAttributeClass, String attributeName);

	void mapOneWayMultiTypeProperty(OneWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName);

	void mapTwoWayMultiTypeProperty(Class<? extends TwoWayMultiTypePropertyViewAttribute<ViewType>> viewAttributeClass, String attributeName);

	void mapTwoWayMultiTypeProperty(TwoWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName);

	void mapEvent(Class<? extends EventViewAttribute<ViewType, ? extends ViewAddOn>> viewAttributeClass, String attributeName);

	void mapEvent(EventViewAttributeFactory<ViewType> factory, String attributeName);

	void mapGroupedAttribute(Class<? extends GroupedViewAttribute<ViewType>> viewAttributeClass, String... attributeGroup);

	void mapGroupedAttribute(GroupedViewAttributeFactory<ViewType> factory, String... attributeGroup);
}
