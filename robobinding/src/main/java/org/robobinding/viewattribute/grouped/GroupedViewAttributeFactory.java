package org.robobinding.viewattribute.grouped;

import android.view.View;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface GroupedViewAttributeFactory<T extends View> {
    GroupedViewAttribute<T> create();

}
