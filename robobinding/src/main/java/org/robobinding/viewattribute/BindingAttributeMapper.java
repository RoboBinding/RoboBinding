package org.robobinding.viewattribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface BindingAttributeMapper<T extends View> {
    void mapBindingAttributes(BindingAttributeMappings<T> mappings);
}
