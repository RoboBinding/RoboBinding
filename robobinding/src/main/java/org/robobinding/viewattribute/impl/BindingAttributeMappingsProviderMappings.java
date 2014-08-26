package org.robobinding.viewattribute.impl;



/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappingsProviderMappings {
    <ViewType> void put(Class<ViewType> viewClass, BindingAttributeMappingsProvider<ViewType> bindingAttributeMappingsProvider);
}
