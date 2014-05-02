package org.robobinding.binder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.PendingAttributesForView;
import org.robobinding.PendingAttributesForView.AttributeGroupResolver;
import org.robobinding.PendingAttributesForView.AttributeResolver;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolver {
    private final BindingAttributeMappingsImpl<View> bindingAttributeMappings;

    private final PropertyViewAttributeResolver propertyViewAttributeResolver;
    private final CommandViewAttributeResolver commandViewAttributeResolver;
    private final GroupedViewAttributeResolver groupedViewAttributeResolver;

    private List<ViewAttribute> resolvedViewAttributes;

    public ByBindingAttributeMappingsResolver(BindingAttributeMappingsImpl<View> bindingAttributeMappings) {
	this.bindingAttributeMappings = bindingAttributeMappings;
	this.propertyViewAttributeResolver = new PropertyViewAttributeResolver();
	this.commandViewAttributeResolver = new CommandViewAttributeResolver();
	this.groupedViewAttributeResolver = new GroupedViewAttributeResolver();
    }

    public Collection<ViewAttribute> resolve(PendingAttributesForView pendingAttributesForView) {
	resolvedViewAttributes = Lists.newArrayList();

	resolvePropertyViewAttributes(pendingAttributesForView);
	resolveCommandViewAttributes(pendingAttributesForView);
	resolveGroupedViewAttributes(pendingAttributesForView);

	return resolvedViewAttributes;
    }

    private void resolvePropertyViewAttributes(PendingAttributesForView pendingAttributesForView) {
	for (String propertyAttribute : bindingAttributeMappings.getPropertyAttributes()) {
	    pendingAttributesForView.resolveAttributeIfExists(propertyAttribute, propertyViewAttributeResolver);
	}
    }

    private void resolveCommandViewAttributes(PendingAttributesForView pendingAttributesForView) {
	for (String commandAttribute : bindingAttributeMappings.getCommandAttributes()) {
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
	    PropertyViewAttribute<View> propertyViewAttribute = bindingAttributeMappings.createPropertyViewAttribute(view, attribute, attributeValue);
	    resolvedViewAttributes.add(propertyViewAttribute);
	}
    }

    private class CommandViewAttributeResolver implements AttributeResolver {
	@Override
	public void resolve(View view, String attribute, String attributeValue) {
	    AbstractCommandViewAttribute<View> commandViewAttribute = bindingAttributeMappings.createCommandViewAttribute(view, attribute,
		    attributeValue);
	    resolvedViewAttributes.add(commandViewAttribute);
	}
    }

    private class GroupedViewAttributeResolver implements AttributeGroupResolver {
	@Override
	public void resolve(View view, String[] attributeGroup, Map<String, String> presentAttributeMappings) {
	    AbstractGroupedViewAttribute<View> groupedViewAttribute = bindingAttributeMappings.createGroupedViewAttribute(view, attributeGroup,
		    presentAttributeMappings);
	    resolvedViewAttributes.add(groupedViewAttribute);
	}
    }
}
