package org.robobinding.viewattribute.impl;

import java.util.Map;

import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class InitailizedBindingAttributeMappingsImpl<ViewType> implements InitailizedBindingAttributeMappings<ViewType> {
	private final Map<String, PropertyViewAttributeFactory<ViewType>> propertyViewAttributeMappings;
	private final Map<String, MultiTypePropertyViewAttributeFactory<ViewType>> multiTypePropertyViewAttributeMappings;
	private final Map<String, EventViewAttributeFactory<ViewType>> eventViewAttributeMappings;
	private final Map<String[], GroupedViewAttributeFactory<ViewType>> groupedViewAttributeMappings;

	public InitailizedBindingAttributeMappingsImpl(Map<String, PropertyViewAttributeFactory<ViewType>> propertyViewAttributeMappings,
			Map<String, MultiTypePropertyViewAttributeFactory<ViewType>> multiTypePropertyViewAttributeMappings,
			Map<String, EventViewAttributeFactory<ViewType>> eventViewAttributeMappings,
			Map<String[], GroupedViewAttributeFactory<ViewType>> groupedViewAttributeMappings) {
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
	public PropertyViewAttributeFactory<ViewType> getPropertyViewAttributeFactory(String attribute) {
		return propertyViewAttributeMappings.get(attribute);
	}

	@Override
	public MultiTypePropertyViewAttributeFactory<ViewType> getMultiTypePropertyViewAttributeFactory(String attribute) {
		return multiTypePropertyViewAttributeMappings.get(attribute);
	}

	@Override
	public EventViewAttributeFactory<ViewType> getEventViewAttributeFactory(String attribute) {
		return eventViewAttributeMappings.get(attribute);
	}

	@Override
	public GroupedViewAttributeFactory<ViewType> getGroupedViewAttributeFactory(String[] attributeGroup) {
		return groupedViewAttributeMappings.get(attributeGroup);
	}
}
