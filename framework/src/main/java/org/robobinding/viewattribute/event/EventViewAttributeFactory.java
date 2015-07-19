package org.robobinding.viewattribute.event;

import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface EventViewAttributeFactory<ViewType> {
	EventViewAttribute<ViewType, ? extends ViewAddOn> create();
}
