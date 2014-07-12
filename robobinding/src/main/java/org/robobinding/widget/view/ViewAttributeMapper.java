package org.robobinding.widget.view;

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
	mappings.mapMultiTypeProperty(new VisibilityAttributeFactory<View>(new ViewVisibilityFactory()), "visibility");
	mappings.mapProperty(EnabledAttribute.class, "enabled");
	mappings.mapMultiTypeProperty(BackgroundAttribute.class, "background");
	mappings.mapProperty(BackgroundColorAttribute.class, "backgroundColor");
	mappings.mapProperty(FocusableAttribute.class, "focusable");

	mappings.mapEvent(OnClickAttribute.class, "onClick");
	mappings.mapEvent(OnLongClickAttribute.class, "onLongClick");
	mappings.mapEvent(OnFocusChangeAttribute.class, "onFocusChange");
	mappings.mapEvent(OnFocusAttribute.class, "onFocus");
	mappings.mapEvent(OnFocusLostAttribute.class, "onFocusLost");
    }
}
