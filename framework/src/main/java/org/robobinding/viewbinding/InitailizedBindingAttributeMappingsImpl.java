package org.robobinding.viewbinding;

import java.util.Map;

import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.AbstractMultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeBinderFactory;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class InitailizedBindingAttributeMappingsImpl implements InitailizedBindingAttributeMappings {
	private final Map<String, AbstractPropertyViewAttributeBinderFactory> propertyViewAttributeMappings;
	private final Map<String, AbstractMultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeMappings;
	private final Map<String, EventViewAttributeBinderFactory> eventViewAttributeMappings;
	private final Map<String[], GroupedViewAttributeBinderFactory> groupedViewAttributeMappings;

	public InitailizedBindingAttributeMappingsImpl(Map<String, AbstractPropertyViewAttributeBinderFactory> propertyViewAttributeMappings,
			Map<String, AbstractMultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeMappings,
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
	public AbstractPropertyViewAttributeBinderFactory getPropertyViewAttributeFactory(String attribute) {
		return propertyViewAttributeMappings.get(attribute);
	}

	@Override
	public AbstractMultiTypePropertyViewAttributeBinderFactory getMultiTypePropertyViewAttributeFactory(String attribute) {
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
