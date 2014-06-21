package org.robobinding.widget.compoundbutton;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.CompoundButton;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CompoundButtonAttributeMapper implements BindingAttributeMapper<CompoundButton> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<CompoundButton> mappings) {
	mappings.mapProperty(CheckedAttribute.class, "checked");

	mappings.mapEvent(OnCheckedChangeAttribute.class, "onCheckedChange");
    }
}
