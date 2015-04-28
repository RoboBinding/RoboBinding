package org.robobinding.widget.expandablelistview;

import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.attribute.PredefinedMappingsAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;
import org.robobinding.widget.adapterview.PredefinedMappingUpdater;

import java.util.Collection;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupMappingAttribute implements ChildViewAttributeWithAttribute<PredefinedMappingsAttribute> {
	private final PredefinedMappingUpdater predefinedMappingsUpdater;
	private PredefinedMappingsAttribute mappingsAttribute;

	public GroupMappingAttribute(PredefinedMappingUpdater predefinedMappingsUpdater) {
		this.predefinedMappingsUpdater = predefinedMappingsUpdater;
	}

	@Override
	public void setAttribute(PredefinedMappingsAttribute attribute) {
		this.mappingsAttribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		Collection<PredefinedPendingAttributesForView> viewMappings = mappingsAttribute.getViewMappings(bindingContext.getContext());
		predefinedMappingsUpdater.updateViewMappings(viewMappings);
	}
}
