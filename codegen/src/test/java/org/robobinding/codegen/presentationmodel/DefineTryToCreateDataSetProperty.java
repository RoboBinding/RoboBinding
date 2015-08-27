package org.robobinding.codegen.presentationmodel;

import java.util.List;

import org.robobinding.codegen.presentationmodel.typemirror.ItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemViewFactory;
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

	public StringItemPresentationModel createStringItemPresentationModelWithArg(Object item) {
		return null;
	}

	@ItemPresentationModel(value=StringItemPresentationModel.class, factoryMethod="createStringItemPresentationModelWithArg")
	public List<String> getDataSetPropWithFactoryMethodWithArg() {
		return null;
	}

	@ItemPresentationModel(value=StringItemPresentationModel.class, viewFactory=CustomItemViewFactory.class)
	public List<String> getDataSetPropWithItemViewFactory() {
		return null;
	}

	public static class CustomItemViewFactory implements ItemViewFactory {

		@Override
		public int getItemViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position, Object item) {
			return position % 2;
		}

		@Override
		public int getItemLayoutId(int position, Object item) {
			return position % 2;
		}
	}
}
