package org.robobinding.presentationmodel;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractItemPresentationModelObject extends AbstractPresentationModelObject 
	implements RefreshableItemPresentationModel {
	private final ItemPresentationModel<Object> itemPresentationModel;
	
	@SuppressWarnings("unchecked")
	public AbstractItemPresentationModelObject(ItemPresentationModel<?> itemPresentationModel) {
		super(itemPresentationModel);
		this.itemPresentationModel = (ItemPresentationModel<Object>)itemPresentationModel;
	}
	
	public void updateData(Object bean, ItemContext itemContext) {
		itemPresentationModel.updateData(bean, itemContext);
	}
	
	@Override
	public void refresh() {
		changeSupport.refreshPresentationModel();
	}
}
