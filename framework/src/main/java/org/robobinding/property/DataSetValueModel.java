package org.robobinding.property;

import org.robobinding.itempresentationmodel.DataSetObservable;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.ViewTypeSelectable;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface DataSetValueModel extends ViewTypeSelectable, DataSetObservable {
	RefreshableItemPresentationModel newRefreshableItemPresentationModel(int itemViewType);
}
