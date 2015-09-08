package org.robobinding.codegen.presentationmodel;

import java.util.List;

import org.robobinding.codegen.presentationmodel.typemirror.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

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
	
	@ItemPresentationModel(value=StringItemPresentationModel.class)
	public TypedCursor<String> getCursorDataSetProp() {
		return null;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class, 
			factoryMethod="createStringItemPresentationModel")
	public List<String> getDataSetPropWithFactoryMethod() {
		return null;
	}
	
	public StringItemPresentationModel createStringItemPresentationModel() {
		return null;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class, 
			viewTypeSelector = "selectViewType")
	public List<String> getDataSetPropWithViewTypeSelector() {
		return null;
	}
	
	public int selectViewType() {
		return 0;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class, 
			factoryMethod="createStringItemPresentationModelWithParameter",
			viewTypeSelector = "selectViewTypeWithParameter")
	public List<String> getDataSetPropWithFactoryMethodAndViewTypeSelector() {
		return null;
	}
	
	public StringItemPresentationModel createStringItemPresentationModelWithParameter(int itemViewType) {
		return null;
	}
	
	public int selectViewTypeWithParameter(ViewTypeSelectionContext<String> context) {
		return 0;
	}
}
