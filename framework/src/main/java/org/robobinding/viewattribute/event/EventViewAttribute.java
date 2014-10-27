package org.robobinding.viewattribute.event;

import org.robobinding.attribute.Command;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface EventViewAttribute<ViewType> {
	void bind(ViewType view, Command command);

	Class<?> getEventType();
}
