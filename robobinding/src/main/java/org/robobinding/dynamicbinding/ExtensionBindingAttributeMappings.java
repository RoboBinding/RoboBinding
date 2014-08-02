package org.robobinding.dynamicbinding;

import java.util.Set;

import org.robobinding.viewattribute.ViewBinding;
import org.robobinding.viewattribute.event.EventViewAttribute;
import org.robobinding.viewattribute.event.EventViewAttributeFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttribute;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsWithCreate;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeFactory;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttributeFactory;

import android.view.View;

import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ExtensionBindingAttributeMappings<T extends View> implements BindingAttributeMappingsWithCreate<T> {
    private final ViewBinding<T> existingBindingAttributeMapper;
    private final BindingAttributeMappingsImpl<T> bindingAttributeMappings;

    private final Set<String> extensionAttributes;
    private final Set<String[]> extensionAttributeGroups;

    public ExtensionBindingAttributeMappings(ViewBinding<T> existingBindingAttributeMapper) {
	this.existingBindingAttributeMapper = existingBindingAttributeMapper;
	this.bindingAttributeMappings = new BindingAttributeMappingsImpl<T>();

	extensionAttributes = Sets.newHashSet();
	extensionAttributeGroups = Sets.newHashSet();
    }

    @Override
    public void mapProperty(Class<? extends PropertyViewAttribute<T, ?>> propertyViewAttributeClass, String attributeName) {
	extensionAttributes.add(attributeName);
	bindingAttributeMappings.mapProperty(propertyViewAttributeClass, attributeName);
    }

    @Override
    public void mapProperty(PropertyViewAttributeFactory<T> propertyViewAttributeFactory, String attributeName) {
	extensionAttributes.add(attributeName);
	bindingAttributeMappings.mapProperty(propertyViewAttributeFactory, attributeName);
    }

    @Override
    public void mapMultiTypeProperty(Class<? extends MultiTypePropertyViewAttribute<T>> multiTypePropertyViewAttributeClass, String attributeName) {
	extensionAttributes.add(attributeName);
	bindingAttributeMappings.mapMultiTypeProperty(multiTypePropertyViewAttributeClass, attributeName);
    }

    @Override
    public void mapMultiTypeProperty(MultiTypePropertyViewAttributeFactory<T> multiTypePropertyViewAttributeFactory, String attributeName) {
	extensionAttributes.add(attributeName);
	bindingAttributeMappings.mapMultiTypeProperty(multiTypePropertyViewAttributeFactory, attributeName);
    }

    @Override
    public void mapEvent(Class<? extends EventViewAttribute<T>> eventViewAttributeClass, String attributeName) {
	extensionAttributes.add(attributeName);
	bindingAttributeMappings.mapEvent(eventViewAttributeClass, attributeName);
    }

    @Override
    public void mapEvent(EventViewAttributeFactory<T> eventViewAttributeFactory, String attributeName) {
	extensionAttributes.add(attributeName);
	bindingAttributeMappings.mapEvent(eventViewAttributeFactory, attributeName);
    }

    @Override
    public void mapGroupedAttribute(Class<? extends GroupedViewAttribute<T>> groupedViewAttributeClass, String... attributeGroup) {
	extensionAttributeGroups.add(attributeGroup);
	bindingAttributeMappings.mapGroupedAttribute(groupedViewAttributeClass, attributeGroup);
    }

    @Override
    public void mapGroupedAttribute(GroupedViewAttributeFactory<T> groupedViewAttributeFactory, String... attributeGroup) {
	extensionAttributeGroups.add(attributeGroup);
	bindingAttributeMappings.mapGroupedAttribute(groupedViewAttributeFactory, attributeGroup);
    }

    @Override
    public InitailizedBindingAttributeMappings<T> createInitailizedBindingAttributeMappings() {
	KeepExtensionAttributes<T> keepExtensionAttributes = new KeepExtensionAttributes<T>(
		bindingAttributeMappings,
		extensionAttributes,
		extensionAttributeGroups);
	existingBindingAttributeMapper.mapBindingAttributes(keepExtensionAttributes);
	return keepExtensionAttributes.createInitailizedBindingAttributeMappings();
    }

}
