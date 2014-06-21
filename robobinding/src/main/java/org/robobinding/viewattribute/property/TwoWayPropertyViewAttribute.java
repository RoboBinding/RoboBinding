package org.robobinding.viewattribute.property;

import org.robobinding.property.ValueModel;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface TwoWayPropertyViewAttribute<ViewType extends View, PropertyType> extends PropertyViewAttribute<ViewType, PropertyType> {
    void observeChangesOnTheView(ViewType view, ValueModel<PropertyType> valueModel);
}
