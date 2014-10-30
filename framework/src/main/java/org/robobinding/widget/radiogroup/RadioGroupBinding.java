package org.robobinding.widget.radiogroup;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.RadioGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class RadioGroupBinding implements ViewBinding<RadioGroup> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<RadioGroup> mappings) {
		mappings.mapEvent(OnCheckedChangeAttribute.class, "onCheckedChange");
	}
}