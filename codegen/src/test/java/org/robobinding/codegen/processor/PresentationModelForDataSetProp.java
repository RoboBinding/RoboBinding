package org.robobinding.codegen.processor;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@org.robobinding.annotation.PresentationModel
class PresentationModelForDataSetProp {
	@ItemPresentationModel(value=ItemPresentationModelWithDataSetProperties.class)
	public List<String> getDataSetProp() {
		return null;
	}
}