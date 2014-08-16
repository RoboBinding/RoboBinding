package org.robobinding.viewattribute.impl;

import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.eventViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.groupedViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.multiTypePropertyViewAttributeFactoryForClass;
import static org.robobinding.viewattribute.grouped.FromClassViewAttributeFactories.propertyViewAttributeFactoryForClass;

import java.util.Map;

import org.robobinding.internal.guava.Maps;
import org.robobinding.internal.guava.Preconditions;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingAttributeMappingsImpl<T extends View> implements BindingAttributeMappingsWithCreate<T> {
    private final Map<String, PropertyViewAttributeFactory<T>> propertyViewAttributeMappings;
    private final Map<String, MultiTypePropertyViewAttributeFactory<T>> multiTypePropertyViewAttributeMappings;
    private final Map<String, EventViewAttributeFactory<T>> eventViewAttributeMappings;
    private final Map<String[], GroupedViewAttributeFactory<T>> groupedViewAttributeMappings;

    public BindingAttributeMappingsImpl() {
	propertyViewAttributeMappings = Maps.newHashMap();
	multiTypePropertyViewAttributeMappings = Maps.newHashMap();
	eventViewAttributeMappings = Maps.newHashMap();
	groupedViewAttributeMappings = Maps.newHashMap();
    }

    @Override
    public void mapProperty(Class<? extends PropertyViewAttribute<T, ?>> propertyViewAttributeClass, String attributeName) {
	Preconditions.checkNotNull(propertyViewAttributeClass, "propertyViewAttributeClass cannot be null");
	addPropertyViewAttributeMapping(propertyViewAttributeFactoryForClass(propertyViewAttributeClass), attributeName);
    }

    @Override
    public void mapProperty(PropertyViewAttributeFactory<T> propertyViewAttributeFactory,
            String attributeName) {
	Preconditions.checkNotNull(propertyViewAttributeFactory, "propertyViewAttributeFactory cannot be null");
	addPropertyViewAttributeMapping(propertyViewAttributeFactory, attributeName);
    }

    private void addPropertyViewAttributeMapping(
	    PropertyViewAttributeFactory<T> propertyViewAttributeFactory,
	    String attributeName) {
	checkAttributeNameNotEmpty(attributeName);
	propertyViewAttributeMappings.put(attributeName, propertyViewAttributeFactory);
    }

    private void checkAttributeNameNotEmpty(String attributeName) {
	org.robobinding.util.Preconditions.checkNotBlank(attributeName, "attributeName cannot be empty");
    }

    @Override
    public void mapMultiTypeProperty(Class<? extends MultiTypePropertyViewAttribute<T>> multiTypePropertyViewAttributeClass, String attributeName) {
	Preconditions.checkNotNull(multiTypePropertyViewAttributeClass, "multiTypePropertyViewAttributeClass cannot be null");
	addMultiTypePropertyViewAttributeMapping(multiTypePropertyViewAttributeFactoryForClass(multiTypePropertyViewAttributeClass), attributeName);
    }

    @Override
    public void mapMultiTypeProperty(
            MultiTypePropertyViewAttributeFactory<T> multiTypePropertyViewAttributeFactory,
            String attributeName) {
	Preconditions.checkNotNull(multiTypePropertyViewAttributeFactory, "multiTypePropertyViewAttributeFactory cannot be null");
	addMultiTypePropertyViewAttributeMapping(multiTypePropertyViewAttributeFactory, attributeName);
    }

    private void addMultiTypePropertyViewAttributeMapping(
	    MultiTypePropertyViewAttributeFactory<T> multiTypePropertyViewAttributeFactory,
	    String attributeName) {
	checkAttributeNameNotEmpty(attributeName);
	multiTypePropertyViewAttributeMappings.put(attributeName, multiTypePropertyViewAttributeFactory);
    }

    @Override
    public void mapEvent(Class<? extends EventViewAttribute<T>> eventViewAttributeClass, String attributeName) {
	Preconditions.checkNotNull(eventViewAttributeClass, "eventViewAttributeClass cannot be null");
	addEventViewAttributeMapping(eventViewAttributeFactoryForClass(eventViewAttributeClass), attributeName);
    }

    @Override
    public void mapEvent(EventViewAttributeFactory<T> eventViewAttributeFactory, String attributeName) {
	Preconditions.checkNotNull(eventViewAttributeFactory, "eventViewAttributeFactory cannot be null");
	addEventViewAttributeMapping(eventViewAttributeFactory, attributeName);
    }

    private void addEventViewAttributeMapping(EventViewAttributeFactory<T> eventViewAttributeFactory, String attributeName) {
	checkAttributeNameNotEmpty(attributeName);
	eventViewAttributeMappings.put(attributeName, eventViewAttributeFactory);
    }

    @Override
    public void mapGroupedAttribute(GroupedViewAttributeFactory<T> groupedViewAttributeFactory,
	    String... attributeNames) {
	Preconditions.checkNotNull(groupedViewAttributeFactory, "groupedViewAttributeFactory cannot be null");
	addGroupedViewAttributeMapping(groupedViewAttributeFactory, attributeNames);
    }

    @Override
    public void mapGroupedAttribute(Class<? extends GroupedViewAttribute<T>> groupedViewAttributeClass,
	    String... attributeNames) {
        Preconditions.checkNotNull(groupedViewAttributeClass, "groupedViewAttributeClass cannot be null");
        addGroupedViewAttributeMapping(groupedViewAttributeFactoryForClass(groupedViewAttributeClass), attributeNames);
    }

    private void addGroupedViewAttributeMapping(GroupedViewAttributeFactory<T> groupedViewAttributeFactory,
	    String... attributeNames) {
	org.robobinding.util.Preconditions.checkNotBlank(
		"attributeNames cannot be empty or contain any empty attribute name", 
		attributeNames);
	groupedViewAttributeMappings.put(attributeNames, groupedViewAttributeFactory);
    }
    
    @Override
    public InitailizedBindingAttributeMappings<T> createInitailizedBindingAttributeMappings() {
	return new InitailizedBindingAttributeMappingsImpl<T>(
		propertyViewAttributeMappings, multiTypePropertyViewAttributeMappings, 
		eventViewAttributeMappings, groupedViewAttributeMappings);
    }
}
