package org.robobinding.viewattribute.property;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface TwoWayPropertyViewAttributeFactory<ViewType> {
	TwoWayPropertyViewAttribute<ViewType, ?, ?> create();
}
