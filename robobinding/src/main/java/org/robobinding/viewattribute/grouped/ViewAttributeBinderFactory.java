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

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeBinderFactory<T extends View> {
    private final T view;
    private final PropertyViewAttributeBinderFactory<T> propertyViewAttributeBinderFactory;
    private final PropertyAttributeParser propertyAttributeParser;
    private final GroupAttributesResolver groupAttributesResolver;
    private final ViewListenersInjector viewListenersInjector;

    public ViewAttributeBinderFactory(T view, PropertyViewAttributeBinderFactory<T> propertyViewAttributeBinderFactory,
            PropertyAttributeParser propertyAttributeParser, GroupAttributesResolver resolvedGroupAttributesFactory,
            ViewListenersInjector viewListenersInjector) {
        this.view = view;
        this.propertyViewAttributeBinderFactory = propertyViewAttributeBinderFactory;
        this.propertyAttributeParser = propertyAttributeParser;
        this.groupAttributesResolver = resolvedGroupAttributesFactory;
        this.viewListenersInjector = viewListenersInjector;
    }

    public PropertyViewAttributeBinder<T, ?> createPropertyViewAttributeBinder(
	    PropertyViewAttributeFactory<T> viewAttributeFactory,
	    String attributeName, String attributeValue) {
	ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);

	return createPropertyViewAttributeBinder(viewAttributeFactory, attribute);
    }

    public PropertyViewAttributeBinder<T, ?> createPropertyViewAttributeBinder(
	    PropertyViewAttributeFactory<T> viewAttributeFactory,
	    ValueModelAttribute attribute) {
	return createPropertyViewAttributeBinder(viewAttributeFactory.create(), attribute);
    }

    public PropertyViewAttributeBinder<T, ?> createPropertyViewAttributeBinder(
            PropertyViewAttribute<T, ?> viewAttribute,
            ValueModelAttribute attribute) {
        viewListenersInjector.injectIfRequired(viewAttribute, view);
        PropertyViewAttributeBinder<T, ?> viewAttributeBinder = propertyViewAttributeBinderFactory.create(viewAttribute, attribute);
        return viewAttributeBinder;
    }

    public MultiTypePropertyViewAttributeBinder<T> createMultiTypePropertyViewAttributeBinder(
	    MultiTypePropertyViewAttributeFactory<T> viewAttributeFactory,
	    String attributeName,
	    String attributeValue) {
	ValueModelAttribute attribute = propertyAttributeParser.parseAsValueModelAttribute(attributeName, attributeValue);
	return createMultiTypePropertyViewAttributeBinder(viewAttributeFactory, attribute);
    }

    public MultiTypePropertyViewAttributeBinder<T> createMultiTypePropertyViewAttributeBinder(
	    MultiTypePropertyViewAttributeFactory<T> viewAttributeFactory,
	    ValueModelAttribute attribute) {
	return createMultiTypePropertyViewAttributeBinder(viewAttributeFactory.create(), attribute);
    }

    public MultiTypePropertyViewAttributeBinder<T> createMultiTypePropertyViewAttributeBinder(
            MultiTypePropertyViewAttribute<T> viewAttribute,
            ValueModelAttribute attribute) {
	PropertyViewAttributeBinderProviderAdapter<T> propertyViewAttributeBinderProviderAdapter = new PropertyViewAttributeBinderProviderAdapter<T>(
		view, viewAttribute, attribute, propertyViewAttributeBinderFactory, viewListenersInjector);
        return new MultiTypePropertyViewAttributeBinder<T>(
        	propertyViewAttributeBinderProviderAdapter,
        	attribute);
    }

    public EventViewAttributeBinder<T> createEventViewAttributeBinder(
	    EventViewAttributeFactory<T> viewAttributeFactory,
	    String attributeName,
	    String attributeValue) {
	EventViewAttribute<T> viewAttribute = viewAttributeFactory.create();
	viewListenersInjector.injectIfRequired(viewAttribute, view);
	EventViewAttributeBinder<T> viewAttributeBinder = new EventViewAttributeBinder<T>(
		view, viewAttribute, new EventAttribute(attributeName, attributeValue));
	return viewAttributeBinder;
    }

    public GroupedViewAttributeBinder<T> createGroupedViewAttributeBinder(
	    GroupedViewAttributeFactory<T> viewAttributeFactory,
	    String[] attributeGroup,
	    Map<String, String> presentAttributeMappings) {
	PendingGroupAttributes pendingGroupAttributes = new PendingGroupAttributes(presentAttributeMappings);
	GroupedViewAttribute<T> viewAttribute = viewAttributeFactory.create();
	ResolvedGroupAttributes resolvedGroupAttributes = groupAttributesResolver.resolve(
		pendingGroupAttributes, viewAttribute);
	ChildViewAttributesBuilderImpl<T> childViewAttributesBuilder = new ChildViewAttributesBuilderImpl<T>(
		resolvedGroupAttributes, this);

	return new GroupedViewAttributeBinder<T>(view, viewAttribute, childViewAttributesBuilder);
    }
}
