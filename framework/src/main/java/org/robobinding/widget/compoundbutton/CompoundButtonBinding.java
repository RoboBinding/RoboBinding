package org.robobinding.widget.compoundbutton;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.CompoundButton;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CompoundButtonBinding implements ViewBinding<CompoundButton> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<CompoundButton> mappings) {
		mappings.mapTwoWayProperty(CheckedAttribute.class, "checked");

		mappings.mapEvent(OnCheckedChangeAttribute.class, "onCheckedChange");
	}
}
