package org.robobinding.viewbinding;

import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface InitializedBindingAttributeMappingsProvider {
	InitailizedBindingAttributeMappings create(ViewAttributeBinderFactory viewAttributeBinderFactory);
}
