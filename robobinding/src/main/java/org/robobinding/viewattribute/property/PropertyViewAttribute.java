package org.robobinding.viewattribute.property;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface PropertyViewAttribute<ViewType extends View, PropertyType> {
    void updateView(ViewType view, PropertyType newValue);
}
