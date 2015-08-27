package org.robobinding.codegen.viewbinding;

import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.property.TwoWayPropertyViewAttribute;
import org.robobinding.widgetaddon.ViewAddOn;
import org.robobinding.widgetaddon.view.ViewAddOnForView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewWithProperties {
	public static final String PRIMITIVE_PROP = "primitiveProp";
	public static final String OBJECT_PROP = "objectProp";
	public static final String AMBIGUOUS_SETTER = "ambiguousSetter";
	public static final String TWO_WAY_PROP = "twoWayProp";

	public void setPrimitiveProp(int value) {
	}
	
	public void setObjectProp(Object value) {
	}
	
	public void setAmbiguousSetter(int value) {
	}
	
	public void setAmbiguousSetter(boolean value) {	
	}

	public static class CustomTwoWayProp
			implements TwoWayPropertyViewAttribute<ViewWithProperties, ViewAddOnForView, Object> {

		@Override
		public void updateView(ViewWithProperties view, Object newValue, ViewAddOnForView viewAddOn) {
		}

		@Override
		public void observeChangesOnTheView(ViewAddOnForView viewAddOn, final ValueModel<Object> valueModel, ViewWithProperties view) {
		}
	}
}
