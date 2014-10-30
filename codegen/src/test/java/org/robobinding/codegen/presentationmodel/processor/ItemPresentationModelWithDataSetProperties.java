package org.robobinding.codegen.presentationmodel.processor;

import java.util.List;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelWithDataSetProperties implements ItemPresentationModel<String> {
	@Override
	public void updateData(String bean, ItemContext itemContext) {
	}
	
	@org.robobinding.annotation.ItemPresentationModel(value=StringItemPresentationModel.class)
	public List<String> getDataSetPropery() {
		return null;
	}
}
