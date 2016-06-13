package org.robobinding.viewbinding;

import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.eventViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.groupedViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.oneWayMultiTypePropertyViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.oneWayPropertyViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.twoWayMultiTypePropertyViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.twoWayPropertyViewAttributeFactoryForClass;

import java.util.Map;

import org.robobinding.util.Maps;
import org.robobinding.util.Preconditions;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinderFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingAttributeMappingsImpl<ViewType> implements BindingAttributeMappingsWithCreate<ViewType> {
	private final ViewAttributeBinderFactory viewAttributeBinderFactory;
	
	private final Map<String, PropertyViewAttributeBinderFactory> propertyViewAttributeMappings;
	private final Map<String, MultiTypePropertyViewAttributeBinderFactory> multiTypePropertyViewAttributeMappings;
	private final Map<String, EventViewAttributeBinderFactory> eventViewAttributeMappings;
	private final Map<String[], GroupedViewAttributeBinderFactory> groupedViewAttributeMappings;

	public BindingAttributeMappingsImpl(ViewAttributeBinderFactory viewAttributeBinderFactory) {
		this.viewAttributeBinderFactory = viewAttributeBinderFactory;
		
		propertyViewAttributeMappings = Maps.newHashMap();
		multiTypePropertyViewAttributeMappings = Maps.newHashMap();
		eventViewAttributeMappings = Maps.newHashMap();
		groupedViewAttributeMappings = Maps.newHashMap();
	}

	@Override
	public void mapOneWayProperty(Class<? extends OneWayPropertyViewAttribute<ViewType, ?>> viewAttributeClass, String attributeName) {
		checkViewAttributeClassNotNull(viewAttributeClass);
		addPropertyViewAttributeMapping(oneWayPropertyViewAttributeFactoryForClass(viewAttributeClass), attributeName);
	}

	private void addPropertyViewAttributeMapping(OneWayPropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		checkAttributeNameNotEmpty(attributeName);
		propertyViewAttributeMappings.put(attributeName, viewAttributeBinderFactory.binderFactoryFor(factory));
	}
	
	private void checkViewAttributeClassNotNull(Class<?> viewAttributeClass) {
		Preconditions.checkNotNull(viewAttributeClass, "viewAttributeClass cannot be null");
	}

	@Override
	public void mapOneWayProperty(OneWayPropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		checkFactoryNotNull(factory);
		addPropertyViewAttributeMapping(factory, attributeName);
	}
	
	private void checkFactoryNotNull(Object factory) {
		Preconditions.checkNotNull(factory, "factory cannot be null");
	}

	private void checkAttributeNameNotEmpty(String attributeName) {
		org.robobinding.util.Preconditions.checkNotBlank(attributeName, "attributeName cannot be empty");
	}
	
	@Override
	public void mapTwoWayProperty(Class<? extends TwoWayPropertyViewAttribute<ViewType, ?, ?>> viewAttributeClass, String attributeName) {
		checkViewAttributeClassNotNull(viewAttributeClass);
		addPropertyViewAttributeMapping(twoWayPropertyViewAttributeFactoryForClass(viewAttributeClass), attributeName);
	}

	private void addPropertyViewAttributeMapping(TwoWayPropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		checkAttributeNameNotEmpty(attributeName);
		propertyViewAttributeMappings.put(attributeName, viewAttributeBinderFactory.binderFactoryFor(factory));
	}
	
	@Override
	public void mapTwoWayProperty(TwoWayPropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		checkFactoryNotNull(factory);
		addPropertyViewAttributeMapping(factory, attributeName);
	}

	@Override
	public void mapOneWayMultiTypeProperty(Class<? extends OneWayMultiTypePropertyViewAttribute<ViewType>> viewAttributeClass, String attributeName) {
		checkViewAttributeClassNotNull(viewAttributeClass);
		addMultiTypePropertyViewAttributeMapping(oneWayMultiTypePropertyViewAttributeFactoryForClass(viewAttributeClass), attributeName);
	}

	@Override
	public void mapOneWayMultiTypeProperty(OneWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		checkFactoryNotNull(factory);
		addMultiTypePropertyViewAttributeMapping(factory, attributeName);
	}

	private void addMultiTypePropertyViewAttributeMapping(OneWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		addMultiTypePropertyViewAttributeMapping(viewAttributeBinderFactory.binderFactoryFor(factory), attributeName);
	}

	private void addMultiTypePropertyViewAttributeMapping(MultiTypePropertyViewAttributeBinderFactory factory,
			String attributeName) {
		checkAttributeNameNotEmpty(attributeName);
		multiTypePropertyViewAttributeMappings.put(attributeName, factory);
	}
	
	@Override
	public void mapTwoWayMultiTypeProperty(Class<? extends TwoWayMultiTypePropertyViewAttribute<ViewType>> viewAttributeClass, String attributeName) {
		checkViewAttributeClassNotNull(viewAttributeClass);
		addMultiTypePropertyViewAttributeMapping(twoWayMultiTypePropertyViewAttributeFactoryForClass(viewAttributeClass), attributeName);
	}

	private void addMultiTypePropertyViewAttributeMapping(TwoWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		addMultiTypePropertyViewAttributeMapping(viewAttributeBinderFactory.binderFactoryFor(factory), attributeName);
	}
	
	@Override
	public void mapTwoWayMultiTypeProperty(TwoWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		checkFactoryNotNull(factory);
		addMultiTypePropertyViewAttributeMapping(factory, attributeName);
	}

	@Override
	public void mapEvent(Class<? extends EventViewAttribute<ViewType, ? extends ViewAddOn>> viewAttributeClass, String attributeName) {
		checkViewAttributeClassNotNull(viewAttributeClass);
		addEventViewAttributeMapping(eventViewAttributeFactoryForClass(viewAttributeClass), attributeName);
	}

	@Override
	public void mapEvent(EventViewAttributeFactory<ViewType> factory, String attributeName) {
		checkFactoryNotNull(factory);
		addEventViewAttributeMapping(factory, attributeName);
	}

	private void addEventViewAttributeMapping(EventViewAttributeFactory<ViewType> factory, String attributeName) {
		checkAttributeNameNotEmpty(attributeName);
		eventViewAttributeMappings.put(attributeName, viewAttributeBinderFactory.binderFactoryFor(factory));
	}

	@Override
	public void mapGroupedAttribute(GroupedViewAttributeFactory<ViewType> factory, String... attributeNames) {
		checkFactoryNotNull(factory);
		addGroupedViewAttributeMapping(factory, attributeNames);
	}

	@Override
	public void mapGroupedAttribute(Class<? extends GroupedViewAttribute<ViewType>> viewAttributeClass, String... attributeNames) {
		checkViewAttributeClassNotNull(viewAttributeClass);
		addGroupedViewAttributeMapping(groupedViewAttributeFactoryForClass(viewAttributeClass), attributeNames);
	}

	private void addGroupedViewAttributeMapping(GroupedViewAttributeFactory<ViewType> factory, String... attributeNames) {
		org.robobinding.util.Preconditions.checkNotBlank("attributeNames cannot be empty or contain any empty attribute name", attributeNames);
		groupedViewAttributeMappings.put(attributeNames, viewAttributeBinderFactory.binderFactoryFor(factory));
	}

	@Override
	public InitailizedBindingAttributeMappings createInitailizedBindingAttributeMappings() {
		return new InitailizedBindingAttributeMappingsImpl(propertyViewAttributeMappings, multiTypePropertyViewAttributeMappings,
				eventViewAttributeMappings, groupedViewAttributeMappings);
	}
}
