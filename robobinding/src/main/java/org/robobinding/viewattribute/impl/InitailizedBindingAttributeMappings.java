package org.robobinding.viewattribute.impl;

import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface InitailizedBindingAttributeMappings<T extends View> {
    Iterable<String> getPropertyAttributes();
    Iterable<String> getMultiTypePropertyAttributes();
    Iterable<String> getEventAttributes();
    Iterable<String[]> getAttributeGroups();

    PropertyViewAttributeFactory<T> getPropertyViewAttributeFactory(String attribute);
    MultiTypePropertyViewAttributeFactory<T> getMultiTypePropertyViewAttributeFactory(String attribute);
    EventViewAttributeFactory<T> getEventViewAttributeFactory(String attribute);
    GroupedViewAttributeFactory<T> getGroupedViewAttributeFactory(String[] attributeGroup);
}
