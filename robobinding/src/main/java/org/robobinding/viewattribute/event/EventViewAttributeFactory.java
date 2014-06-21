package org.robobinding.viewattribute.event;

import android.view.View;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface EventViewAttributeFactory<T extends View> {
    EventViewAttribute<T> create();
}
