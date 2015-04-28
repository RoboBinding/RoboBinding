package org.robobinding.groupeditempresentationmodel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public interface GroupedItemPresentationModel<T> {
	void updateData(T bean, GroupedItemContext groupedItemContext);
}
