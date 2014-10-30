package org.robobinding.widget.viewanimator;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.ViewAnimator;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class ViewAnimatorBinding implements ViewBinding<ViewAnimator> {

	@Override
	public void mapBindingAttributes(BindingAttributeMappings<ViewAnimator> mappings) {
		mappings.mapOneWayProperty(DisplayedChildAttribute.class, "displayedChild");
	}
}
