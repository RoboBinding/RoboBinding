package org.robobinding.viewattribute.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface OneWayMultiTypePropertyViewAttribute<ViewType> {
	OneWayPropertyViewAttribute<ViewType, ?> create(ViewType view, Class<?> propertyType);
}
