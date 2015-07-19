package org.robobinding.codegen.viewbinding;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.customviewbinding.CustomViewBinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@ViewBinding(simpleOneWayProperties={"primitiveProp", "objectProp"})
public class SimpleOneWayPropertyViewBinding extends CustomViewBinding<SimpleOneWayPropertyView>{

}
