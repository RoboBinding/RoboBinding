package org.robobinding.viewattribute.event;

import org.robobinding.attribute.Command;
import org.robobinding.widget.view.AbstractViewEvent;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface EventViewAttribute<T extends View> {
    void bind(T view, Command command);
    Class<? extends AbstractViewEvent> getEventType();
}
