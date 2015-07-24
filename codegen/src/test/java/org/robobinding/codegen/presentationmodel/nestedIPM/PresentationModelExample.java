package org.robobinding.codegen.presentationmodel.nestedIPM;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class PresentationModelExample {
	@ItemPresentationModel(value=ItemPresentationModelExample.class)
	public List<Item> getItems() {
		return Lists.newArrayList();
	}
}
