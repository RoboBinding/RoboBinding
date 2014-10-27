package org.robobinding.viewattribute.impl;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface BindingAttributeMappingsProvider<ViewType> {
	InitailizedBindingAttributeMappings<ViewType> createBindingAttributeMappings();
}
