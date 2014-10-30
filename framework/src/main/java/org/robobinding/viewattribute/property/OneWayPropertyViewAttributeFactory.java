package org.robobinding.viewattribute.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface OneWayPropertyViewAttributeFactory<ViewType> {
	OneWayPropertyViewAttribute<ViewType, ?> create();

}
