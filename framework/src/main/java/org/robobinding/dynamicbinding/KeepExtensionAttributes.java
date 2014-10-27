package org.robobinding.dynamicbinding;

import java.util.Set;

import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsWithCreate;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import com.google.common.collect.Sets;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class KeepExtensionAttributes<T extends View> implements BindingAttributeMappingsWithCreate<T> {
	private final BindingAttributeMappingsWithCreate<T> forwarding;
	private final Set<String> extensionAttributes;
	private final Set<String[]> extensionAttributeGroups;

	public KeepExtensionAttributes(BindingAttributeMappingsWithCreate<T> forwarding, Set<String> extensionAttributes, Set<String[]> extensionAttributeGroups) {
		this.forwarding = forwarding;
		this.extensionAttributes = Sets.newHashSet(extensionAttributes);
		this.extensionAttributeGroups = Sets.newHashSet(extensionAttributeGroups);
	}

	@Override
	public void mapProperty(Class<? extends PropertyViewAttribute<T, ?>> propertyViewAttributeClass, String attributeName) {
		if (extensionAttributes.contains(attributeName))
			return;

		forwarding.mapProperty(propertyViewAttributeClass, attributeName);
	}

	@Override
	public void mapProperty(PropertyViewAttributeFactory<T> propertyViewAttributeFactory, String attributeName) {
		if (extensionAttributes.contains(attributeName))
			return;

		forwarding.mapProperty(propertyViewAttributeFactory, attributeName);
	}

	@Override
	public void mapMultiTypeProperty(Class<? extends MultiTypePropertyViewAttribute<T>> multiTypePropertyViewAttributeClass, String attributeName) {
		if (extensionAttributes.contains(attributeName))
			return;

		forwarding.mapMultiTypeProperty(multiTypePropertyViewAttributeClass, attributeName);
	}

	@Override
	public void mapMultiTypeProperty(MultiTypePropertyViewAttributeFactory<T> multiTypePropertyViewAttributeFactory, String attributeName) {
		if (extensionAttributes.contains(attributeName))
			return;

		forwarding.mapMultiTypeProperty(multiTypePropertyViewAttributeFactory, attributeName);
	}

	@Override
	public void mapEvent(Class<? extends EventViewAttribute<T>> eventViewAttributeClass, String attributeName) {
		if (extensionAttributes.contains(attributeName))
			return;

		forwarding.mapEvent(eventViewAttributeClass, attributeName);
	}

	@Override
	public void mapEvent(EventViewAttributeFactory<T> eventViewAttributeFactory, String attributeName) {
		if (extensionAttributes.contains(attributeName))
			return;

		forwarding.mapEvent(eventViewAttributeFactory, attributeName);
	}

	@Override
	public void mapGroupedAttribute(Class<? extends GroupedViewAttribute<T>> groupedViewAttributeClass, String... attributeGroup) {
		if (extensionAttributeGroups.contains(attributeGroup))
			return;

		forwarding.mapGroupedAttribute(groupedViewAttributeClass, attributeGroup);
	}

	@Override
	public void mapGroupedAttribute(GroupedViewAttributeFactory<T> groupedViewAttributeFactory, String... attributeGroup) {
		if (extensionAttributeGroups.contains(attributeGroup))
			return;

		forwarding.mapGroupedAttribute(groupedViewAttributeFactory, attributeGroup);
	}

	@Override
	public InitailizedBindingAttributeMappings<T> createInitailizedBindingAttributeMappings() {
		return forwarding.createInitailizedBindingAttributeMappings();
	}
}