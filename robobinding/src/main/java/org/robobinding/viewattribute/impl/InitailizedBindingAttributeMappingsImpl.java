package org.robobinding.viewattribute.impl;

import java.util.Map;

import org.robobinding.internal.guava.Maps;
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
public class InitailizedBindingAttributeMappingsImpl<T extends View> implements InitailizedBindingAttributeMappings<T> {
    private final Map<String, PropertyViewAttributeFactory<T>> propertyViewAttributeMappings;
    private final Map<String, MultiTypePropertyViewAttributeFactory<T>> multiTypePropertyViewAttributeMappings;
    private final Map<String, EventViewAttributeFactory<T>> eventViewAttributeMappings;
    private final Map<String[], GroupedViewAttributeFactory<T>> groupedViewAttributeMappings;

    public InitailizedBindingAttributeMappingsImpl(Map<String, PropertyViewAttributeFactory<T>> propertyViewAttributeMappings,
            Map<String, MultiTypePropertyViewAttributeFactory<T>> multiTypePropertyViewAttributeMappings,
            Map<String, EventViewAttributeFactory<T>> eventViewAttributeMappings,
            Map<String[], GroupedViewAttributeFactory<T>> groupedViewAttributeMappings) {
        this.propertyViewAttributeMappings = Maps.newHashMap(propertyViewAttributeMappings);
        this.multiTypePropertyViewAttributeMappings = Maps.newHashMap(multiTypePropertyViewAttributeMappings);
        this.eventViewAttributeMappings = Maps.newHashMap(eventViewAttributeMappings);
        this.groupedViewAttributeMappings = Maps.newHashMap(groupedViewAttributeMappings);
    }

    @Override
    public Iterable<String> getPropertyAttributes() {
	return propertyViewAttributeMappings.keySet();
    }

    @Override
    public Iterable<String> getMultiTypePropertyAttributes() {
        return multiTypePropertyViewAttributeMappings.keySet();
    }

    @Override
    public Iterable<String> getEventAttributes() {
	return eventViewAttributeMappings.keySet();
    }

    @Override
    public Iterable<String[]> getAttributeGroups() {
	return groupedViewAttributeMappings.keySet();
    }

    @Override
    public PropertyViewAttributeFactory<T> getPropertyViewAttributeFactory(String attribute) {
        return propertyViewAttributeMappings.get(attribute);
    }

    @Override
    public MultiTypePropertyViewAttributeFactory<T> getMultiTypePropertyViewAttributeFactory(String attribute) {
        return multiTypePropertyViewAttributeMappings.get(attribute);
    }

    @Override
    public EventViewAttributeFactory<T> getEventViewAttributeFactory(String attribute) {
        return eventViewAttributeMappings.get(attribute);
    }

    @Override
    public GroupedViewAttributeFactory<T> getGroupedViewAttributeFactory(String[] attributeGroup) {
        return groupedViewAttributeMappings.get(attributeGroup);
    }
}
