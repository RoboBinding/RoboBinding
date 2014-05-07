package org.robobinding.viewattribute;

import org.robobinding.BindingContext;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface ViewAttribute extends Bindable {
    void preInitializeView(BindingContext bindingContext);
}
