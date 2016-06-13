package org.robobinding.customviewbinding;

import java.util.Set;

import org.robobinding.util.Sets;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayMultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttributeFactory;
import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class KeepFirstAttributes<ViewType> implements BindingAttributeMappings<ViewType> {
	private final BindingAttributeMappings<ViewType> forwarding;
	private final Set<String> propertyAttributes;
	private final Set<String> multiTypePropertyAttributes;
	private final Set<String> eventAttributes;
	private final Set<String[]> attributeGroups;

	public KeepFirstAttributes(BindingAttributeMappings<ViewType> forwarding) {
		this.forwarding = forwarding;
		this.propertyAttributes = Sets.newHashSet();
		this.multiTypePropertyAttributes = Sets.newHashSet();
		this.eventAttributes = Sets.newHashSet();
		this.attributeGroups = Sets.newHashSet();
	}

	@Override
	public void mapOneWayProperty(Class<? extends OneWayPropertyViewAttribute<ViewType, ?>> viewAttributeClass, String attributeName) {
		if (propertyAttributes.contains(attributeName))
			return;

		forwarding.mapOneWayProperty(viewAttributeClass, attributeName);
		propertyAttributes.add(attributeName);
	}

	@Override
	public void mapOneWayProperty(OneWayPropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		if (propertyAttributes.contains(attributeName))
			return;

		forwarding.mapOneWayProperty(factory, attributeName);
		propertyAttributes.add(attributeName);
	}

	@Override
	public void mapTwoWayProperty(Class<? extends TwoWayPropertyViewAttribute<ViewType, ?, ?>> viewAttributeClass, String attributeName) {
		if (propertyAttributes.contains(attributeName))
			return;

		forwarding.mapTwoWayProperty(viewAttributeClass, attributeName);
		propertyAttributes.add(attributeName);
	}

	@Override
	public void mapTwoWayProperty(TwoWayPropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		if (propertyAttributes.contains(attributeName))
			return;

		forwarding.mapTwoWayProperty(factory, attributeName);
		propertyAttributes.add(attributeName);
	}

	@Override
	public void mapOneWayMultiTypeProperty(Class<? extends OneWayMultiTypePropertyViewAttribute<ViewType>> viewAttributeClass, String attributeName) {
		if (multiTypePropertyAttributes.contains(attributeName))
			return;

		forwarding.mapOneWayMultiTypeProperty(viewAttributeClass, attributeName);
		multiTypePropertyAttributes.add(attributeName);
	}

	@Override
	public void mapOneWayMultiTypeProperty(OneWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		if (multiTypePropertyAttributes.contains(attributeName))
			return;

		forwarding.mapOneWayMultiTypeProperty(factory, attributeName);
		multiTypePropertyAttributes.add(attributeName);
	}

	@Override
	public void mapTwoWayMultiTypeProperty(Class<? extends TwoWayMultiTypePropertyViewAttribute<ViewType>> viewAttributeClass, String attributeName) {
		if (multiTypePropertyAttributes.contains(attributeName))
			return;

		forwarding.mapTwoWayMultiTypeProperty(viewAttributeClass, attributeName);
		multiTypePropertyAttributes.add(attributeName);
	}

	@Override
	public void mapTwoWayMultiTypeProperty(TwoWayMultiTypePropertyViewAttributeFactory<ViewType> factory, String attributeName) {
		if (multiTypePropertyAttributes.contains(attributeName))
			return;

		forwarding.mapTwoWayMultiTypeProperty(factory, attributeName);
		multiTypePropertyAttributes.add(attributeName);
	}

	@Override
	public void mapEvent(Class<? extends EventViewAttribute<ViewType, ? extends ViewAddOn>> viewAttributeClass, String attributeName) {
		if (eventAttributes.contains(attributeName))
			return;

		forwarding.mapEvent(viewAttributeClass, attributeName);
		eventAttributes.add(attributeName);
	}

	@Override
	public void mapEvent(EventViewAttributeFactory<ViewType> factory, String attributeName) {
		if (eventAttributes.contains(attributeName))
			return;

		forwarding.mapEvent(factory, attributeName);
		eventAttributes.add(attributeName);
	}

	@Override
	public void mapGroupedAttribute(Class<? extends GroupedViewAttribute<ViewType>> viewAttributeClass, String... attributeGroup) {
		if (attributeGroups.contains(attributeGroup))
			return;

		forwarding.mapGroupedAttribute(viewAttributeClass, attributeGroup);
		attributeGroups.add(attributeGroup);
	}

	@Override
	public void mapGroupedAttribute(GroupedViewAttributeFactory<ViewType> factory, String... attributeGroup) {
		if (attributeGroups.contains(attributeGroup))
			return;

		forwarding.mapGroupedAttribute(factory, attributeGroup);
		attributeGroups.add(attributeGroup);
	}
}