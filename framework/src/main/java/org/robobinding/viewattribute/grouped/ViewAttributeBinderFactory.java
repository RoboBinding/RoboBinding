package org.robobinding.viewattribute.grouped;

import java.util.Map;

import org.robobinding.attribute.EventAttribute;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderProviderAdapter;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeBinderFactory<ViewType> {
	private final ViewType view;
	private final PropertyViewAttributeBinderFactory<ViewType> propertyViewAttributeBinderFactory;
	private final PropertyAttributeParser propertyAttributeParser;
	private final GroupAttributesResolver groupAttributesResolver;
	private final ViewListenersInjector viewListenersInjector;

	public ViewAttributeBinderFactory(ViewType view, PropertyViewAttributeBinderFactory<ViewType> propertyViewAttributeBinderFactory,
			PropertyAttributeParser propertyAttributeParser, GroupAttributesResolver resolvedGroupAttributesFactory, ViewListenersInjector viewListenersInjector) {
		this.view = view;
		this.propertyViewAttributeBinderFactory = propertyViewAttributeBinderFactory;
		this.propertyAttributeParser = propertyAttributeParser;
		this.groupAttributesResolver = resolvedGroupAttributesFactory;
		this.viewListenersInjector = viewListenersInjector;
	}

	public PropertyViewAttributeBinder<ViewType, ?> createPropertyViewAttributeBinder(PropertyViewAttributeFactory<ViewType> viewAttributeFactory,
			String attributeName, String attributeValue) {
		ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);

		return createPropertyViewAttributeBinder(viewAttributeFactory, attribute);
	}

	public PropertyViewAttributeBinder<ViewType, ?> createPropertyViewAttributeBinder(PropertyViewAttributeFactory<ViewType> viewAttributeFactory,
			ValueModelAttribute attribute) {
		return createPropertyViewAttributeBinder(viewAttributeFactory.create(), attribute);
	}

	public PropertyViewAttributeBinder<ViewType, ?> createPropertyViewAttributeBinder(PropertyViewAttribute<ViewType, ?> viewAttribute,
			ValueModelAttribute attribute) {
		viewListenersInjector.injectIfRequired(viewAttribute, view);
		PropertyViewAttributeBinder<ViewType, ?> viewAttributeBinder = propertyViewAttributeBinderFactory.create(viewAttribute, attribute);
		return viewAttributeBinder;
	}

	public MultiTypePropertyViewAttributeBinder<ViewType> createMultiTypePropertyViewAttributeBinder(
			MultiTypePropertyViewAttributeFactory<ViewType> viewAttributeFactory, String attributeName, String attributeValue) {
		ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);
		return createMultiTypePropertyViewAttributeBinder(viewAttributeFactory, attribute);
	}

	public MultiTypePropertyViewAttributeBinder<ViewType> createMultiTypePropertyViewAttributeBinder(
			MultiTypePropertyViewAttributeFactory<ViewType> viewAttributeFactory, ValueModelAttribute attribute) {
		return createMultiTypePropertyViewAttributeBinder(viewAttributeFactory.create(), attribute);
	}

	public MultiTypePropertyViewAttributeBinder<ViewType> createMultiTypePropertyViewAttributeBinder(MultiTypePropertyViewAttribute<ViewType> viewAttribute,
			ValueModelAttribute attribute) {
		PropertyViewAttributeBinderProviderAdapter<ViewType> propertyViewAttributeBinderProviderAdapter = new PropertyViewAttributeBinderProviderAdapter<ViewType>(
				view, viewAttribute, attribute, propertyViewAttributeBinderFactory, viewListenersInjector);
		return new MultiTypePropertyViewAttributeBinder<ViewType>(propertyViewAttributeBinderProviderAdapter, attribute);
	}

	public EventViewAttributeBinder<ViewType> createEventViewAttributeBinder(EventViewAttributeFactory<ViewType> viewAttributeFactory, String attributeName,
			String attributeValue) {
		EventViewAttribute<ViewType> viewAttribute = viewAttributeFactory.create();
		viewListenersInjector.injectIfRequired(viewAttribute, view);
		EventViewAttributeBinder<ViewType> viewAttributeBinder = new EventViewAttributeBinder<ViewType>(view, viewAttribute, new EventAttribute(attributeName,
				attributeValue));
		return viewAttributeBinder;
	}

	public GroupedViewAttributeBinder<ViewType> createGroupedViewAttributeBinder(GroupedViewAttributeFactory<ViewType> viewAttributeFactory,
			String[] attributeGroup, Map<String, String> presentAttributeMappings) {
		PendingGroupAttributes pendingGroupAttributes = new PendingGroupAttributes(presentAttributeMappings);
		GroupedViewAttribute<ViewType> viewAttribute = viewAttributeFactory.create();
		ResolvedGroupAttributes resolvedGroupAttributes = groupAttributesResolver.resolve(pendingGroupAttributes, viewAttribute);
		ChildViewAttributesBuilderImpl<ViewType> childViewAttributesBuilder = new ChildViewAttributesBuilderImpl<ViewType>(resolvedGroupAttributes, this);

		return new GroupedViewAttributeBinder<ViewType>(view, viewAttribute, childViewAttributesBuilder);
	}
}
