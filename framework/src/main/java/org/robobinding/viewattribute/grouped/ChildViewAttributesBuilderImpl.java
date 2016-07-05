package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.EventAttribute;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.util.Maps;
import org.robobinding.viewattribute.Bindable;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeBinder;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinder;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.widgetaddon.ViewAddOn;

import java.util.Map;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ChildViewAttributesBuilderImpl<ViewType> implements ChildViewAttributesBuilder<ViewType> {
	private final ResolvedGroupAttributes resolvedGroupAttributes;
	private final ViewAttributeBinderFactory viewAttributeBinderFactory;
	private final ChildViewAttributeInitializer childViewAttributeInitializer;
	final Map<String, Bindable> childViewAttributeMap;
	private boolean failOnFirstBindingError;

	public ChildViewAttributesBuilderImpl(ResolvedGroupAttributes resolvedGroupAttributes, 
			ViewAttributeBinderFactory viewAttributeBinderFactory) {
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
	public void add(String attributeName, OneWayPropertyViewAttribute<ViewType, ?> viewAttribute) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		PropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(viewAttribute, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String propertyAttribute, OneWayPropertyViewAttributeFactory<ViewType> factory) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(propertyAttribute);
		PropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(factory, attribute);
		childViewAttributeMap.put(propertyAttribute, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, TwoWayPropertyViewAttribute<ViewType, ?, ?> viewAttribute) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		PropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(viewAttribute, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, TwoWayPropertyViewAttributeFactory<ViewType> factory) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		PropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(factory, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, OneWayMultiTypePropertyViewAttribute<ViewType> viewAttribute) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		MultiTypePropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(
				viewAttribute, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, OneWayMultiTypePropertyViewAttributeFactory<ViewType> factory) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		MultiTypePropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(factory,
				attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, TwoWayMultiTypePropertyViewAttribute<ViewType> viewAttribute) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		MultiTypePropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(
				viewAttribute, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, TwoWayMultiTypePropertyViewAttributeFactory<ViewType> factory) {
		ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(attributeName);
		MultiTypePropertyViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(factory,
				attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, EventViewAttribute<ViewType, ? extends ViewAddOn> viewAttribute) {
		EventAttribute attribute = resolvedGroupAttributes.eventAttributeFor(attributeName);
		EventViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(viewAttribute, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
	}

	@Override
	public void add(String attributeName, EventViewAttributeFactory<ViewType> factory) {
		EventAttribute attribute = resolvedGroupAttributes.eventAttributeFor(attributeName);
		EventViewAttributeBinder viewAttributeBinder = viewAttributeBinderFactory.binderFor(factory, attribute);
		childViewAttributeMap.put(attributeName, viewAttributeBinder);
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