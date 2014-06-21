package org.robobinding.viewattribute.property;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface MultiTypePropertyViewAttribute<T extends View> {
    PropertyViewAttribute<T, ?> create(T view, Class<?> propertyType);
}
