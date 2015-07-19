package org.robobinding.viewattribute.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface OneWayPropertyViewAttribute<ViewType, PropertyType> {
	void updateView(ViewType view, PropertyType newValue);
}
