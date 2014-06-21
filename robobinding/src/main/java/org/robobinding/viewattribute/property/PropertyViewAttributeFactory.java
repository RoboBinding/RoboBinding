package org.robobinding.viewattribute.property;

import android.view.View;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface PropertyViewAttributeFactory<T extends View> {
    PropertyViewAttribute<T, ?> create();

}
