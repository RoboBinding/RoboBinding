package org.robobinding.widget.edittext;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EditTextBinding implements ViewBinding<EditText> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<EditText> mappings) {
		mappings.mapGroupedAttribute(TwoWayTextAttributeGroup.class, TwoWayTextAttributeGroup.TEXT, TwoWayTextAttributeGroup.VALUE_COMMIT_MODE);

		mappings.mapEvent(OnTextChangedAttribute.class, "onTextChanged");
	}
}
