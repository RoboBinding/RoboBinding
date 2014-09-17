package org.robobinding.itempresentationmodel;

import org.robobinding.presentationmodel.AbstractPresentationModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractItemPresentationModel<T> extends AbstractPresentationModel implements ItemPresentationModel<T> {

	@Override
	public final void updateData(int index, T bean) {
		doUpdateData(index, bean);
		refreshPresentationModel();
	}

	protected abstract void doUpdateData(int index, T bean);

}
