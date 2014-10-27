package org.robobinding.viewattribute.event;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface EventViewAttributeFactory<ViewType> {
	EventViewAttribute<ViewType> create();
}
