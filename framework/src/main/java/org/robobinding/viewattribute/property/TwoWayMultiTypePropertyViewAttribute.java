package org.robobinding.viewattribute.property;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface TwoWayMultiTypePropertyViewAttribute<ViewType> {
	TwoWayPropertyViewAttribute<ViewType, ?, ?> create(ViewType view, Class<?> propertyType);

}
