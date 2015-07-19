package org.robobinding.codegen.viewbinding;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.customviewbinding.CustomViewBinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@ViewBinding(simpleOneWayProperties={ViewWithProperties.AMBIGUOUS_SETTER})
public class ViewBindingWithAmbiguousSetter extends CustomViewBinding<ViewWithProperties> {
}
