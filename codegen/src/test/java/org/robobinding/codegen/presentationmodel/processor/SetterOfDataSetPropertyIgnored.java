package org.robobinding.codegen.presentationmodel.processor;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class SetterOfDataSetPropertyIgnored {
	@ItemPresentationModel(value = StringItemPresentationModel.class)
	public List<String> getDataSetProp() {
		return null;
	}
	
	public void setDataSetProp(List<String> param) {
	}
}
