package org.robobinding.widget.textview;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextViewBinding implements ViewBinding<TextView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<TextView> mappings) {
		mappings.mapOneWayProperty(TextAttribute.class, "text");
		mappings.mapOneWayProperty(TextColorAttribute.class, "textColor");
	}
}