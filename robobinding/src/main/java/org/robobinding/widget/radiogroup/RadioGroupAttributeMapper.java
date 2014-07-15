package org.robobinding.widget.radiogroup;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.RadioGroup;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RadioGroupAttributeMapper implements BindingAttributeMapper<RadioGroup> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<RadioGroup> mappings) {
	mappings.mapEvent(OnCheckedChangeAttribute.class, "onCheckedChange");
    }
}