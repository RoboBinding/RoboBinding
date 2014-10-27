package org.robobinding.itempresentationmodel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public interface ItemPresentationModel<T> {
	void updateData(T bean, ItemContext itemContext);
}
