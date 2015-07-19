package org.robobinding.viewattribute.event;

import org.robobinding.attribute.Command;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface EventViewAttribute<ViewType, ViewAddOnType extends ViewAddOn> {
	void bind(ViewAddOnType viewAddOn, Command command, ViewType view);

	Class<?> getEventType();
}
