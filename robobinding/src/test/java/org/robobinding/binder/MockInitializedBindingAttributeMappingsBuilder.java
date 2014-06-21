package org.robobinding.binder;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

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

    private MockInitializedBindingAttributeMappingsBuilder() {
	propertyAttributes = newArrayList();
	multiTypePropertyAttributes = newArrayList();
	eventAttributes = newArrayList();
	attributeGroups = newArrayList();
    }

    public static MockInitializedBindingAttributeMappingsBuilder aBindingAttributeMappings() {
	return new MockInitializedBindingAttributeMappingsBuilder();
    }

    public MockInitializedBindingAttributeMappingsBuilder withPropertyAttribute(String attribute) {
	propertyAttributes.add(attribute);
	return this;
    }

    public MockInitializedBindingAttributeMappingsBuilder withMultiTypePropertyAttribute(String attribute) {
	multiTypePropertyAttributes.add(attribute);
	return this;
    }

    public MockInitializedBindingAttributeMappingsBuilder withEventAttribute(String attribute) {
	eventAttributes.add(attribute);
	return this;
    }

    public MockInitializedBindingAttributeMappingsBuilder withAttributeGroup(String[] attributeGroup) {
	attributeGroups.add(attributeGroup);
	return this;
    }

    public InitailizedBindingAttributeMappings<View> build() {
	return new MockInitailizedBindingAttributeMappings(this);
    }

    private static class MockInitailizedBindingAttributeMappings implements InitailizedBindingAttributeMappings<View> {
	private List<String> propertyAttributes;
	private List<String> multiTypePropertyAttributes;
	private List<String> eventAttributes;
	private List<String[]> attributeGroups;

	private MockInitailizedBindingAttributeMappings(MockInitializedBindingAttributeMappingsBuilder builder) {
	    propertyAttributes = newArrayList(builder.propertyAttributes);
	    multiTypePropertyAttributes = newArrayList(builder.multiTypePropertyAttributes);
	    eventAttributes = newArrayList(builder.eventAttributes);
	    attributeGroups = newArrayList(builder.attributeGroups);
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
	public PropertyViewAttributeFactory<View> getPropertyViewAttributeFactory(String attribute) {
	    return null;
	}

	@Override
	public MultiTypePropertyViewAttributeFactory<View> getMultiTypePropertyViewAttributeFactory(String attribute) {
	    return null;
	}

	@Override
	public EventViewAttributeFactory<View> getEventViewAttributeFactory(String attribute) {
	    return null;
	}

	@Override
	public GroupedViewAttributeFactory<View> getGroupedViewAttributeFactory(String[] attributeGroup) {
	    return null;
	}
    }
}
