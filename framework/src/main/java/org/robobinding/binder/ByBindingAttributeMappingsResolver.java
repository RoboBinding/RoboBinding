package org.robobinding.binder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForView.AttributeGroupResolver;
import org.robobinding.PendingAttributesForView.AttributeResolver;
import org.robobinding.util.Lists;
import org.robobinding.viewattribute.ViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinder;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;
import org.robobinding.viewbinding.InitailizedBindingAttributeMappings;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolver {
	private final InitailizedBindingAttributeMappings bindingAttributeMappings;

	private final PropertyViewAttributeResolver propertyViewAttributeResolver;
	private final MultiTypePropertyViewAttributeResolver multiTypePropertyViewAttributeResolver;
	private final EventViewAttributeResolver commandViewAttributeResolver;
	private final GroupedViewAttributeResolver groupedViewAttributeResolver;

	private List<ViewAttributeBinder> resolvedViewAttributes;

	public ByBindingAttributeMappingsResolver(InitailizedBindingAttributeMappings bindingAttributeMappings) {
		this.bindingAttributeMappings = bindingAttributeMappings;

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

		List<ViewAttributeBinder> temp = resolvedViewAttributes;
		resolvedViewAttributes = null;
		return temp;
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
		public void resolve(Object view, String attribute, String attributeValue) {
			PropertyViewAttributeBinderFactory factory = bindingAttributeMappings.getPropertyViewAttributeFactory(attribute);
			PropertyViewAttributeBinder viewAttributeBinder = factory.create(view, attribute, attributeValue);
			resolvedViewAttributes.add(viewAttributeBinder);
		}
	}

	private class MultiTypePropertyViewAttributeResolver implements AttributeResolver {
		@Override
		public void resolve(Object view, String attribute, String attributeValue) {
			MultiTypePropertyViewAttributeBinderFactory factory = bindingAttributeMappings.getMultiTypePropertyViewAttributeFactory(attribute);
			MultiTypePropertyViewAttributeBinder viewAttributeBinder = factory.create(view, attribute, attributeValue);
			resolvedViewAttributes.add(viewAttributeBinder);
		}
	}

	private class EventViewAttributeResolver implements AttributeResolver {
		@Override
		public void resolve(Object view, String attribute, String attributeValue) {
			EventViewAttributeBinderFactory factory = bindingAttributeMappings.getEventViewAttributeFactory(attribute);
			EventViewAttributeBinder viewAttributeBinder = factory.create(view, attribute, attributeValue);
			resolvedViewAttributes.add(viewAttributeBinder);
		}
	}

	private class GroupedViewAttributeResolver implements AttributeGroupResolver {
		@Override
		public void resolve(Object view, String[] attributeGroup, Map<String, String> presentAttributeMappings) {
			GroupedViewAttributeBinderFactory factory = bindingAttributeMappings.getGroupedViewAttributeFactory(attributeGroup);
			GroupedViewAttributeBinder groupedViewAttribute = factory.create(view, presentAttributeMappings);
			resolvedViewAttributes.add(groupedViewAttribute);
		}
	}
}
