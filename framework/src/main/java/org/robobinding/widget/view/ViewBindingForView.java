package org.robobinding.widget.view;

import org.robobinding.viewbinding.BindingAttributeMappings;
import org.robobinding.viewbinding.ViewBinding;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewBindingForView implements ViewBinding<View> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<View> mappings) {
		mappings.mapOneWayMultiTypeProperty(new VisibilityAttributeFactory<View>(new ViewVisibilityFactory()), "visibility");
		mappings.mapOneWayProperty(LayoutMarginAttribute.class, "layoutMargin");
		mappings.mapOneWayProperty(PaddingAttribute.class, "padding");

		mappings.mapEvent(OnClickAttribute.class, "onClick");
		mappings.mapEvent(OnLongClickAttribute.class, "onLongClick");
		mappings.mapEvent(OnFocusChangeAttribute.class, "onFocusChange");
		mappings.mapEvent(OnFocusAttribute.class, "onFocus");
		mappings.mapEvent(OnFocusLostAttribute.class, "onFocusLost");
		mappings.mapEvent(OnTouchAttribute.class, "onTouch");
	}
}
