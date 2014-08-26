package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface InitailizedBindingAttributeMappings<ViewType> {
    Iterable<String> getPropertyAttributes();
    Iterable<String> getMultiTypePropertyAttributes();
    Iterable<String> getEventAttributes();
    Iterable<String[]> getAttributeGroups();

    PropertyViewAttributeFactory<ViewType> getPropertyViewAttributeFactory(String attribute);
    MultiTypePropertyViewAttributeFactory<ViewType> getMultiTypePropertyViewAttributeFactory(String attribute);
    EventViewAttributeFactory<ViewType> getEventViewAttributeFactory(String attribute);
    GroupedViewAttributeFactory<ViewType> getGroupedViewAttributeFactory(String[] attributeGroup);
}
