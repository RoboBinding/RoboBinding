package org.robobinding.codegen.processor;

import java.util.List;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@PresentationModel
public class SelfDescribedPresentationModel {
	public static final int numProperties = 3;
	public static final int numDataSetProperties = 2;
	public static final int numEventMethods = 4;

	public String getProp() {
		return null;
	}

	public void setProp(String prop) {
	}
	
	public int getReadOnlyProp() {
		return 0;
	}

	public void setWriteOnlyProp(int prop) {
	}

	@ItemPresentationModel(value = StringItemPresentationModel.class)
	public List<String> getDataSetProp() {
		return null;
	}
	
	@ItemPresentationModel(value = StringItemPresentationModel.class, factoryMethod = "newStringItemPresentationModel")
	public List<String> getDataSetPropWithFactoryMethod() {
		return null;
	}
	
	public StringItemPresentationModel newStringItemPresentationModel() {
		return new StringItemPresentationModel();
	}
	
	public void eventMethod() {
	}
	
	public void eventMethodWithArg(Object event) {
	}
	
	public int eventMethodWithReturn() {
		return 0;
	}
	
	public int eventMethodWithReturnAndArg(Object event) {
		return 0;
	}
}
