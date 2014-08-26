package org.robobinding.viewattribute.grouped;

import java.util.Map;

import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.internal.guava.Maps;
import org.robobinding.viewattribute.Bindable;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ChildViewAttributesBuilderImpl<ViewType> implements ChildViewAttributesBuilder<ViewType> {
    private final ResolvedGroupAttributes resolvedGroupAttributes;
    private final ViewAttributeBinderFactory<ViewType> viewAttributeBinderFactory;
    private final ChildViewAttributeInitializer childViewAttributeInitializer;
    final Map<String, Bindable> childViewAttributeMap;
    private boolean failOnFirstBindingError;

    public ChildViewAttributesBuilderImpl(ResolvedGroupAttributes resolvedGroupAttributes,
	    ViewAttributeBinderFactory<ViewType> viewAttributeBinderFactory) {
	this.resolvedGroupAttributes = resolvedGroupAttributes;
	this.viewAttributeBinderFactory = viewAttributeBinderFactory;

	childViewAttributeInitializer = new ChildViewAttributeInitializer();
	childViewAttributeMap = Maps.newLinkedHashMap();
	failOnFirstBindingError = false;
    }

    @Override
    public void add(String attributeName, ChildViewAttribute childAttribute) {
	AbstractAttribute attribute = resolvedGroupAttributes.attributeFor(attributeName);
	childViewAttributeInitializer.initializeChildViewAttribute(childAttribute, attribute);
	childViewAttributeMap.put(attributeName, childAttribute);
    }

    @Override
    public void add(String attributeName, ChildViewAttributeFactory factory) {
	ChildViewAttribute childViewAttribute = factory.create();
	add(attributeName, childViewAttribute);
    }

    @Override
    public void addDependent(String attributeName, ChildViewAttributeFactory factory) {
	AbstractAttribute attribute = resolvedGroupAttributes.attributeFor(attributeName);
	childViewAttributeMap.put(attributeName, new DependentChildViewAttribute(factory, attribute, childViewAttributeInitializer));
    }

    @Override
    public void add(String attributeName, PropertyViewAttribute<ViewType, ?> viewAttribute) {
	ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
	PropertyViewAttributeBinder<ViewType, ?> viewAttributeBinder =
		viewAttributeBinderFactory.createPropertyViewAttributeBinder(viewAttribute, attribute);
	childViewAttributeMap.put(attributeName, viewAttributeBinder);
    }

    @Override
    public void add(String propertyAttribute,
	    PropertyViewAttributeFactory<ViewType> factory) {
	ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(propertyAttribute);
	PropertyViewAttributeBinder<ViewType, ?> viewAttributeBinder =
		viewAttributeBinderFactory.createPropertyViewAttributeBinder(factory, attribute);
	childViewAttributeMap.put(propertyAttribute, viewAttributeBinder);
    }

    @Override
    public void add(String attributeName, MultiTypePropertyViewAttribute<ViewType> viewAttribute) {
	ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
	MultiTypePropertyViewAttributeBinder<ViewType> viewAttributeBinder =
		viewAttributeBinderFactory.createMultiTypePropertyViewAttributeBinder(viewAttribute, attribute);
	childViewAttributeMap.put(attributeName, viewAttributeBinder);
    }

    @Override
    public void add(String propertyAttribute,
	    MultiTypePropertyViewAttributeFactory<ViewType> factory) {
	ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(propertyAttribute);
	MultiTypePropertyViewAttributeBinder<ViewType> viewAttributeBinder = 
		viewAttributeBinderFactory.createMultiTypePropertyViewAttributeBinder(factory, attribute);
	childViewAttributeMap.put(propertyAttribute, viewAttributeBinder);
    }

    @Override
    public void failOnFirstBindingError() {
	failOnFirstBindingError = true;
    }

    public ChildViewAttributes build() {
	return new ChildViewAttributes(childViewAttributeMap, failOnFirstBindingError);
    }

    @Override
    public boolean hasAttribute(String attributeName) {
	return resolvedGroupAttributes.hasAttribute(attributeName);
    }

    @Override
    public ValueModelAttribute valueModelAttributeFor(String attributeName) {
	return resolvedGroupAttributes.valueModelAttributeFor(attributeName);
    }

    @Override
    public StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
	return resolvedGroupAttributes.staticResourceAttributeFor(attributeName);
    }

    @Override
    public <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName) {
	return resolvedGroupAttributes.enumAttributeFor(attributeName);
    }
}