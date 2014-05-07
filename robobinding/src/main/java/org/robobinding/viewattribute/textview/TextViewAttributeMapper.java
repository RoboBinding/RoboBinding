package org.robobinding.viewattribute.textview;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextViewAttributeMapper implements BindingAttributeMapper<TextView> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<TextView> mappings) {
	mappings.mapPropertyAttribute(TextAttribute.class, "text");

	mappings.mapPropertyAttribute(TextColorAttribute.class, "textColor");
    }
}
