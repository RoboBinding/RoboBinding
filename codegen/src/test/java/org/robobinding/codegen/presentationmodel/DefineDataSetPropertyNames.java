package org.robobinding.codegen.presentationmodel;

import java.util.List;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefineDataSetPropertyNames {
	
	@org.robobinding.codegen.presentationmodel.typemirror.ItemPresentationModel(value = StringIPM.class)
	public List<String> getDataSetProp1() {
		return null;
	}
	
	@org.robobinding.codegen.presentationmodel.typemirror.ItemPresentationModel(value = StringIPM.class)
	public List<String> getDataSetProp2() {
		return null;
	}
	
	public static class StringIPM implements ItemPresentationModel<String> {
		@Override
		public void updateData(String bean, ItemContext itemContext) {
		}
	}
}
