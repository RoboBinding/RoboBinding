package org.robobinding.binder;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinder;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;
import org.robobinding.viewbinding.InitailizedBindingAttributeMappings;

import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockInitializedBindingAttributeMappingsBuilder {
	private final List<String> propertyAttributes;
	private final List<String> multiTypePropertyAttributes;
	private final List<String> eventAttributes;
	private final List<String[]> attributeGroups;
	private final Map<String, PropertyViewAttributeBinderFactory> propertyViewAttributeBinderFactoryMap;
	private final Map<String, MultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeBinderFactoryMap;
	private final Map<String, EventViewAttributeBinderFactory> eventViewAttributeBinderFactoryMap;
	private final Map<String[], GroupedViewAttributeBinderFactory> groupedViewAttributeBinderFactoryMap;	

	private MockInitializedBindingAttributeMappingsBuilder() {
		propertyAttributes = newArrayList();
		multiTypePropertyAttributes = newArrayList();
		eventAttributes = newArrayList();
		attributeGroups = newArrayList();
		
		propertyViewAttributeBinderFactoryMap = Maps.newHashMap();
		multiTypePropertyViewAttributeBinderFactoryMap = Maps.newHashMap();
		eventViewAttributeBinderFactoryMap = Maps.newHashMap();
		groupedViewAttributeBinderFactoryMap = Maps.newHashMap();
	}

	public static MockInitializedBindingAttributeMappingsBuilder aBindingAttributeMappings() {
		return new MockInitializedBindingAttributeMappingsBuilder();
	}

	public MockInitializedBindingAttributeMappingsBuilder withPropertyAttribute(String attribute, 
			final PropertyViewAttributeBinder viewAttributeBinder) {
		propertyAttributes.add(attribute);
		propertyViewAttributeBinderFactoryMap.put(attribute, new PropertyViewAttributeBinderFactory(null, null) {
			@Override
			public PropertyViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
				return viewAttributeBinder;
			}

			@Override
			public PropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
				return viewAttributeBinder;
			}
		});
		return this;
	}

	public MockInitializedBindingAttributeMappingsBuilder withMultiTypePropertyAttribute(String attribute, 
			final MultiTypePropertyViewAttributeBinder viewAttributeBinder) {
		multiTypePropertyAttributes.add(attribute);
		multiTypePropertyViewAttributeBinderFactoryMap.put(attribute, new MultiTypePropertyViewAttributeBinderFactory(null, null) {
			
			@Override
			public MultiTypePropertyViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
				return viewAttributeBinder;
			}
			
			@Override
			public MultiTypePropertyViewAttributeBinder create(Object view, ValueModelAttribute attribute) {
				return viewAttributeBinder;
			}
		});
		return this;
	}

	public MockInitializedBindingAttributeMappingsBuilder withEventAttribute(String attribute, 
			final EventViewAttributeBinder viewAttributeBinder) {
		eventAttributes.add(attribute);
		eventViewAttributeBinderFactoryMap.put(attribute, new EventViewAttributeBinderFactory(null, null) {
			@Override
			public EventViewAttributeBinder create(Object view, String attributeName, String attributeValue) {
				return viewAttributeBinder;
			}
		});
		return this;
	}

	public MockInitializedBindingAttributeMappingsBuilder withAttributeGroup(String[] attributeGroup,
			final GroupedViewAttributeBinder viewAttributeBinder) {
		attributeGroups.add(attributeGroup);
		groupedViewAttributeBinderFactoryMap.put(attributeGroup, new GroupedViewAttributeBinderFactory(null, null, null) {
			@Override
			public GroupedViewAttributeBinder create(Object view, Map<String, String> presentAttributeMappings) {
				return viewAttributeBinder;
			}
		});
		return this;
	}

	public InitailizedBindingAttributeMappings build() {
		return new MockInitailizedBindingAttributeMappings(this);
	}

	private static class MockInitailizedBindingAttributeMappings implements InitailizedBindingAttributeMappings {
		private final List<String> propertyAttributes;
		private final List<String> multiTypePropertyAttributes;
		private final List<String> eventAttributes;
		private final List<String[]> attributeGroups;
		private final Map<String, PropertyViewAttributeBinderFactory> propertyViewAttributeBinderFactoryMap;
		private final Map<String, MultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeBinderFactoryMap;
		private final Map<String, EventViewAttributeBinderFactory> eventViewAttributeBinderFactoryMap;
		private final Map<String[], GroupedViewAttributeBinderFactory> groupedViewAttributeBinderFactoryMap;	

		private MockInitailizedBindingAttributeMappings(MockInitializedBindingAttributeMappingsBuilder builder) {
			propertyAttributes = newArrayList(builder.propertyAttributes);
			multiTypePropertyAttributes = newArrayList(builder.multiTypePropertyAttributes);
			eventAttributes = newArrayList(builder.eventAttributes);
			attributeGroups = newArrayList(builder.attributeGroups);
			
			propertyViewAttributeBinderFactoryMap = Maps.newHashMap(builder.propertyViewAttributeBinderFactoryMap);
			multiTypePropertyViewAttributeBinderFactoryMap = Maps.newHashMap(builder.multiTypePropertyViewAttributeBinderFactoryMap);
			eventViewAttributeBinderFactoryMap = Maps.newHashMap(builder.eventViewAttributeBinderFactoryMap);
			groupedViewAttributeBinderFactoryMap = Maps.newHashMap(builder.groupedViewAttributeBinderFactoryMap);
		}

		@Override
		public Iterable<String> getPropertyAttributes() {
			return propertyAttributes;
		}

		@Override
		public Iterable<String> getMultiTypePropertyAttributes() {
			return multiTypePropertyAttributes;
		}

		@Override
		public Iterable<String> getEventAttributes() {
			return eventAttributes;
		}

		@Override
		public Iterable<String[]> getAttributeGroups() {
			return attributeGroups;
		}

		@Override
		public PropertyViewAttributeBinderFactory getPropertyViewAttributeFactory(String attribute) {
			return propertyViewAttributeBinderFactoryMap.get(attribute);
		}

		@Override
		public MultiTypePropertyViewAttributeBinderFactory getMultiTypePropertyViewAttributeFactory(String attribute) {
			return multiTypePropertyViewAttributeBinderFactoryMap.get(attribute);
		}

		@Override
		public EventViewAttributeBinderFactory getEventViewAttributeFactory(String attribute) {
			return eventViewAttributeBinderFactoryMap.get(attribute);
		}

		@Override
		public GroupedViewAttributeBinderFactory getGroupedViewAttributeFactory(String[] attributeGroup) {
			return groupedViewAttributeBinderFactoryMap.get(attributeGroup);
		}
	}
}
