package org.robobinding.viewbinding;

import org.robobinding.viewattribute.event.EventViewAttributeBinderFactory;
import org.robobinding.viewattribute.grouped.GroupedViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttributeBinderFactory;
import org.robobinding.viewattribute.property.PropertyViewAttributeBinderFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface InitailizedBindingAttributeMappings {
	Iterable<String> getPropertyAttributes();

	Iterable<String> getMultiTypePropertyAttributes();

	Iterable<String> getEventAttributes();

	Iterable<String[]> getAttributeGroups();

	PropertyViewAttributeBinderFactory getPropertyViewAttributeFactory(String attribute);

	MultiTypePropertyViewAttributeBinderFactory getMultiTypePropertyViewAttributeFactory(String attribute);

	EventViewAttributeBinderFactory getEventViewAttributeFactory(String attribute);

	GroupedViewAttributeBinderFactory getGroupedViewAttributeFactory(String[] attributeGroup);
}
