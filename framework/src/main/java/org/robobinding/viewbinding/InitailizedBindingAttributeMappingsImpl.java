package org.robobinding.viewbinding;

import java.util.Map;

import org.robobinding.util.Maps;
import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class InitailizedBindingAttributeMappingsImpl implements InitailizedBindingAttributeMappings {
	private final Map<String, PropertyViewAttributeBinderFactory> propertyViewAttributeMappings;
	private final Map<String, MultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeMappings;
	private final Map<String, EventViewAttributeBinderFactory> eventViewAttributeMappings;
	private final Map<String[], GroupedViewAttributeBinderFactory> groupedViewAttributeMappings;

	public InitailizedBindingAttributeMappingsImpl(Map<String, PropertyViewAttributeBinderFactory> propertyViewAttributeMappings,
			Map<String, MultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeMappings,
			Map<String, EventViewAttributeBinderFactory> eventViewAttributeMappings,
			Map<String[], GroupedViewAttributeBinderFactory> groupedViewAttributeMappings) {
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
	public PropertyViewAttributeBinderFactory getPropertyViewAttributeFactory(String attribute) {
		return propertyViewAttributeMappings.get(attribute);
	}

	@Override
	public MultiTypePropertyViewAttributeBinderFactory getMultiTypePropertyViewAttributeFactory(String attribute) {
		return multiTypePropertyViewAttributeMappings.get(attribute);
	}

	@Override
	public EventViewAttributeBinderFactory getEventViewAttributeFactory(String attribute) {
		return eventViewAttributeMappings.get(attribute);
	}

	@Override
	public GroupedViewAttributeBinderFactory getGroupedViewAttributeFactory(String[] attributeGroup) {
		return groupedViewAttributeMappings.get(attributeGroup);
	}
}
