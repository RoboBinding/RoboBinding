package org.robobinding.widget.compoundbutton;

import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;

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
	mappings.mapProperty(CheckedAttribute.class, "checked");

	mappings.mapEvent(OnCheckedChangeAttribute.class, "onCheckedChange");
    }
}
