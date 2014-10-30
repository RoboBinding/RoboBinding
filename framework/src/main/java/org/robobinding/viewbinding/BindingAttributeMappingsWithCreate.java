package org.robobinding.viewbinding;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BindingAttributeMappingsWithCreate<ViewType> extends BindingAttributeMappings<ViewType> {
	InitailizedBindingAttributeMappings createInitailizedBindingAttributeMappings();
}
