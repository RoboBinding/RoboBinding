package org.robobinding.viewattribute.property;

import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface TwoWayPropertyViewAttribute<ViewType, PropertyType> extends PropertyViewAttribute<ViewType, PropertyType> {
	void observeChangesOnTheView(ViewType view, ValueModel<PropertyType> valueModel);
}
