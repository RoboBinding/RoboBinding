package org.robobinding.viewbinding;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface ViewBinding<ViewType> {
	void mapBindingAttributes(BindingAttributeMappings<ViewType> mappings);
}
