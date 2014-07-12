package org.robobinding.widget.edittext;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EditTextAttributeMapper implements BindingAttributeMapper<EditText> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<EditText> mappings) {
	mappings.mapGroupedAttribute(TwoWayTextAttributeGroup.class, TwoWayTextAttributeGroup.TEXT, TwoWayTextAttributeGroup.VALUE_COMMIT_MODE);

	mappings.mapEvent(OnTextChangedAttribute.class, "onTextChanged");
    }
}
