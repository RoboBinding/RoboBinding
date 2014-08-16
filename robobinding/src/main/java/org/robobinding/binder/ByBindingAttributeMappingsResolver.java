package org.robobinding.binder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForView.AttributeGroupResolver;
import org.robobinding.PendingAttributesForView.AttributeResolver;
import org.robobinding.internal.guava.Lists;
import org.robobinding.viewattribute.ViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinder;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolver {
    private final InitailizedBindingAttributeMappings<View> bindingAttributeMappings;
    private final ViewAttributeBinderFactory<View> viewAttributeBinderFactory;

    private final PropertyViewAttributeResolver propertyViewAttributeResolver;
    private final MultiTypePropertyViewAttributeResolver multiTypePropertyViewAttributeResolver;
    private final EventViewAttributeResolver commandViewAttributeResolver;
    private final GroupedViewAttributeResolver groupedViewAttributeResolver;

    private List<ViewAttributeBinder> resolvedViewAttributes;

    public ByBindingAttributeMappingsResolver(
	    InitailizedBindingAttributeMappings<View> bindingAttributeMappings,
	    ViewAttributeBinderFactory<View> viewAttributeBinderFactory) {
	this.bindingAttributeMappings = bindingAttributeMappings;
	this.viewAttributeBinderFactory = viewAttributeBinderFactory;

	this.propertyViewAttributeResolver = new PropertyViewAttributeResolver();
	this.multiTypePropertyViewAttributeResolver = new MultiTypePropertyViewAttributeResolver();
	this.commandViewAttributeResolver = new EventViewAttributeResolver();
	this.groupedViewAttributeResolver = new GroupedViewAttributeResolver();
    }

    public Collection<ViewAttributeBinder> resolve(PendingAttributesForView pendingAttributesForView) {
	resolvedViewAttributes = Lists.newArrayList();

	resolvePropertyViewAttributes(pendingAttributesForView);
	resolveMultiTypePropertyViewAttributes(pendingAttributesForView);
	resolveCommandViewAttributes(pendingAttributesForView);
	resolveGroupedViewAttributes(pendingAttributesForView);

	return resolvedViewAttributes;
    }

    private void resolvePropertyViewAttributes(PendingAttributesForView pendingAttributesForView) {
	for (String propertyAttribute : bindingAttributeMappings.getPropertyAttributes()) {
	    pendingAttributesForView.resolveAttributeIfExists(propertyAttribute, propertyViewAttributeResolver);
	}
    }

    private void resolveMultiTypePropertyViewAttributes(PendingAttributesForView pendingAttributesForView) {
	for (String propertyAttribute : bindingAttributeMappings.getMultiTypePropertyAttributes()) {
	    pendingAttributesForView.resolveAttributeIfExists(propertyAttribute, multiTypePropertyViewAttributeResolver);
	}
    }

    private void resolveCommandViewAttributes(PendingAttributesForView pendingAttributesForView) {
	for (String commandAttribute : bindingAttributeMappings.getEventAttributes()) {
	    pendingAttributesForView.resolveAttributeIfExists(commandAttribute, commandViewAttributeResolver);
	}
    }

    private void resolveGroupedViewAttributes(PendingAttributesForView pendingAttributesForView) {
	for (String[] attributeGroup : bindingAttributeMappings.getAttributeGroups()) {
	    pendingAttributesForView.resolveAttributeGroupIfExists(attributeGroup, groupedViewAttributeResolver);
	}
    }

    private class PropertyViewAttributeResolver implements AttributeResolver {
	@Override
	public void resolve(View view, String attribute, String attributeValue) {
	    PropertyViewAttributeBinder<View, ?> viewAttributeBinder = viewAttributeBinderFactory.createPropertyViewAttributeBinder(
		    bindingAttributeMappings.getPropertyViewAttributeFactory(attribute),
		    attribute,
		    attributeValue);
	    resolvedViewAttributes.add(viewAttributeBinder);
	}
    }

    private class MultiTypePropertyViewAttributeResolver implements AttributeResolver {
	@Override
	public void resolve(View view, String attribute, String attributeValue) {
	    MultiTypePropertyViewAttributeBinder<View> viewAttributeBinder = viewAttributeBinderFactory.createMultiTypePropertyViewAttributeBinder(
		    bindingAttributeMappings.getMultiTypePropertyViewAttributeFactory(attribute),
		    attribute,
		    attributeValue);
	    resolvedViewAttributes.add(viewAttributeBinder);
	}
    }

    private class EventViewAttributeResolver implements AttributeResolver {
	@Override
	public void resolve(View view, String attribute, String attributeValue) {
	    EventViewAttributeBinder<View> viewAttributeBinder = viewAttributeBinderFactory.createEventViewAttributeBinder(
		    bindingAttributeMappings.getEventViewAttributeFactory(attribute),
		    attribute,
		    attributeValue);
	    resolvedViewAttributes.add(viewAttributeBinder);
	}
    }

    private class GroupedViewAttributeResolver implements AttributeGroupResolver {
	@Override
	public void resolve(View view, String[] attributeGroup, Map<String, String> presentAttributeMappings) {
	    GroupedViewAttributeBinder<View> groupedViewAttribute = viewAttributeBinderFactory.createGroupedViewAttributeBinder(
		    bindingAttributeMappings.getGroupedViewAttributeFactory(attributeGroup),
		    attributeGroup,
		    presentAttributeMappings);
	    resolvedViewAttributes.add(groupedViewAttribute);
	}
    }
}
