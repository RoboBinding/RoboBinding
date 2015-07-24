package org.robobinding.codegen.presentationmodel.nestedIPM;

import java.util.List;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class ItemPresentationModelExample implements ItemPresentationModel<Item> {
	@Override
	public void updateData(Item bean, ItemContext itemContext) {
	}
	
	@org.robobinding.annotation.ItemPresentationModel(value=SubItemPresentationModelExample.class)
	public List<SubItem> getSubItems() {
		return Lists.newArrayList();
	}
}
