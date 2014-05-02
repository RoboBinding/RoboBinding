package org.robobinding.viewattribute;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.Map;

import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.EnumAttribute;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributesBuilder<T extends View> {
    private final ResolvedGroupAttributes resolvedGroupAttributes;
    private final ChildViewAttributeInitializer viewAttributeInitializer;
    final Map<String, Bindable> childViewAttributeMap;
    private boolean failOnFirstBindingError;

    public ChildViewAttributesBuilder(ResolvedGroupAttributes resolvedGroupAttributes, ChildViewAttributeInitializer viewAttributeInitializer) {
	this.resolvedGroupAttributes = resolvedGroupAttributes;
	this.viewAttributeInitializer = viewAttributeInitializer;
	childViewAttributeMap = newLinkedHashMap();
	failOnFirstBindingError = false;
    }

    public void add(String attributeName, ChildViewAttribute childAttribute) {
	AbstractAttribute attribute = resolvedGroupAttributes.attributeFor(attributeName);
	viewAttributeInitializer.initializeChildViewAttribute(childAttribute, attribute);
	childViewAttributeMap.put(attributeName, childAttribute);
    }

    public void add(String attributeName, ChildViewAttributeFactory factory) {
	AbstractAttribute attribute = resolvedGroupAttributes.attributeFor(attributeName);
	DependentChildViewAttribute dependentViewAttribute = new DependentChildViewAttribute(factory, attribute, viewAttributeInitializer);
	childViewAttributeMap.put(attributeName, dependentViewAttribute);
    }

    public void add(String propertyAttribute, PropertyViewAttribute<? extends View> propertyViewAttribute) {
	ValueModelAttribute attributeValue = resolvedGroupAttributes.valueModelAttributeFor(propertyAttribute);
	viewAttributeInitializer.initializePropertyViewAttribute(propertyViewAttribute, attributeValue);
	childViewAttributeMap.put(propertyAttribute, propertyViewAttribute);
    }

    public void add(String propertyAttribute, ViewAttributeFactory<? extends PropertyViewAttribute<? extends View>> factory) {
	ValueModelAttribute attribute = resolvedGroupAttributes.valueModelAttributeFor(propertyAttribute);
	DependentPropertyViewAttribute dependentViewAttribute = new DependentPropertyViewAttribute(factory, attribute, viewAttributeInitializer);
	childViewAttributeMap.put(propertyAttribute, dependentViewAttribute);
    }

    public void failOnFirstBindingError() {
	failOnFirstBindingError = true;
    }

    public ChildViewAttributes build() {
	return new ChildViewAttributes(childViewAttributeMap, failOnFirstBindingError);
    }

    public final boolean hasAttribute(String attributeName) {
	return resolvedGroupAttributes.hasAttribute(attributeName);
    }

    public final ValueModelAttribute valueModelAttributeFor(String attributeName) {
	return resolvedGroupAttributes.valueModelAttributeFor(attributeName);
    }

    public final StaticResourceAttribute staticResourceAttributeFor(String attributeName) {
	return resolvedGroupAttributes.staticResourceAttributeFor(attributeName);
    }

    public final <E extends Enum<E>> EnumAttribute<E> enumAttributeFor(String attributeName) {
	return resolvedGroupAttributes.enumAttributeFor(attributeName);
    }
}