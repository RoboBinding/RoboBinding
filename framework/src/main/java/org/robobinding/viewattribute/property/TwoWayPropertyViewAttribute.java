package org.robobinding.viewattribute.property;

import org.robobinding.property.ValueModel;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface TwoWayPropertyViewAttribute<ViewType, ViewAddOnType extends ViewAddOn, PropertyType> {
	void updateView(ViewType view, PropertyType newValue, ViewAddOnType viewAddOn);
	void observeChangesOnTheView(ViewAddOnType viewAddOn, ValueModel<PropertyType> valueModel, ViewType view);
}