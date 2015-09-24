package org.robobinding.codegen.presentationmodel;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.itempresentationmodel.TypedCursor;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DefineTryToCreateDataSetProperty {
	public static final int numDataSetProperties = 5;

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
			factoryMethod="createStringItemPresentationModelWithParameter")
	public List<String> getDataSetPropWithFactoryMethodWithParameter() {
		return null;
	}
	
	
	public StringItemPresentationModel createStringItemPresentationModelWithParameter(int itemViewType) {
		return null;
	}
	
	@ItemPresentationModel(value=StringItemPresentationModel.class,
			viewTypeSelector = "selectViewType")
	public List<String> getDataSetPropWithViewTypeSelector() {
		return null;
	}
	
	public int selectViewType(ViewTypeSelectionContext<String> context) {
		return 0;
	}
}
