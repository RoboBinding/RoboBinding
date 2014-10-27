package org.robobinding.codegen;

import java.util.List;

import org.robobinding.codegen.typemirror.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefineTryToCreateDataSetProperty {
	@ItemPresentationModel(value=StringItemPresentationModel.class)
	public List<String> getDataSetProp() {
		return null;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class, factoryMethod="createStringItemPresentationModel")
	public List<String> getDataSetPropWithFactoryMethod() {
		return null;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class)
	public TypedCursor<String> getCursorDataSetProp() {
		return null;
	}
	
	public StringItemPresentationModel createStringItemPresentationModel() {
		return null;
	}
}
