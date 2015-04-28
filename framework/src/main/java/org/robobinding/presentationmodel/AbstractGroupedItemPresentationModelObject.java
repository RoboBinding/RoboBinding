package org.robobinding.presentationmodel;

import org.robobinding.groupeditempresentationmodel.GroupedItemContext;
import org.robobinding.groupeditempresentationmodel.GroupedItemPresentationModel;
import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;


/**
 * @since 1.0
 * @author Jihun Lee
 *
 */
public abstract class AbstractGroupedItemPresentationModelObject extends AbstractPresentationModelObject
	implements RefreshableGroupedItemPresentationModel {
	private final GroupedItemPresentationModel<Object> groupedItemPresentationModel;

	@SuppressWarnings("unchecked")
	public AbstractGroupedItemPresentationModelObject(GroupedItemPresentationModel<?> groupedItemPresentationModel) {
		super(groupedItemPresentationModel);
		this.groupedItemPresentationModel = (GroupedItemPresentationModel<Object>)groupedItemPresentationModel;
	}
	
	public void updateData(Object bean, GroupedItemContext itemContext) {
		groupedItemPresentationModel.updateData(bean, itemContext);
	}
	
	@Override
	public void refresh() {
		changeSupport.refreshPresentationModel();
	}
}
