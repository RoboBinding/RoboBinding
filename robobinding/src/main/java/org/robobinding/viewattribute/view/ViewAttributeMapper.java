package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeMapper implements BindingAttributeMapper<View> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<View> mappings) {
	mappings.mapPropertyAttribute(ViewVisibilityAttribute.class, "visibility");
	mappings.mapPropertyAttribute(EnabledAttribute.class, "enabled");
	mappings.mapPropertyAttribute(BackgroundAttribute.class, "background");
	mappings.mapPropertyAttribute(BackgroundColorAttribute.class, "backgroundColor");
	mappings.mapPropertyAttribute(FocusableAttribute.class, "focusable");

	mappings.mapCommandAttribute(OnClickAttribute.class, "onClick");
	mappings.mapCommandAttribute(OnLongClickAttribute.class, "onLongClick");
	mappings.mapCommandAttribute(OnFocusChangeAttribute.class, "onFocusChange");
	mappings.mapCommandAttribute(OnFocusAttribute.class, "onFocus");
	mappings.mapCommandAttribute(OnFocusLostAttribute.class, "onFocusLost");
    }
}
